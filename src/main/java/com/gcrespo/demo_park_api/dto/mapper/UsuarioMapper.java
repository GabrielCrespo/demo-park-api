package com.gcrespo.demo_park_api.dto.mapper;

import com.gcrespo.demo_park_api.dto.UsuarioCreateDto;
import com.gcrespo.demo_park_api.dto.UsuarioResponseDto;
import com.gcrespo.demo_park_api.entity.Usuario;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;

import java.util.List;

public class UsuarioMapper {

    public static Usuario toUsuario(UsuarioCreateDto usuarioCreateDto) {
        return new ModelMapper().map(usuarioCreateDto, Usuario.class);
    }

    public static UsuarioResponseDto toUsuarioResponseDto(Usuario usuario) {
        String role = usuario.getRole().name().substring("ROLE_".length());

        PropertyMap<Usuario, UsuarioResponseDto> props = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setRole(role);
            }
        };

        ModelMapper mapper = new ModelMapper();
        mapper.addMappings(props);

        return mapper.map(usuario, UsuarioResponseDto.class);
    }

    public static List<UsuarioResponseDto> toUsuarioResponseListDto(List<Usuario> usuarios) {
        return usuarios.stream().map(UsuarioMapper::toUsuarioResponseDto).toList();
    }

}
