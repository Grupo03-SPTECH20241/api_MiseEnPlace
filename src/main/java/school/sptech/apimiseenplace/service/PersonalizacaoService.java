package school.sptech.apimiseenplace.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import school.sptech.apimiseenplace.dto.personalizacao.PersonalizacaoListagemDto;
import school.sptech.apimiseenplace.dto.personalizacao.PersonalizacaoMapper;
import school.sptech.apimiseenplace.dto.recheio.RecheioListagemDto;
import school.sptech.apimiseenplace.entity.Cobertura;
import school.sptech.apimiseenplace.entity.Massa;
import school.sptech.apimiseenplace.entity.Personalizacao;
import school.sptech.apimiseenplace.entity.Recheio;
import school.sptech.apimiseenplace.exception.BadRequestException;
import school.sptech.apimiseenplace.exception.NaoEncontradoException;
import school.sptech.apimiseenplace.repository.PersonalizacaoRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PersonalizacaoService {

    private final PersonalizacaoRepository repository;


    public List<RecheioListagemDto.PersonalizacaoListagem> listar(){
        return PersonalizacaoMapper.toDtoSemPersonalizacao(repository.findAll());
    }

    public List<RecheioListagemDto.PersonalizacaoListagem> listarWhereIdRecheioEquals(Integer idRecheio){
        return PersonalizacaoMapper.toDtoSemPersonalizacao(repository.findbyRecheioIdRecheioEquals(idRecheio));
    }

    public RecheioListagemDto.PersonalizacaoListagem updateRecheio(Recheio r, Integer id) {
        if(!repository.existsById(id)) throw new BadRequestException("Personalizacao");

        Optional<Personalizacao> p = repository.findById(id);
        if (p.isEmpty()) throw new NaoEncontradoException("Personalizacao");

        Personalizacao p2 = new Personalizacao();
        p2.setIdPersonalizacao(id);
        p2.setRecheio(r);
        p2.setCobertura(p.get().getCobertura());
        p2.setTema(p.get().getTema());
        p2.setMassa(p.get().getMassa());
        p2.setProdutoPedidos(p.get().getProdutoPedidos());

        return PersonalizacaoMapper.toDtoSemPersonalizacao(repository.save(p2));
    }

    public List<RecheioListagemDto.PersonalizacaoListagem> listarWhereIdCoberturaEquals(Integer idCobertura){
        return PersonalizacaoMapper.toDtoSemPersonalizacao(repository.findByCoberturaIdEquals(idCobertura));
    }
    public RecheioListagemDto.PersonalizacaoListagem updateCobertura(Cobertura c, Integer id){
        if(!repository.existsById(id)) throw new BadRequestException("Personalizacao");

        Optional<Personalizacao> p = repository.findById(id);
        if (p.isEmpty()) throw new NaoEncontradoException("Personalizacao");

        Personalizacao p2 = new Personalizacao();
        p2.setIdPersonalizacao(id);
        p2.setRecheio(p.get().getRecheio());
        p2.setCobertura(c);
        p2.setTema(p.get().getTema());
        p2.setMassa(p.get().getMassa());
        p2.setProdutoPedidos(p.get().getProdutoPedidos());

        return PersonalizacaoMapper.toDtoSemPersonalizacao(repository.save(p2));
    }

    public List<RecheioListagemDto.PersonalizacaoListagem> listarWhereIdMassaEquals(Integer idMassa){
        return PersonalizacaoMapper.toDtoSemPersonalizacao(repository.findByMassaIdEquals(idMassa));
    }
    public RecheioListagemDto.PersonalizacaoListagem updateMassa(Massa m, Integer id){
        if(!repository.existsById(id)) throw new BadRequestException("Personalizacao");

        Optional<Personalizacao> p = repository.findById(id);
        if (p.isEmpty()) throw new NaoEncontradoException("Personalizacao");

        Personalizacao p2 = new Personalizacao();
        p2.setIdPersonalizacao(id);
        p2.setRecheio(p.get().getRecheio());
        p2.setCobertura(p.get().getCobertura());
        p2.setTema(p.get().getTema());
        p2.setMassa(m);
        p2.setProdutoPedidos(p.get().getProdutoPedidos());

        return PersonalizacaoMapper.toDtoSemPersonalizacao(repository.save(p2));
    }
}
