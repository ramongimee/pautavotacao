package br.com.sicredi.pautavotacao.utils.exceptions;

public class CPFException extends BaseException{
    public CPFException() {
        super("Ocorreu um erro ao acessar o serviço de consulta ao CPF");
    }
}
