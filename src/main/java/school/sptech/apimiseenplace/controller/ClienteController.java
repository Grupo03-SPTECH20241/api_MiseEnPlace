package school.sptech.apimiseenplace.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import school.sptech.apimiseenplace.dto.cliente.ClienteCriacaoDto;
import school.sptech.apimiseenplace.dto.cliente.ClienteListagemDto;
import school.sptech.apimiseenplace.dto.cliente.ClienteMapper;
import school.sptech.apimiseenplace.entity.Cliente;
import school.sptech.apimiseenplace.service.ClienteService;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/clientes")
@RequiredArgsConstructor
public class ClienteController {
    private final ClienteService clienteService;

    @PostMapping
    public ResponseEntity<ClienteListagemDto> cadastrarCliente(@RequestBody @Valid ClienteCriacaoDto clienteCriacaoDto) {
        Cliente cliente = ClienteMapper.toEntity(clienteCriacaoDto);
        cliente = clienteService.cadastrarCliente(
                cliente
        );
        ClienteListagemDto clienteListagemDto = ClienteMapper.toDto(cliente);
        URI uri = URI.create("/clientes/" + clienteListagemDto.getIdCliente());
        return ResponseEntity.created(uri).body(clienteListagemDto);
    }

    @GetMapping
    public ResponseEntity<List<ClienteListagemDto>> listarCLientes() {
        List<ClienteListagemDto> clienteListagemDtos = clienteService.listarClientes();
        if (clienteListagemDtos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clienteListagemDtos);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteListagemDto> atualizarCliente(@PathVariable int id, @RequestBody @Valid ClienteCriacaoDto clienteCriacaoDto) {
        Cliente cliente = ClienteMapper.toEntity(clienteCriacaoDto);
        cliente = clienteService.atualizarCliente(id, cliente);
        return ResponseEntity.ok(ClienteMapper.toDto(cliente));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable int id) {
        clienteService.deletarCliente(id);
        return ResponseEntity.noContent().build();
    }
}
