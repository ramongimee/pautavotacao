package br.com.sicredi.pautavotacao.configuration.validation;

import br.com.sicredi.pautavotacao.utils.exceptions.BaseException;
import br.com.sicredi.pautavotacao.utils.exceptions.CPFException;
import br.com.sicredi.pautavotacao.utils.exceptions.PautaNaoEncontradaException;
import br.com.sicredi.pautavotacao.utils.exceptions.SessaoNaoEncontradaException;
import org.hibernate.resource.transaction.backend.jta.internal.synchronization.ExceptionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class ErrorValidationHandler {

    private final MessageSource messageSource;

    public ErrorValidationHandler(final MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorFormDTO> handle(MethodArgumentNotValidException exception){

        List<ErrorFormDTO> errorFormDTOS = new ArrayList<>();

        List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        fieldErrors.forEach(e -> {
            String message = messageSource.getMessage(e , LocaleContextHolder.getLocale());
            errorFormDTOS.add(ErrorFormDTO.builder().field(e.getField()).error(message).build());
        });
        return errorFormDTOS;
    }

    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BaseException.class)
    public ResponseEntity<?> handleBaseException(BaseException exception){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorFormDTO.builder().error(exception.getMessage()).field("").build());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(PautaNaoEncontradaException.class)
    public ResponseEntity<?> handlePautaNaoEncontradaException(PautaNaoEncontradaException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorFormDTO.builder().error(exception.getMessage()).field("").build());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(SessaoNaoEncontradaException.class)
    public ResponseEntity<?> handleSessaoNaoEncontradaException(SessaoNaoEncontradaException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorFormDTO.builder().error(exception.getMessage()).field("").build());
    }

    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ExceptionHandler(CPFException.class)
    public ResponseEntity<?> handleSessaoNaoEncontradaException(CPFException exception){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ErrorFormDTO.builder().error(exception.getMessage()).field("").build());
    }
}
