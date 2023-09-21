package io.springwebflux.usermanagement.aspect;

import io.springwebflux.usermanagement.exception.CustomRuntimeException;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.reactivestreams.Publisher;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static io.springwebflux.usermanagement.utilities.logging.LoggingUtil.CONTEXT_KEY;
import static io.springwebflux.usermanagement.utilities.logging.LoggingUtil.MDC_KEY;

@Aspect
@Component
@Slf4j
/**
 * Aspect intended to add the values from Context into the MDC for logging purposes
 * @author Sreedevi Sasikumar
 */
public class MdcAspect {

    private final String EXCEPTION_MSG = "exception while setting MDC in aspect";

    /**
     * @param joinPoint to extract returntype and to proceed with execuiton
     * @return Any Publisher types depending on controller endpoint return
     */
    @Around("execution(* io.springwebflux.usermanagement.controller..*.*(..))")
    @SneakyThrows()
    public Publisher<?> aroundEndpointExecution(ProceedingJoinPoint joinPoint) {
        log.info("setting value from context into MDC for logging before controller endpoint execution");
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        Class<?> returnType = methodSignature.getReturnType();
        if (Mono.class.isAssignableFrom(returnType)) {
            return handleMono(joinPoint, returnType);
        } else if (Flux.class.isAssignableFrom(returnType)) {
            return handleFlux(joinPoint, returnType);
        } else {
            return (Publisher<?>) joinPoint.proceed();
        }
    }

    private Flux<?> handleFlux(ProceedingJoinPoint joinPoint, Class<?> returnType) {
        return Flux.deferContextual(contextView -> {
            try (MDC.MDCCloseable mdcCloseable = MDC.putCloseable(MDC_KEY, contextView.get(CONTEXT_KEY))) {
                return (Flux<?>) joinPoint.proceed();
            } catch (Throwable e) {
                throw new CustomRuntimeException(e, EXCEPTION_MSG);
            }
        });
    }

    private Mono<Object> handleMono(ProceedingJoinPoint joinPoint, Class<?> returnType) {
        return Mono.deferContextual(contextView -> {
            try (MDC.MDCCloseable mdcCloseable = MDC.putCloseable(MDC_KEY, contextView.get(CONTEXT_KEY))) {
                return (Mono<?>) joinPoint.proceed();
            } catch (Throwable e) {
                throw new CustomRuntimeException(e, EXCEPTION_MSG);
            }
        });
    }
}
