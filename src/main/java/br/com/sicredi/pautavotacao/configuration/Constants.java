package br.com.sicredi.pautavotacao.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class Constants {

    public static Constants instance = null;

    @Value("${configuration.tempo-sessao-segundos}")
    Integer timeSessionSeconds;

    @Value("${configuration.service-url}")
    String serviceUrl;

    /**
     * Método expõem as constantes para serem utilizadas nas classes.
     * Método é iniciado com a api.
     */
    @PostConstruct
    public void init(){
        instance = this;
    }

    public static Constants getInstance() {
        return instance;
    }

    public Integer getTimeSessionSeconds() {
        return timeSessionSeconds;
    }

    public String getServiceUrl() {
        return serviceUrl;
    }

    public void setServiceUrl(String serviceUrl) {
        this.serviceUrl = serviceUrl;
    }
}