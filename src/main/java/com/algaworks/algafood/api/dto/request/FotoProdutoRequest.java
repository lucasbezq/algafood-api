package com.algaworks.algafood.api.dto.request;

import com.algaworks.algafood.core.validation.FileContentType;
import com.algaworks.algafood.core.validation.FileSize;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoRequest {

    @NotNull
    @FileSize(max = "500KB")
    @FileContentType(allowed = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ApiModelProperty(value = "Arquivo da foto do produto (máximo 500KB, tipos permitidos: JPEG e PNG)", required = true)
    private MultipartFile arquivo;

    @NotBlank
    @ApiModelProperty(value = "Descrição da foto do produto", required = true)
    private String descricao;

}
