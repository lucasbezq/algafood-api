package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.UsuarioDTO;
import com.algaworks.algafood.api.dto.request.RestauranteRequest;
import com.algaworks.algafood.api.dto.request.UsuarioAtualizacaoRequest;
import com.algaworks.algafood.api.dto.request.UsuarioRequest;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.model.Usuario;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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

}
