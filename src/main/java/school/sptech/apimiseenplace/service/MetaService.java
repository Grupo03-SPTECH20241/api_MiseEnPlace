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
    public Metas criarMeta(Metas metas){
        if (Objects.isNull(metas)) throw new BadRequestException("Meta");
        return repository.save(metas);
    }

    public List<Metas> listarMetas(){
        return repository.findAll();
    }
    public Metas encontrarPorId(int id){
        return repository.findById(id).orElseThrow(
                () -> new NaoEncontradoException("Meta")
        );
    }
    public Metas atualizarMeta(int id, Metas meta){
        Metas metaEncontrada = encontrarPorId(id);
        if (Objects.isNull(metaEncontrada)) return null;
        metaEncontrada.setValor(meta.getValor());
        metaEncontrada.setDtTermino(meta.getDtTermino());
        return repository.save(metaEncontrada);
    }

    public String deletarMetaPorId(int id){
        if(!repository.existsById(id)) throw new BadRequestException("Meta");
        repository.deleteById(id);
        return "Meta deletado com sucesso!";
    }
}
