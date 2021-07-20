package br.com.sicredi.pautavotacao.utils.exceptions;

public class SessaoFechadaException extends BaseException{
    public SessaoFechadaException() {
        super("Sessão fechada, não é possivel realizar votações.");
    }
}
