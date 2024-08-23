package br.com.rocketseat.springboot.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.dtos.AuthCandidateRequestDTO;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.dtos.AuthCandidateResponseDTO;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.repositories.CandidateRepository;
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
public class AuthCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;
  @Autowired
  private PasswordEncoder passwordEncoder;
  @Value("${security.token.secret.candidate}")
  private String secretKey;

  public AuthCandidateResponseDTO execute(AuthCandidateRequestDTO authCandidateRequestDTO) throws AuthenticationException {
    var candidate = this.candidateRepository.findByUsername(authCandidateRequestDTO.username()).
        orElseThrow(() -> new UsernameNotFoundException("Username/password incorrect"));

    var passwordMatches = this.passwordEncoder.matches(authCandidateRequestDTO.password(), candidate.getPassword());

    if (!passwordMatches) {
      throw new AuthenticationException();
    }

    Algorithm algorithm = Algorithm.HMAC256(secretKey);
    var expiresIn = Instant.now().plus(Duration.ofMinutes(10));

    var token = JWT.create()
        .withIssuer("javagas")
        .withExpiresAt(expiresIn)
        .withClaim("roles", List.of("CANDIDATE"))
        .withSubject(candidate.getId().toString())
        .sign(algorithm);

    return new AuthCandidateResponseDTO(token, expiresIn.toEpochMilli());
  }
}
