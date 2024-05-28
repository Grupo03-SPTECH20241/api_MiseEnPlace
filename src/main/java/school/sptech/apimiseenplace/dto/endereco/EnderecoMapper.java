package school.sptech.apimiseenplace.dto.endereco;

import school.sptech.apimiseenplace.entity.Endereco;

import java.util.List;

public class EnderecoMapper {
    public static Endereco toEntity(EnderecoCriacaoDto enderecoCriacaoDto) {
        Endereco endereco = new Endereco();

        endereco.setLogradouro(enderecoCriacaoDto.getLogradouro());
        endereco.setComplemento(enderecoCriacaoDto.getComplemento());
        endereco.setCep(enderecoCriacaoDto.getCep());
        endereco.setNumero(enderecoCriacaoDto.getNumero());

        return endereco;
    }

    public static EnderecoListagemDto toDto(Endereco endereco) {
        EnderecoListagemDto enderecoListagemDto = new EnderecoListagemDto();

        enderecoListagemDto.setIdEndereco(endereco.getIdEndereco());
        enderecoListagemDto.setLogradouro(endereco.getLogradouro());
        enderecoListagemDto.setComplemento(endereco.getComplemento());
        enderecoListagemDto.setCep(endereco.getCep());
        enderecoListagemDto.setNumero(endereco.getNumero());

        return enderecoListagemDto;
    }

    public static List<EnderecoListagemDto> toDto(List<Endereco> enderecos) {
        if (enderecos == null) return null;
        return enderecos.stream().map(EnderecoMapper::toDto).toList();
    }
}
