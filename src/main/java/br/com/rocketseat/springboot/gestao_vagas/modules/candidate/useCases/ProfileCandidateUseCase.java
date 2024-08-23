package br.com.rocketseat.springboot.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.dtos.ProfileCandidateResponseDTO;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

  @Autowired
  private CandidateRepository candidateRepository;

  public ProfileCandidateResponseDTO execute(UUID idCandidate) {
    var candidate = this.candidateRepository.findById(idCandidate)
        .orElseThrow(() -> new UsernameNotFoundException("User not found"));

    return ProfileCandidateResponseDTO.builder()
        .id(idCandidate)
        .email(candidate.getEmail())
        .username(candidate.getUsername())
        .name(candidate.getName())
        .description(candidate.getDescription())
        .build();
  }

}
