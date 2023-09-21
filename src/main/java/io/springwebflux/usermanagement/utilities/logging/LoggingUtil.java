package io.springwebflux.usermanagement.utilities.logging;

import org.slf4j.MDC;
import org.springframework.http.HttpHeaders;
import reactor.core.publisher.Signal;

import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * utility specifically for logging context into MDC
 */
public class LoggingUtil {

    public static final String MDC_KEY = "MDC_KEY";
    public static final String CONTEXT_KEY = "CONTEXT_KEY";
    public static final String X_REQUEST_ID = "X-Request-ID";


    public static String getCorrelationId(HttpHeaders headers) {
        List<String> requestIdHeaders = headers.get(X_REQUEST_ID);
        return requestIdHeaders == null || requestIdHeaders.isEmpty()
                ? UUID.randomUUID().toString()
                : requestIdHeaders.get(0);
    }

    public static <T> Consumer<Signal<T>> logOnEach(Consumer<T> logStatement) {
        return signal -> {
            String contextValue = signal.getContextView().get(CONTEXT_KEY);
            try (MDC.MDCCloseable cMdc = MDC.putCloseable(MDC_KEY, contextValue)) {
                logStatement.accept(signal.get());
            }
        };
    }

}
