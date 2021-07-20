package br.com.sicredi.pautavotacao.service;

import br.com.sicredi.pautavotacao.configuration.Constants;
import br.com.sicredi.pautavotacao.dto.CPFResponseDTO;
import br.com.sicredi.pautavotacao.utils.exceptions.BaseException;
import br.com.sicredi.pautavotacao.utils.exceptions.CPFException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import java.io.Serializable;

@Service
public class CPFService implements Serializable {



    public String habilitadoParaVotar(String cpf) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = Constants.getInstance().getServiceUrl()+cpf;
            ResponseEntity<CPFResponseDTO> exchange = restTemplate.exchange(url, HttpMethod.GET, null, CPFResponseDTO.class);
            System.out.println(exchange);
            return exchange.getBody().getStatus();
        } catch (HttpStatusCodeException hsce) {
            if (hsce.getStatusCode()== HttpStatus.NOT_FOUND)
                throw new BaseException("CPF Invalido");

            throw new CPFException();
        }
    }
}
