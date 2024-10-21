package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.entity.*;
import school.sptech.apimiseenplace.repository.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArquivoTxtService {
    private final ProdutoRepository produtoRepository;
    private final MassaRepository massaRepository;
    private final RecheioRepository recheioRepository;
    private final CoberturaRepository coberturaRepository;
    private final UnidadeMedidaRepository unidadeMedidaRepository;
    private final TipoProdutoRepository tipoProdutoRepository;

    // Método para gravar o registro no arquivo
    public void gravaRegistro(String nomeArq, String registro) {
        BufferedWriter saida = null;

        try {
            saida = new BufferedWriter(new FileWriter(nomeArq, true));
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo: " + erro.getMessage());
        }

        try {
            if (saida != null) {
                saida.append(registro).append("\n");
                saida.close();
            }
        } catch (IOException erro) {
            System.out.println("Erro na gravação do arquivo: " + erro.getMessage());
        }
    }

    // Método para gravar o arquivo TXT
    public void gravaArquivoTxt(String nomeArq) {
        int contaRegDados = 0;

        // Grava o header
        String header = "00PRODUTO";
        header += LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        header += "01";
        gravaRegistro(nomeArq, header);

        List<Produto> lista = produtoRepository.findAll();

        // Grava cada produto no corpo do arquivo
        for (Produto p : lista) {
            String corpo = "02";
            corpo += String.format("%-50.50s", p.getNome());
            corpo += String.format("%06.2f", p.getPreco());
            corpo += String.format("%-10.10s", p.getUnidadeMedida());
            corpo += String.format("%-30.30s", p.getMassa().getNome());
            corpo += String.format("%-30.30s", p.getRecheio().getNome());
            corpo += String.format("%06.2f", p.getRecheio().getPreco()); // Pega o preço do recheio diretamente
            corpo += String.format("%-30.30s", p.getCobertura().getNome());
            corpo += String.format("%-20.20s", p.getTipoProduto().getTipo());
            corpo += String.format("%-100.100s", p.getDescricao());

            gravaRegistro(nomeArq, corpo);
            contaRegDados++;
        }

        // Grava o trailer
        String trailer = "01" + String.format("%05d", contaRegDados);
        gravaRegistro(nomeArq, trailer);
    }

    // Método para ler o arquivo TXT
    public List<Produto> leArquivoTxt(File file) {
        BufferedReader entrada = null;
        List<Produto> listaProdutos = new ArrayList<>();
        String registro, tipoRegistro;

        try {
            entrada = new BufferedReader(new FileReader(file));
        } catch (IOException erro) {
            System.out.println("Erro ao abrir o arquivo: " + erro.getMessage());
        }

        List<Massa> massas = massaRepository.findAll();
        List<Cobertura> coberturas = coberturaRepository.findAll();
        List<Recheio> recheios = recheioRepository.findAll();
        List<UnidadeMedida> unidadeMedidas = unidadeMedidaRepository.findAll();
        List<TipoProduto> tipoProdutos = tipoProdutoRepository.findAll();

        try {
            if (entrada != null) {
                registro = entrada.readLine();

                while (registro != null) {
                    tipoRegistro = registro.substring(0, 2);

                    if (tipoRegistro.equals("00")) {
                        System.out.println("Header encontrado.");
                    } else if (tipoRegistro.equals("01")) {
                        System.out.println("Trailer encontrado.");
                        int qtdRegistros = Integer.parseInt(registro.substring(2, 7));
                        System.out.println("Registros lidos: " + qtdRegistros);
                    } else if (tipoRegistro.equals("02")) {
                        String nome = registro.substring(2, 52).trim();
                        double preco = Double.parseDouble(registro.substring(52, 58).trim().replace(",", "."));
                        String unidadeMedida = registro.substring(58, 68).trim();
                        String massa = registro.substring(68, 98).trim();
                        String recheioNome = registro.substring(98, 128).trim();
                        double precoRecheio = Double.parseDouble(registro.substring(128, 134).trim().replace(",", "."));
                        String cobertura = registro.substring(134, 164).trim();
                        String tipoProduto = registro.substring(164, 184).trim();
                        String descricao = registro.substring(184, 284).trim();

                        Massa massaRegistro = null;
                        Recheio recheioRegistro = null;
                        Cobertura coberturaRegistro = null;
                        UnidadeMedida unidadeMedidaRegistro = null;
                        TipoProduto tipoProdutoRegistro = null;

                        boolean massaEncontrada = false;
                        for (Massa massaBanco : massas) {
                            if (massaBanco.getNome().equalsIgnoreCase(massa)) {
                                massaRegistro = massaBanco;
                                massaEncontrada = true;
                                break;
                            }
                        }

                        boolean coberturaEncontrada = false;
                        for (Cobertura coberturaBanco : coberturas) {
                            if (coberturaBanco.getNome().equalsIgnoreCase(cobertura)) {
                                coberturaRegistro = coberturaBanco;
                                coberturaEncontrada = true;
                                break;
                            }
                        }

                        boolean recheioEncontrado = false;
                        for (Recheio recheioBanco : recheios) {
                            if (recheioBanco.getNome().equalsIgnoreCase(recheioNome)) {
                                recheioRegistro = recheioBanco;
                                recheioEncontrado = true;
                                break;
                            }
                        }

                        boolean unidadeMedidadaEncontrada = false;
                        for (UnidadeMedida unidadeMedidaBanco : unidadeMedidas) {
                            if (unidadeMedidaBanco.getUnidadeMedida().equalsIgnoreCase(unidadeMedida)) {
                                unidadeMedidaRegistro = unidadeMedidaBanco;
                                unidadeMedidadaEncontrada = true;
                                break;
                            }
                        }

                        boolean tipoProdutoEncontrado = false;
                        for (TipoProduto tipoProdutoBanco : tipoProdutos) {
                            if (tipoProdutoBanco.getTipo().equalsIgnoreCase(tipoProduto)) {
                                tipoProdutoRegistro = tipoProdutoBanco;
                                tipoProdutoEncontrado = true;
                                break;
                            }
                        }

                        if (!massaEncontrada) {
                            Massa massaNova = new Massa(null, massa);
                            massaRegistro = massaRepository.save(massaNova);
                        }

                        if (!coberturaEncontrada) {
                            Cobertura coberturaNova = new Cobertura(null, cobertura);
                            coberturaRegistro = coberturaRepository.save(coberturaRegistro);
                        }

                        if (!recheioEncontrado) {
                            Recheio recheioNovo = new Recheio(null, recheioNome, precoRecheio);
                            recheioRegistro = recheioRepository.save(recheioNovo);
                        }

                        if (!unidadeMedidadaEncontrada) {
                            UnidadeMedida unidadeMedidaNova = new UnidadeMedida(null, unidadeMedida, null);
                            unidadeMedidaRegistro = unidadeMedidaRepository.save(unidadeMedidaNova);
                        }

                        if (!tipoProdutoEncontrado) {
                            TipoProduto tipoProdutoNovo = new TipoProduto(null, tipoProduto, null);
                            tipoProdutoRegistro = tipoProdutoRepository.save(tipoProdutoNovo);
                        }

                        Produto produto = new Produto(
                                null,
                                nome,
                                preco,
                                descricao,
                                null,
                                1,
                                recheioRegistro,
                                massaRegistro,
                                coberturaRegistro,
                                unidadeMedidaRegistro,
                                tipoProdutoRegistro
                        );

                        listaProdutos.add(produto);
                        produtoRepository.save(produto);
                    }

                    registro = entrada.readLine();
                }

                entrada.close();
            }
        } catch (IOException erro) {
            System.out.println("Erro ao ler o arquivo: " + erro.getMessage());
        }

        return listaProdutos;
    }
}
