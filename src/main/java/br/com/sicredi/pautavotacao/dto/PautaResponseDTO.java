package br.com.sicredi.pautavotacao.dto;

import br.com.sicredi.pautavotacao.utils.OpcaoVoto;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Map;

@Data
public class PautaResponseDTO implements Serializable {

    private String id;

    private String titulo;

    private String descricao;

    private Map<OpcaoVoto, Long> resultado;

}
