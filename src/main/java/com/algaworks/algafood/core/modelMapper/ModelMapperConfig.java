package com.algaworks.algafood.core.modelMapper;

import com.algaworks.algafood.api.dto.EnderecoDTO;
import com.algaworks.algafood.api.dto.request.ItemPedidoRequest;
import com.algaworks.algafood.domain.model.Endereco;
import com.algaworks.algafood.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        var modelMapper = new ModelMapper();

        modelMapper.createTypeMap(ItemPedidoRequest.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));

        var enderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
        enderecoDTOTypeMap.<String>addMapping(
                src -> src.getCidade().getEstado().getNome(),
                (destino, valor) -> destino.getCidade().setEstado(valor)
        );

        return modelMapper;
    }

}
