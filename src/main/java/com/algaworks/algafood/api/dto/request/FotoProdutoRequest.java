package com.algaworks.algafood.api.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FotoProdutoRequest {

    private MultipartFile arquivo;
    private String descricao;

}
