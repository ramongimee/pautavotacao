package br.com.sicredi.pautavotacao.service;

import br.com.sicredi.pautavotacao.configuration.Constants;
import br.com.sicredi.pautavotacao.dto.PautaResponseDTO;
import br.com.sicredi.pautavotacao.model.Pauta;
import br.com.sicredi.pautavotacao.model.SessaoVotacao;
import br.com.sicredi.pautavotacao.model.Voto;
import br.com.sicredi.pautavotacao.repository.PautaRepository;
import br.com.sicredi.pautavotacao.repository.SessaoRepository;
import br.com.sicredi.pautavotacao.repository.VotoRepository;
import br.com.sicredi.pautavotacao.utils.OpcaoVoto;
import br.com.sicredi.pautavotacao.utils.exceptions.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PautaService {

    private final PautaRepository pautaRepository;
    private final SessaoRepository sessaoRepository;
    private final VotoRepository votoRepository;
    private final ObjectMapper objectMapper;

    public PautaService(final PautaRepository pautaRepository, final SessaoRepository sessaoRepository, final VotoRepository votoRepository, final ObjectMapper objectMapper) {
        this.pautaRepository = pautaRepository;
        this.sessaoRepository = sessaoRepository;
        this.votoRepository = votoRepository;
        this.objectMapper = objectMapper;
    }

    public List<Pauta> getPautas() {
        return pautaRepository.findAll();
    }

    public Optional<Pauta> getPautaPorId(Long id) {
        Optional<Pauta> pautaOptional = pautaRepository.findById(id);
        return pautaOptional;
    }

    @Transactional
    public Pauta criarNovaPauta(Pauta pauta) {
        pautaRepository.save(pauta);
        return pauta;
    }

    @Transactional
    public void iniciarSessaoVotacao(Long idPauta, LocalDateTime dataFechamento) {
        Optional<Pauta> pauta = getPautaPorId(idPauta);

        if(!pauta.isPresent()){
            throw new PautaNaoEncontradaException(idPauta.toString());
        }

        if(getSessaoVotacao(pauta.get()).isPresent()){
            throw new ExisteSessaoException(idPauta.toString());
        }

        if (dataFechamento != null && LocalDateTime.now().isAfter(dataFechamento)) {
            throw new DataFechamentoSessaoException();
        }

        criaSessaoVotacao(pauta.get(), dataFechamento);
    }

    private void criaSessaoVotacao(Pauta pauta, LocalDateTime dataFechamento) {
        SessaoVotacao sessaoVotacao = SessaoVotacao.builder()
                .dataAbertura(LocalDateTime.now())
                .dataFechamento(dataFechamento(dataFechamento))
                .pauta(pauta)
                .build();

        sessaoRepository.save(sessaoVotacao);

    }

    private LocalDateTime dataFechamento(LocalDateTime dataFechamento) {
        return dataFechamento == null ? LocalDateTime.now().plusSeconds(Constants.getInstance().getTimeSessionSeconds()) : dataFechamento;
    }

    private Optional<SessaoVotacao> getSessaoVotacao(Pauta pauta) {
        Optional<SessaoVotacao> byPautaEquals = sessaoRepository.findByPauta(pauta);
        return byPautaEquals;
    }

    @Transactional
    public void votar(Long idPauta, Voto voto) {
        SessaoVotacao sessaoVotacao = getSessaoVotacao(getPautaPorId(idPauta)
                .orElseThrow(() -> new PautaNaoEncontradaException(idPauta.toString())))
                .orElseThrow(SessaoNaoEncontradaException::new);

        if (LocalDateTime.now().isAfter(sessaoVotacao.getDataFechamento())) {
            throw new SessaoFechadaException();
        }

        voto.setSessaoVotacao(sessaoVotacao);
        voto.setDataHora(LocalDateTime.now());

        if (eleitorVotou(voto)) {
            throw new EleitorVotouException();
        }

        votoRepository.save(voto);
    }

    public Map<OpcaoVoto, Long> result(Pauta pauta) {
        return getSessaoVotacao(pauta).map(sv -> sv.getVotos()
                .stream()
                .collect(Collectors.groupingBy(Voto::getOpcaoVoto,
                        Collectors.counting()))).orElse(null);
    }


    private boolean eleitorVotou(Voto voto){
        boolean jaVotou = false;
        Optional<Voto> optionalVoto = votoRepository.findBySessaoVotacaoEqualsAndIdEleitorEquals(voto.getSessaoVotacao(), voto.getIdEleitor());

        if(optionalVoto.isPresent()){
            jaVotou = true;
        }

        return jaVotou;
    }

    public PautaResponseDTO getPautaResponse(Pauta pauta) {
        PautaResponseDTO pautaResponse = objectMapper.convertValue(pauta, PautaResponseDTO.class);
        pautaResponse.setResultado(result(pauta));

        return pautaResponse;
    }
}
