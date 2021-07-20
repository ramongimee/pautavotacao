package br.com.sicredi.pautavotacao.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class SessaoRequestDTO implements Serializable {

    private LocalDateTime dataFechamento;

}
