package school.sptech.crudloginsenha.dto.usuario;

import school.sptech.crudloginsenha.entity.Usuario;

import java.time.LocalDate;
import java.util.List;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioCriacaoDto dto){

        Usuario entity = new Usuario();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(dto.getSenha());
        entity.setLogado(dto.getLogado());

        return entity;
    }

    public static UsuarioListagemDto toDto(Usuario entity){
        UsuarioListagemDto dto = new UsuarioListagemDto();

        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setLogado(entity.getLogado());
        return dto;

    }

    public static List<UsuarioListagemDto> toDto(List<Usuario> entities){
        return entities.stream().map(UsuarioMapper::toDto).toList();
    }

    public static UsuarioTokenDto of(Usuario usuario, String token){
        UsuarioTokenDto usuarioTokenDto = new UsuarioTokenDto();

        usuarioTokenDto.setUserId(usuario.getIdUsuario());
        usuarioTokenDto.setEmail(usuario.getEmail());
        usuarioTokenDto.setNome(usuario.getNome());
        usuarioTokenDto.setToken(token);
        return usuarioTokenDto;
    }
}
