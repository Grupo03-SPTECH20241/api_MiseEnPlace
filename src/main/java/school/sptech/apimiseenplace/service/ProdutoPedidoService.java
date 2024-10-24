package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.pedido.PedidoListagemDTO;
import school.sptech.apimiseenplace.dto.pedido.PedidoMapper;
import school.sptech.apimiseenplace.dto.produto_pedido.*;
import school.sptech.apimiseenplace.entity.*;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.*;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProdutoPedidoService {
    private final ProdutoPedidoRepository produtoPedidoRepository;
    private final PedidoRepository pedidoRepository;
    private final ProdutoService produtoService;
    private final PersonalizacaoService personalizacaoService;
    private final PedidoService pedidoService;
    private final FormaEntregaRepository formaEntregaRepository;
    private final ClienteRepository clienteRepository;
    private final RecheioRepository recheioRepository;
    private final MassaRepository massaRepository;
    private final CoberturaRepository coberturaRepository;
    private final UnidadeMedidaRepository unidadeMedidaRepository;
    private final TipoProdutoRepository tipoProdutoRepository;
    private final PersonalizacaoRepository personalizacaoRepository;
    private final FormaPagamentoRepository formaPagamentoRepository;
    private final RecheioService recheioService;

    public ProdutoPedido cadastrar(ProdutoPedido produtoPedido, Integer produtoId, Integer personalizacaoId, Integer pedidoId) {
        if (produtoPedido == null) throw new BadRequestException("Produto Pedido");

        Produto produto = produtoService.encontrarPorId(produtoId);
        if (personalizacaoId != null) {
            Personalizacao personalizacao = personalizacaoService.encontrarPorId(personalizacaoId);
            produtoPedido.setPersonalizacao(personalizacao);
        }
        Pedido pedido = pedidoService.encontrarPorId(pedidoId);

        produtoPedido.setProduto(produto);
        produtoPedido.setPedido(pedido);

        return produtoPedidoRepository.save(produtoPedido);
    }

    public ProdutoPedido encontrarPorId(Integer id) {
        return produtoPedidoRepository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Produto Pedido")
        );
    }

    public List<ProdutoPedidoListagemDTO> listar() {
        List<ProdutoPedido> produtoPedidos = produtoPedidoRepository.findAll();
        return ProdutoPedidoMapper.toDto(produtoPedidos);
    }

    public ProdutoPedido atualizar(Integer id, ProdutoPedido produtoPedido, Integer produtoId, Integer personalizacaoId, Integer pedidoId) {
        ProdutoPedido produtoPedidoEncontrado = encontrarPorId(id);

        produtoPedidoEncontrado.setIdProdutoPedido(id);
        produtoPedidoEncontrado.setQtProduto(produtoPedido.getQtProduto());
        produtoPedidoEncontrado.setObservacoes(produtoPedido.getObservacoes());

        Produto produto = produtoService.encontrarPorId(produtoId);

        if (personalizacaoId != null) {
            Personalizacao personalizacao = personalizacaoService.encontrarPorId(personalizacaoId);
            produtoPedidoEncontrado.setPersonalizacao(personalizacao);
        } else {
            produtoPedidoEncontrado.setPersonalizacao(null);
        }
        Pedido pedido = pedidoService.encontrarPorId(pedidoId);

        produtoPedidoEncontrado.setProduto(produto);
        produtoPedidoEncontrado.setPedido(pedido);

        return produtoPedidoRepository.save(produtoPedidoEncontrado);
    }

    public boolean existePorId(Integer id) {
        if (id == null) return false;
        return produtoPedidoRepository.existsById(id);
    }

    public String deletar(Integer id) {
        if (!produtoPedidoRepository.existsById(id)) {
            throw new NaoEncontradoException("Produto Pedido");
        }

        produtoPedidoRepository.deleteById(id);
        return "Produto Pedido deletado com sucesso!";
    }

    public List<ProdutoPedidoListagemDTO> listarWhereIdPersonalizacaoEquals(Integer idPedido) {
        return ProdutoPedidoMapper.toDto(produtoPedidoRepository.findByIdPersonalizacaoEquals(idPedido));
    }

    public ProdutoPedidoListagemDTO updatePersonalizacao(Personalizacao p, Integer idProdutoPedido) {
        if (!produtoPedidoRepository.existsById(idProdutoPedido)) throw new BadRequestException("Produto Pedido");

        Optional<ProdutoPedido> p2 = produtoPedidoRepository.findById(idProdutoPedido);
        if (p2.isEmpty()) throw new NaoEncontradoException("Produto Pedido");

        ProdutoPedido p3 = new ProdutoPedido();
        p3.setIdProdutoPedido(idProdutoPedido);
        p3.setQtProduto(p2.get().getQtProduto());
        p3.setObservacoes(p2.get().getObservacoes());
        p3.setProduto(p2.get().getProduto());
        p3.setPedido(p2.get().getPedido());
        p3.setPersonalizacao(p);

        return ProdutoPedidoMapper.toDto(produtoPedidoRepository.save(p3));
    }

    public List<QuantidadeProdutoDto> getQuantidadeProduto() {
        List<ProdutoPedidoListagemDTO> listaProdutoPedido = listar();

        List<QuantidadeProdutoDto> quantidadeProdutos = listaProdutoPedido.stream()
                .collect(Collectors.groupingBy(produto -> produto.getPedidoDto().getIdPedido(), Collectors.counting()))
                .entrySet().stream()
                .map(entry -> {
                    QuantidadeProdutoDto quantidadeProdutoDto = new QuantidadeProdutoDto();
                    quantidadeProdutoDto.setIdPedido(entry.getKey());
                    quantidadeProdutoDto.setQuantidadeProdutos(entry.getValue().intValue());
                    return quantidadeProdutoDto;
                })
                .collect(Collectors.toList());

        return quantidadeProdutos;
    }

    public List<ListagemProdutosDto> listagemProdutos() {
        List<ProdutoPedidoListagemDTO> listaProdutoPedido = listar();

        List<ListagemProdutosDto> listagemProdutos = listaProdutoPedido.stream()
                .collect(Collectors.groupingBy(produto -> produto.getPedidoDto().getIdPedido(),
                        Collectors.mapping(ProdutoPedidoListagemDTO::getProdutoDto, Collectors.toList())))
                .entrySet().stream()
                .map(entry -> {
                    ListagemProdutosDto listagemProdutosDto = new ListagemProdutosDto();
                    listagemProdutosDto.setIdPedido(entry.getKey());
                    listagemProdutosDto.setProdutos(entry.getValue());
                    return listagemProdutosDto;
                })
                .collect(Collectors.toList());

        return listagemProdutos;
    }

    public List<ProdutoPedido> listagemAgenda(LocalDate dataInicio, LocalDate dataFim) {
        return produtoPedidoRepository.findByDataInicioAndDataFim(dataInicio, dataFim);
    }

    public File exportarPedidos(LocalDate dataInicio, LocalDate dataFim) {
        List<ProdutoPedido> listaPedidos = listagemAgenda(dataInicio, dataFim);
        File csv = new File("pedidos.csv");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        try (PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(csv), StandardCharsets.UTF_8))) {
            writer.println("DataPedido;Valor;Status;ValorSinal;DataEntrega;FormaEntrega;Cliente;FormaPagamento;Observacoes;QuantidadeProduto;NomeProduto;PrecoProduto;DescricaoProduto;QuantidadeDisponivelProduto;NomeRecheio;PrecoRecheio;Massa;Cobertura;UnidadeMedida;TipoProduto;Tema;RecheioPersonalizacao;ValorRecheioPersonalizacao;MassaPersonalizacao;CoberturaPersonalizacao");
            for (ProdutoPedido produtoPedido : listaPedidos) {
                writer.println(
                        formatter.format(produtoPedido.getPedido().getDtPedido())+ ";"
                        + produtoPedido.getPedido().getVlPedido() + ";"
                        + produtoPedido.getPedido().getStatus() + ";"
                        + produtoPedido.getPedido().getValorSinal() + ";"
                        + formatter.format(produtoPedido.getPedido().getDtEntrega()) + ";"
                        + produtoPedido.getPedido().getFormaEntrega().getFormaEntrega() + ";"
                        + produtoPedido.getPedido().getCliente().getNome() + ";"
                        + produtoPedido.getPedido().getFormaPagamento().getFormaPagamento() + ";"
                        + produtoPedido.getObservacoes() + ";"
                        + produtoPedido.getQtProduto() + ";"
                        + produtoPedido.getProduto().getNome() + ";"
                        + produtoPedido.getProduto().getPreco() + ";"
                        + produtoPedido.getProduto().getDescricao() + ";"
                        + produtoPedido.getProduto().getQtdDisponivel() + ";"
                        + produtoPedido.getProduto().getRecheio().getNome() + ";"
                        + produtoPedido.getProduto().getRecheio().getPreco() + ";"
                        + produtoPedido.getProduto().getMassa().getNome() + ";"
                        + produtoPedido.getProduto().getCobertura().getNome() + ";"
                        + produtoPedido.getProduto().getUnidadeMedida().getUnidadeMedida() + ";"
                        + produtoPedido.getProduto().getTipoProduto().getTipo() + ";"
                        + produtoPedido.getPersonalizacao().getTema() + ";"
                        + produtoPedido.getPersonalizacao().getRecheio().getNome() + ";"
                        + produtoPedido.getPersonalizacao().getRecheio().getPreco() + ";"
                        + produtoPedido.getPersonalizacao().getMassa().getNome() + ";"
                        + produtoPedido.getPersonalizacao().getCobertura().getNome());
            }
            return csv;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void base64ToCSV(String base64, String filePath) {
        try {
            // Remover quebras de linha ou espaços em branco, se existirem
            base64 = base64.replaceAll("\\s+", "");

            // Validar a string Base64
            if (!isValidBase64(base64)) {
                throw new IllegalArgumentException("Invalid Base64 input");
            }

            // Decode the Base64 string into a byte array
            byte[] decodedBytes = Base64.getDecoder().decode(base64);

            // Convert the byte array into a string
            String content = new String(decodedBytes, StandardCharsets.UTF_8);

            // Write the string to a .csv file
            Path path = Paths.get(filePath);
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        } catch (IllegalArgumentException e) {
            System.err.println("Error: " + e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static boolean isValidBase64(String base64) {
        String base64Pattern = "^[A-Za-z0-9+/=]+$";
        if (!base64.matches(base64Pattern)) {
            return false;
        }
        try {
            Base64.getDecoder().decode(base64);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }


    public boolean importarPedidos(String base64) {
        base64ToCSV(base64, "pedidos.csv");

        try (Scanner scanner = new Scanner(new File("pedidos.csv"), StandardCharsets.UTF_8.name())) {
            scanner.nextLine();
            var linha = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");
                linha++;
                Pedido pedido = new Pedido();
                pedido.setDtPedido(LocalDate.parse(values[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                pedido.setVlPedido(Double.parseDouble(values[1]));
                pedido.setStatus(values[2].charAt(0));
                pedido.setValorSinal(Double.parseDouble(values[3]));
                pedido.setDtEntrega(LocalDate.parse(values[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                pedido.setFormaEntrega(formaEntregaRepository.findByFormaEntrega(values[5]));
                var cliente = clienteRepository.findByNome(values[6]);
                if (cliente == null) {
                    throw new BadRequestException("Cliente na linha " + linha);
                } else {
                    pedido.setCliente(cliente);
                }

                var formaPagamento = formaPagamentoRepository.findByFormaPagamento(values[7]);
                if (formaPagamento == null) {
                    throw new BadRequestException("Forma de Pagamento na linha " + linha);
                } else {
                    pedido.setFormaPagamento(formaPagamento);
                }
                Pedido pedidoCadastrado = pedidoRepository.save(pedido);

                Produto produto = new Produto();
                produto.setNome(values[10]);
                produto.setPreco(Double.parseDouble(values[11]));
                produto.setDescricao(values[12]);
                produto.setQtdDisponivel(Integer.parseInt(values[13]));
                var recheio = recheioRepository.findByNomeAndPreco(values[14], Double.parseDouble(values[15]));
                if (recheio == null) {
                    var recheioCadastrado = recheioService.cadastrarRecheio(new Recheio(values[14], Double.parseDouble(values[15])));
                    produto.setRecheio(recheioCadastrado);
                } else {
                    produto.setRecheio(recheio);
                }
                var massa = massaRepository.findByNome(values[16]);
                if (massa == null) {
                    throw new BadRequestException("Massa na linha " + linha);
                } else {
                    produto.setMassa(massa);
                }

                var cobertura = coberturaRepository.findByNome(values[17]);
                if (cobertura == null) {
                    throw new BadRequestException("Cobertura na linha " + linha);
                } else {
                    produto.setCobertura(cobertura);
                }

                var unidadeMedida = unidadeMedidaRepository.findByUnidadeMedida(values[18]);
                if (unidadeMedida == null) {
                    throw new BadRequestException("Unidade de Medida na linha " + linha);
                } else {
                    produto.setUnidadeMedida(unidadeMedida);
                }

                var tipoProduto = tipoProdutoRepository.findByTipo(values[19]);
                if (tipoProduto == null) {
                    throw new BadRequestException("Tipo de Produto na linha " + linha);
                } else {
                    produto.setTipoProduto(tipoProduto);
                }

                Produto produtoCadastrado = produtoService.cadastrarProduto(
                        produto,
                        produto.getRecheio().getIdRecheio(),
                        produto.getMassa().getIdMassa(),
                        produto.getCobertura().getIdCobertura(),
                        produto.getUnidadeMedida().getIdUnidadeMedida(),
                        produto.getTipoProduto().getIdTipoProduto()
                );

                Personalizacao personalizacao = new Personalizacao();
                personalizacao.setTema(values[20]);

                var recheioPersonalizacao = recheioRepository.findByNomeAndPreco(values[21], Double.parseDouble(values[22]));
                if (recheioPersonalizacao == null) {
                    var recheioCadastrado = recheioService.cadastrarRecheio(new Recheio(values[21], Double.parseDouble(values[22])));
                    personalizacao.setRecheio(recheioCadastrado);
                } else {
                    personalizacao.setRecheio(recheioPersonalizacao);
                }

                var massaPersonalizacao = massaRepository.findByNome(values[23]);
                if (massaPersonalizacao == null) {
                    throw new BadRequestException("Massa na linha " + linha);
                } else {
                    personalizacao.setMassa(massaPersonalizacao);
                }

                var coberturaPersonalizacao = coberturaRepository.findByNome(values[24]);
                if (coberturaPersonalizacao == null) {
                    throw new BadRequestException("Cobertura na linha " + linha);
                } else {
                    personalizacao.setCobertura(coberturaPersonalizacao);
                }
                Personalizacao personalizacaoCadastrada = personalizacaoRepository.save(personalizacao);

                ProdutoPedido produtoPedido = new ProdutoPedido();
                produtoPedido.setObservacoes(values[8]);
                produtoPedido.setQtProduto(Integer.parseInt(values[9]));
                produtoPedido.setPedido(pedidoCadastrado);
                produtoPedido.setProduto(produtoCadastrado);
                produtoPedido.setPersonalizacao(personalizacaoCadastrada);
                produtoPedidoRepository.save(produtoPedido);
            }
            return true;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public VisualizarPedidoDto visualizarPedido(Integer id) {
        VisualizarPedidoDto visualizarPedidoDto = new VisualizarPedidoDto();

        Optional<Pedido> pedido = pedidoRepository.findById(id);
        PedidoListagemDTO pedidoListagemDTO = PedidoMapper.toDto(pedido.get());

        visualizarPedidoDto.setPedidoListagemDTO(pedidoListagemDTO);

        List<ProdutoVisualizarPedidoDto> produtoVisualizarPedidoDtos = produtoPedidoRepository.findProdutoVisualizarPedido(pedido.get());

        visualizarPedidoDto.setProdutos(produtoVisualizarPedidoDtos);

        return visualizarPedidoDto;
    }

}
