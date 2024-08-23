package br.com.rocketseat.springboot.gestao_vagas.modules.company.useCases;

import br.com.rocketseat.springboot.gestao_vagas.modules.company.dtos.AuthCompanyDTO;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.dtos.AuthCompanyResponseDTO;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.repositories.CompanyRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

@Service
public class AuthCompanyUseCase {

  @Autowired
  private CompanyRepository companyRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Value("${security.token.secret}")
  private String secretKey;

  public AuthCompanyResponseDTO execute(AuthCompanyDTO authCompanyDTO) throws AuthenticationException {
    var company = companyRepository.findByUsername(authCompanyDTO.getUsername()).
        orElseThrow(() -> new UsernameNotFoundException("Username/password is incorrect"));

    var passwordMathces = this.passwordEncoder.matches(authCompanyDTO.getPassword(), company.getPassword());

    if (!passwordMathces)
      throw new AuthenticationException("Username/password is incorrect");

    Algorithm algorithm = Algorithm.HMAC256(secretKey);

    var expiresIn = Instant.now().plus(Duration.ofHours(2));

    var token = JWT.create()
        .withIssuer("javagas")
        .withExpiresAt(expiresIn)
        .withSubject(company.getId().toString())
        .withClaim("roles", List.of("COMPANY"))
        .sign(algorithm);

    return new AuthCompanyResponseDTO(token, expiresIn.toEpochMilli());
  }

}
