package br.com.rocketseat.springboot.gestao_vagas.providers;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JWTCompanyProvider {

  @Value("${security.token.secret}")
  private String secretKey;

  public DecodedJWT validateToken(String token) {
    token = token.replace("Bearer ", "");

    var algorithm = Algorithm.HMAC256(secretKey);

    try {
      var tokenDecoded = JWT.require(algorithm).build().verify(token);
      return tokenDecoded;
    } catch (JWTVerificationException ex) {
      ex.printStackTrace();
      return null;
    }
  }

}
