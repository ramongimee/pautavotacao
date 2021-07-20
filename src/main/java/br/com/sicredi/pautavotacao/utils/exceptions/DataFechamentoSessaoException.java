package br.com.sicredi.pautavotacao.utils.exceptions;

public class DataFechamentoSessaoException extends BaseException{

    public DataFechamentoSessaoException() {
        super("A Data de Fechamento da sessão é inferior da atual");
    }
}
