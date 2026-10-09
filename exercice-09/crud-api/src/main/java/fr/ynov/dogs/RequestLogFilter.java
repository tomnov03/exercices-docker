package fr.ynov.dogs;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/** Log INFO pour chaque appel réussi (statut < 400). Les erreurs sont loguées par ApiExceptionHandler. */
@Component
public class RequestLogFilter extends OncePerRequestFilter {

    private final LogClient logClient;

    public RequestLogFilter(LogClient logClient) {
        this.logClient = logClient;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        chain.doFilter(request, response);
        if (response.getStatus() < 400) {
            logClient.send("INFO", "Appel réussi (HTTP " + response.getStatus() + ")",
                    request.getMethod(), request.getRequestURI());
        }
    }
}
