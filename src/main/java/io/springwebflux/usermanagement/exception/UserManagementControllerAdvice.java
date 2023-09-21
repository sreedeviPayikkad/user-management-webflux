package io.springwebflux.usermanagement.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.bind.support.WebExchangeBindException;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
@Order(Ordered.HIGHEST_PRECEDENCE)
@RequiredArgsConstructor
/**
 * Handle custom exceptions including validation exceptions and runtime exceptions.
 */
public class UserManagementControllerAdvice {
    private final ObjectMapper objectMapper;

    @SneakyThrows
    @ExceptionHandler(WebExchangeBindException.class)
    public Mono<Void> handleEntityNotFoundException(final ServerWebExchange exchange, WebExchangeBindException ex) {
        final Map<String, String> errors = getValidationErrors(ex);
        exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        return writeResponse(exchange, objectMapper.writeValueAsBytes(errors));
    }

    @SneakyThrows
    @ExceptionHandler({CustomRuntimeException.class, Throwable.class})
    public Mono<Void> handleCustomRuntimeException(final ServerWebExchange exchange, CustomRuntimeException ex) {
        exchange.getResponse().setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
        log.info("runtime exception: {}", ex.getMessage());
        return writeResponse(exchange, objectMapper.writeValueAsBytes(Map.of("msg", "unable to process the request")));
    }

    private Map<String, String> getValidationErrors(final WebExchangeBindException validationEx) {
        return validationEx.getBindingResult().getFieldErrors().stream().collect(Collectors.toMap(FieldError::getField,
                error -> Optional.ofNullable(error.getDefaultMessage()).orElse("")));
    }

    private Mono<Void> writeResponse(final ServerWebExchange exchange, final byte[] responseBytes) {

        return exchange.getResponse().writeWith(Mono.just(exchange.getResponse().bufferFactory().wrap(responseBytes)));
    }

}
