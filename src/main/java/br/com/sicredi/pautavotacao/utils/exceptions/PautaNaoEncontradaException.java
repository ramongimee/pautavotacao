package br.com.sicredi.pautavotacao.utils.exceptions;

public class PautaNaoEncontradaException extends BaseException{
    public PautaNaoEncontradaException(String mensagem) {
        super(String.format("Pauta %s não existe.",mensagem));
    }
}
