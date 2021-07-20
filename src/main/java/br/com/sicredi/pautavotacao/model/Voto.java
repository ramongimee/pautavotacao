package br.com.sicredi.pautavotacao.model;

import br.com.sicredi.pautavotacao.utils.OpcaoVoto;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
@Entity
@Table(name = "VOTOS")
public class Voto implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="opcao")
    private OpcaoVoto opcaoVoto;

    @Column(name="data")
    private LocalDateTime dataHora;

    @Column(name="id_eleitor")
    private Long idEleitor;

    @ManyToOne
    @JoinColumn(name = "id_sessao")
    private SessaoVotacao sessaoVotacao;

}
