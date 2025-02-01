package com.algaworks.algafood.api.dto.request;

import com.algaworks.algafood.core.validation.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoRequest {

    @NotNull
    @FileSize(max = "500KB")
    private MultipartFile arquivo;

    @NotBlank
    private String descricao;

}
