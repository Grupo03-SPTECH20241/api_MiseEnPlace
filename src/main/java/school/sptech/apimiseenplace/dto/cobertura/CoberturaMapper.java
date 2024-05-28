package school.sptech.apimiseenplace.dto.cobertura;

import school.sptech.apimiseenplace.dto.massa.MassaSemPersonalizacaoDto;
import school.sptech.apimiseenplace.dto.recheio.RecheioListagemDto;
import school.sptech.apimiseenplace.dto.recheio.RecheioSemPersonalizacaoDto;
import school.sptech.apimiseenplace.entity.Cobertura;
import school.sptech.apimiseenplace.entity.Personalizacao;
import school.sptech.apimiseenplace.entity.Recheio;

import java.util.ArrayList;
import java.util.List;

public class CoberturaMapper {

    public static CoberturaSemPersonalizacaoDto toSemPersonalizacaoDto(Cobertura entity){

        CoberturaSemPersonalizacaoDto dto = new CoberturaSemPersonalizacaoDto();

        dto.setIdCobertura(entity.getIdCobertura());
        dto.setNome(entity.getNome());

        return dto;
    }

    public static List<CoberturaSemPersonalizacaoDto> toSemPersonalizacaoDto(List<Cobertura> entities){
        return entities.stream().map(CoberturaMapper::toSemPersonalizacaoDto).toList();
    }

    public static CoberturaListagemDto toDto(Cobertura entity){
        CoberturaListagemDto dto = new CoberturaListagemDto();

        dto.setIdCobertura(entity.getIdCobertura());
        dto.setNome(entity.getNome());
        dto.setPersonalizacoes(toPersonalizacoesDto(entity.getPersonalizacoes()));
        return dto;
    }

    public static Cobertura toEntity(CoberturaCriacaoDto dto){

        Cobertura cobertura = new Cobertura();
        cobertura.setNome(dto.getNome());

        return cobertura;
    }

    public static List<CoberturaListagemDto> toDto(List<Cobertura> entities){
        return entities.stream().map(CoberturaMapper::toDto).toList();
    }

    public static List<RecheioListagemDto.PersonalizacaoListagem> toPersonalizacoesDto(List<Personalizacao> entities){
        if (entities == null) return null;
        List<RecheioListagemDto.PersonalizacaoListagem> dto = new ArrayList<>();

        for (Personalizacao p : entities){
            RecheioListagemDto.PersonalizacaoListagem p2 = new RecheioListagemDto.PersonalizacaoListagem();
            CoberturaSemPersonalizacaoDto c = new CoberturaSemPersonalizacaoDto();
            RecheioSemPersonalizacaoDto r = new RecheioSemPersonalizacaoDto();
            MassaSemPersonalizacaoDto m = new MassaSemPersonalizacaoDto();

            r.setIdRecheio(p.getRecheio().getIdRecheio());
            r.setNome(p.getRecheio().getNome());
            p2.setRecheio(r);

            p2.setTema(p.getTema());

            c.setNome(p.getCobertura().getNome());
            c.setIdCobertura(p.getCobertura().getIdCobertura());
            p2.setCobertura(c);

            m.setIdMassa(p.getMassa().getIdMassa());
            m.setNome(p.getMassa().getNome());
            p2.setMassa(m);

            dto.add(p2);
        }
        return dto;
    }
}
