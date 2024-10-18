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
            writer.println("ID Pedido;ID Produto;Nome Produto;Quantidade;Observações;Data Pedido");
            for (ProdutoPedido produtoPedido : listaPedidos) {
                writer.println(produtoPedido.getPedido().getIdPedido() + ";"
                        + produtoPedido.getProduto().getIdProduto() + ";"
                        + produtoPedido.getProduto().getNome() + ";"
                        + produtoPedido.getQtProduto() + ";"
                        + produtoPedido.getObservacoes() + ";"
                        + formatter.format(produtoPedido.getPedido().getDtPedido()));
            }
            return csv;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void base64ToCSV(String base64, String filePath) {
        try {
            // Decode the Base64 string into a byte array
            byte[] decodedBytes = Base64.getDecoder().decode(base64);

            // Convert the byte array into a string
            String content = new String(decodedBytes, StandardCharsets.UTF_8);

            // Write the string to a .csv file
            Path path = Paths.get(filePath);
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean importarPedidos(String base64) {
        base64ToCSV(base64, "pedidos.csv");

        try (Scanner scanner = new Scanner(new File("pedidos.csv"), StandardCharsets.UTF_8.name())) {
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                String[] values = line.split(";");

                Pedido pedido = new Pedido();
                pedido.setDtPedido(LocalDate.parse(values[0], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                pedido.setVlPedido(Double.parseDouble(values[1]));
                pedido.setStatus(values[2].charAt(0));
                pedido.setValorSinal(Double.parseDouble(values[3]));
                pedido.setDtEntrega(LocalDate.parse(values[4], DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                pedido.setFormaEntrega(formaEntregaRepository.findByFormaEntrega(values[5]));
                pedido.setCliente(clienteRepository.findByNome(values[6]));
                pedido.setFormaPagamento(formaPagamentoRepository.findByFormaPagamento(values[7]));
                Pedido pedidoCadastrado = pedidoRepository.save(pedido);

                Produto produto = new Produto();
                produto.setNome(values[10]);
                produto.setPreco(Double.parseDouble(values[11]));
                produto.setDescricao(values[12]);
                produto.setQtdDisponivel(Integer.parseInt(values[13]));
                produto.setRecheio(recheioRepository.findByNome(values[14]));
                produto.setMassa(massaRepository.findByNome(values[16]));
                produto.setCobertura(coberturaRepository.findByNome(values[17]));
                produto.setUnidadeMedida(unidadeMedidaRepository.findByUnidadeMedida(values[18]));
                produto.setTipoProduto(tipoProdutoRepository.findByTipo(values[19]));
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
                personalizacao.setRecheio(recheioRepository.findByNome(values[21]));
                personalizacao.setMassa(massaRepository.findByNome(values[22]));
                personalizacao.setCobertura(coberturaRepository.findByNome(values[23]));

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
