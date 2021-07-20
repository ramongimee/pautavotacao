package br.com.sicredi.pautavotacao.repository;

import br.com.sicredi.pautavotacao.model.SessaoVotacao;
import br.com.sicredi.pautavotacao.model.Voto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface VotoRepository extends JpaRepository<Voto,Long> {


    Optional<Voto> findBySessaoVotacaoEqualsAndIdEleitorEquals(SessaoVotacao sessaoVotacao, Long idEleitor);

}
