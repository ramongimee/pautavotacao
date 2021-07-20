package br.com.sicredi.pautavotacao.utils.exceptions;

public class BaseException extends RuntimeException {
    public BaseException(String mensagem) {
        super(mensagem,null,true,false);
    }
}
