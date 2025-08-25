package br.com.rocketseat.springboot.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.springboot.gestao_vagas.exceptions.UserNotFoundException;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.dtos.ProfileCandidateResponseDTO;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ProfileCandidateUseCase {

    @Autowired
    private CandidateRepository candidateRepository;

    public ProfileCandidateResponseDTO execute(UUID idCandidate) {
        var candidate = this.candidateRepository.findById(idCandidate)
            .orElseThrow(UserNotFoundException::new);

        return ProfileCandidateResponseDTO.builder()
            .id(idCandidate)
            .email(candidate.getEmail())
            .username(candidate.getUsername())
            .name(candidate.getName())
            .description(candidate.getDescription())
            .build();
    }

}
