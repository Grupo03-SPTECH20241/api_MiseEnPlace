package school.sptech.apimiseenplace.dto.forma_entrega;

import school.sptech.apimiseenplace.dto.produto.ProdutoListagemDTO;
import school.sptech.apimiseenplace.dto.produto.ProdutoMapper;
import school.sptech.apimiseenplace.entity.FormaEntrega;
import school.sptech.apimiseenplace.entity.Produto;

import java.util.List;

public class FormaEntregaMapper {
    public static FormaEntrega toEntity(FormaEntregaCriacaoDto formaEntregaCriacaoDto) {
        FormaEntrega formaEntrega = new FormaEntrega();

        formaEntrega.setIdFormaEntrega(formaEntregaCriacaoDto.getIdFormaEntrega());
        formaEntrega.setFormaEntrega(formaEntregaCriacaoDto.getFormaEntrega());

        return formaEntrega;
    }

    public static FormaEntregaListagemDto toDto(FormaEntrega formaEntrega) {
        FormaEntregaListagemDto formaEntregaListagemDto = new FormaEntregaListagemDto();

        formaEntregaListagemDto.setIdFormaEntrega(formaEntrega.getIdFormaEntrega());
        formaEntregaListagemDto.setFormaEntrega(formaEntrega.getFormaEntrega());

        return formaEntregaListagemDto;
    }

    public static List<FormaEntregaListagemDto> toDto(List<FormaEntrega> produtos){
        if (produtos == null) return null;
        return produtos.stream().map(FormaEntregaMapper::toDto).toList();
    }
}
