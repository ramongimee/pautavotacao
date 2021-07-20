package br.com.sicredi.pautavotacao.utils.exceptions;

public class SessaoNaoEncontradaException extends BaseException{
    public SessaoNaoEncontradaException() {
        super("Sessão de votação não encontrada");
    }
}
