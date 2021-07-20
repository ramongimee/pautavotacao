package br.com.sicredi.pautavotacao.repository;

import br.com.sicredi.pautavotacao.model.Pauta;
import br.com.sicredi.pautavotacao.model.SessaoVotacao;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessaoRepository extends JpaRepository<SessaoVotacao,Long> {

    Optional<SessaoVotacao> findByPauta(Pauta pauta);

}
