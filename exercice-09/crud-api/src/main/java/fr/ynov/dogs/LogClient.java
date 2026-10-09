package fr.ynov.dogs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Map;

/** Envoie les logs de l'API CRUD vers l'API de logging via RestTemplate. */
@Component
public class LogClient {

    private static final Logger LOG = LoggerFactory.getLogger(LogClient.class);

    private final RestTemplate restTemplate;
    private final String url;

    public LogClient(@Value("${logs.api.url}") String url) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(2000);
        factory.setReadTimeout(2000);
        this.restTemplate = new RestTemplate(factory);
        this.url = url;
    }

    public void send(String level, String message, String method, String path) {
        Map<String, String> body = Map.of(
                "message", message,
                "source", "[CrudAPI] " + method + " " + path,
                "timestamp", Instant.now().toString(),
                "level", level);
        try {
            restTemplate.postForObject(url, body, String.class);
        } catch (Exception e) {
            // L'indisponibilité de l'API de logs ne doit jamais casser l'API CRUD
            LOG.warn("Impossible d'envoyer le log à {} : {}", url, e.getMessage());
        }
    }
}
