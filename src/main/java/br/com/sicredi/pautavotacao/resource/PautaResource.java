package br.com.sicredi.pautavotacao.resource;

import br.com.sicredi.pautavotacao.dto.PautaRequestDTO;
import br.com.sicredi.pautavotacao.dto.PautaResponseDTO;
import br.com.sicredi.pautavotacao.dto.SessaoRequestDTO;
import br.com.sicredi.pautavotacao.dto.VotoRequestDTO;
import br.com.sicredi.pautavotacao.model.Pauta;
import br.com.sicredi.pautavotacao.model.Voto;
import br.com.sicredi.pautavotacao.service.PautaService;
import br.com.sicredi.pautavotacao.utils.exceptions.PautaNaoEncontradaException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/pautas")
public class PautaResource {

    private final PautaService pautaService;
    private final ObjectMapper objectMapper;

    public PautaResource(final PautaService pautaService, final ObjectMapper objectMapper) {
        this.pautaService = pautaService;
        this.objectMapper = objectMapper;
    }

    @PostMapping
    public ResponseEntity<?> criarPauta(@RequestBody @Valid PautaRequestDTO pautaRequestDTO) {

        Pauta pauta = objectMapper.convertValue(pautaRequestDTO, Pauta.class);

        pauta = pautaService.criarNovaPauta(pauta);

        return ResponseEntity.status(HttpStatus.CREATED).body(objectMapper.convertValue(pauta, PautaResponseDTO.class));
    }

    @GetMapping
    public ResponseEntity<List<PautaResponseDTO>> getPautas() {
        return ResponseEntity.status(HttpStatus.OK).body(pautaService.getPautas().stream()
                .map(pautaService::getPautaResponse)
                .collect(Collectors.toList()));
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getPauta(@PathVariable("id") Long idPauta) {

        return ResponseEntity.status(HttpStatus.OK).body(pautaService.getPautaResponse(pautaService.getPautaPorId(idPauta)
                    .orElseThrow(() -> new PautaNaoEncontradaException(idPauta.toString()))));
    }

    @PostMapping("/iniciar-sessao/{idPauta}")
    public ResponseEntity<?> iniciarSessaoVotacao(@PathVariable("idPauta") Long idPauta, @RequestBody SessaoRequestDTO sessao) {

        pautaService.iniciarSessaoVotacao(idPauta, sessao != null ? sessao.getDataFechamento() : null);

        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/votar/{idPauta}")
    public ResponseEntity<?> votar(@PathVariable("idPauta") Long idPauta, @RequestBody VotoRequestDTO votoRequest) {

        pautaService.votar(idPauta, objectMapper.convertValue(votoRequest, Voto.class));

        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
