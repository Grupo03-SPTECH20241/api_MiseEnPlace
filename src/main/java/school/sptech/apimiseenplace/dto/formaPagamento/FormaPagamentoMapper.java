package school.sptech.apimiseenplace.dto.formaPagamento;

import school.sptech.apimiseenplace.entity.FormaPagamento;

import java.util.List;

public class FormaPagamentoMapper {
    public static FormaPagamento toEntity(FormaPagamentoCriacaoDto dto){
        FormaPagamento formaPagamento = new FormaPagamento();
        formaPagamento.setFormaPagamento(dto.getFormaPagamento());
        return formaPagamento;
    }

    public static FormaPagamentoListagemDto toDto(FormaPagamento formaPagamento){
        FormaPagamentoListagemDto dto = new FormaPagamentoListagemDto();
        dto.setIdFormaPagamento(formaPagamento.getIdFormaPagamento());
        dto.setFormaPagamento(formaPagamento.getFormaPagamento());
        return dto;
    }

    public static List<FormaPagamentoListagemDto> toDto(List<FormaPagamento> entities){
        return entities.stream().map(FormaPagamentoMapper::toDto).toList();
    }
}
