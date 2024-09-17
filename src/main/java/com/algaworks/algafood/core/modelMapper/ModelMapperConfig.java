package com.algaworks.algafood.core.modelMapper;

import com.algaworks.algafood.api.dto.EnderecoDTO;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();
        var enderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);

        enderecoDTOTypeMap.<String>addMapping(
                src -> src.getCidade().getEstado().getNome(),
                (destino, valor) -> destino.getCidade().setEstado(valor)
        );

        return modelMapper;
    }

}
