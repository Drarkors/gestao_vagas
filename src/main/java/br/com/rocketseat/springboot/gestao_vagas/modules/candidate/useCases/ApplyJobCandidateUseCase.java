package br.com.rocketseat.springboot.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.springboot.gestao_vagas.exceptions.JobNotFoundException;
import br.com.rocketseat.springboot.gestao_vagas.exceptions.UserNotFoundException;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.repositories.JobRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ApplyJobCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  @Autowired
  private JobRepository jobRepository;

  public void execute(UUID idCandidate, UUID idJob) {
    // Validar se o candidato existe
    var candidate = candidateRepository.findById(idCandidate).orElseThrow(() -> new UserNotFoundException());

    // Validar se a vaga existe
    var job = this.jobRepository.findById(idJob).orElseThrow(() -> new JobNotFoundException());

    // Inscrever candidato na vaga
  }

}
