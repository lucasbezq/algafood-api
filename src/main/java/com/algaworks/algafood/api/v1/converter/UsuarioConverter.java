package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.dto.request.UsuarioAtualizacaoRequest;
import com.algaworks.algafood.api.v1.dto.request.UsuarioRequest;
import com.algaworks.algafood.api.v1.dto.request.UsuarioSenhaRequest;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Usuario toDomain(UsuarioRequest usuarioRequest) {
        return modelMapper.map(usuarioRequest, Usuario.class);
    }

    public void copyToDomain(UsuarioAtualizacaoRequest usuarioAtualizacaoRequest, Usuario usuario) {
        modelMapper.map(usuarioAtualizacaoRequest, usuario);
    }

    public void copyToDomain(UsuarioSenhaRequest usuarioSenhaRequest, Usuario usuario) {
        modelMapper.map(usuarioSenhaRequest, usuario);
    }

}
