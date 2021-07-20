package br.com.sicredi.pautavotacao.dto;

import br.com.sicredi.pautavotacao.utils.OpcaoVoto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class VotoRequestDTO implements Serializable {

    @NotNull
    private Long idEleitor;

    @NotNull
    private OpcaoVoto opcaoVoto;


}
