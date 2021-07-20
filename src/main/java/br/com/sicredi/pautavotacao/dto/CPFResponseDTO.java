package br.com.sicredi.pautavotacao.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class CPFResponseDTO implements Serializable {

    private String status;

}
