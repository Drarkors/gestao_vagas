package br.com.rocketseat.springboot.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.springboot.gestao_vagas.exceptions.UserFoundException;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public CandidateEntity execute(CandidateEntity candidate) {
    this.candidateRepository
        .findByUsernameOrEmail(candidate.getUsername(), candidate.getEmail())
        .ifPresent(user -> {
          throw new UserFoundException();
        });

    var password = passwordEncoder.encode(candidate.getPassword());
    candidate.setPassword(password);

    return this.candidateRepository.save(candidate);
  }

}
