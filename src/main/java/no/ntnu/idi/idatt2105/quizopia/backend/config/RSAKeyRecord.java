package no.ntnu.idi.idatt2105.quizopia.backend.config;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Record of RSA-keys used for encryption of jwt tokens.
 * @param rsaPublicKey
 * @param rsaPrivateKey
 */
@ConfigurationProperties(prefix = "jwt")
public record RSAKeyRecord(RSAPublicKey rsaPublicKey, RSAPrivateKey rsaPrivateKey) {

}
