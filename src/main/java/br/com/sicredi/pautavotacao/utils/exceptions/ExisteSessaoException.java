package br.com.sicredi.pautavotacao.utils.exceptions;

public class ExisteSessaoException extends BaseException{
    public ExisteSessaoException(String mensagem) {
        super(String.format("Existe sessão para esta pauta %s.", mensagem));
    }
}
