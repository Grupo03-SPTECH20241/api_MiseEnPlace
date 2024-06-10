package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.metas.MetaCriacaoDto;
import school.sptech.apimiseenplace.dto.metas.MetaMapper;
import school.sptech.apimiseenplace.dto.vwQuantidadeVendidaValorVendido.vwQuantidadeVendidaValorVendidoMes.QuantidadeVendidaValorVendidoDto;
import school.sptech.apimiseenplace.entity.Metas;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.MetaRepository;
import school.sptech.apimiseenplace.repository.QuantidadeVendidaValorVendidoRepository;
import school.sptech.apimiseenplace.view.graficoQuantidadeVendidaValorVendido.VwQuantidadeVendidaValorVendido;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class MetaService {
    private final MetaRepository repository;
    private final QuantidadeVendidaValorVendidoRepository quantidadeVendidaValorVendidoRepository;

    public Double AcompanhamentoMeta(){
        List<VwQuantidadeVendidaValorVendido> qtd = quantidadeVendidaValorVendidoRepository.findAll();
        Double valorVendido = 0.0;
        for (VwQuantidadeVendidaValorVendido q : qtd){
            valorVendido += q.getValorVendido();
        }

        return valorVendido;
    }
    public Metas criarMeta(MetaCriacaoDto metaCriacaoDto){
        if (Objects.isNull(metaCriacaoDto)) throw new BadRequestException(" ");
        return repository.save(MetaMapper.toEntity(metaCriacaoDto));
    }

    public List<Metas> listarMetas(){
        return repository.findAll();
    }
    public Metas encontrarPorId(int id){
        return repository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Meta")
        );
    }
    public Metas atualizarMeta(int id, MetaCriacaoDto metaCriacaoDto){
        Metas meta = encontrarPorId(id);
        if (Objects.isNull(meta)) return null;
        meta.setValor(metaCriacaoDto.getValor());
        meta.setDtTermino(metaCriacaoDto.getDtTermino());
        return repository.save(meta);
    }

    public void deletarMetaPorId(int id){
        if(!repository.existsById(id)) throw new BadRequestException("Meta");
        repository.deleteById(id);
    }
}
