package ar.edu.unlp.pasae.tp_integrador.aspects;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

@Aspect
@Configuration
public class ExceptionLoggerAspect {
  private static final Logger logger = LoggerFactory.getLogger(ExceptionLoggerAspect.class);

  @AfterThrowing(value = "execution(* ar.edu.unlp.pasae.tp_integrador.services.*.*(..))", throwing = "exception")
  public void after(final JoinPoint joinPoint, final Exception exception) throws Exception {
    logger.error(exception.getLocalizedMessage());
  }
}