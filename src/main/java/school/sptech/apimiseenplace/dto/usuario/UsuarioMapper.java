package school.sptech.apimiseenplace.dto.usuario;

import school.sptech.apimiseenplace.entity.Usuario;

import java.util.List;

public class UsuarioMapper {

    public static Usuario toEntity(UsuarioCriacaoDto dto, String nomeArquivo){

        Usuario entity = new Usuario();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setSenha(dto.getSenha());
        entity.setLogo(nomeArquivo);
        entity.setCnpj(dto.getCnpj());

        return entity;
    }

    public static Usuario toEntityFromEmail(UsuarioEmailDto dto){
        Usuario entity = new Usuario();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCnpj(dto.getCnpj());
        entity.setLogo(dto.getLogo().toString());
        return entity;
    }

    public static UsuarioCriacaoDto fromEmailToCriacao(UsuarioEmailDto dto){
        UsuarioCriacaoDto entity = new UsuarioCriacaoDto();
        entity.setNome(dto.getNome());
        entity.setEmail(dto.getEmail());
        entity.setCnpj(dto.getCnpj());
        entity.setLogo(dto.getLogo());
        return entity;
    }

    public static UsuarioListagemDto toDto(Usuario entity){
        UsuarioListagemDto dto = new UsuarioListagemDto();

        dto.setNome(entity.getNome());
        dto.setEmail(entity.getEmail());
        dto.setCnpj(entity.getCnpj());

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
