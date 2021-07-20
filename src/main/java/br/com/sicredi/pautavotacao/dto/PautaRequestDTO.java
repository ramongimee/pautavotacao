package br.com.sicredi.pautavotacao.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PautaRequestDTO implements Serializable {

    @NotNull
    @NotBlank
    @NotEmpty
    private String titulo;

    @NotNull
    @NotBlank
    @NotEmpty
    private String descricao;


}
