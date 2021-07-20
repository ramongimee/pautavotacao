package br.com.sicredi.pautavotacao.resource;

import br.com.sicredi.pautavotacao.service.CPFService;
import br.com.sicredi.pautavotacao.utils.exceptions.PautaNaoEncontradaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.stream.Collectors;

@RestController
@RequestMapping("/cpf")
public class CPFResource {

    private final CPFService cpfService;
    private final ObjectMapper objectMapper;

    public CPFResource(final CPFService cpfService, final ObjectMapper objectMapper) {
        this.cpfService = cpfService;
        this.objectMapper = objectMapper;
    }

    @GetMapping("{cpf}")
    public ResponseEntity<?> verificaCPF(@PathVariable("cpf") String cpf) {
        return ResponseEntity.status(HttpStatus.OK).body(cpfService.habilitadoParaVotar(cpf));
    }

}
