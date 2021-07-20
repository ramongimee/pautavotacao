package br.com.sicredi.pautavotacao.utils.exceptions;

public class EleitorVotouException extends BaseException {
    public EleitorVotouException() {
        super("Eleitor ja realizou o seu voto.");
    }
}
