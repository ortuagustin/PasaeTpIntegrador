package ar.edu.unlp.pasae.tp_integrador.config;

public class JwtConfig {
  public static final String SECRET = "SecretKeyToGenJWTs";
  public static final long EXPIRATION_TIME = 864_000_000; // 10 days
  public static final String COOKIE_NAME = "TOKEN";
}