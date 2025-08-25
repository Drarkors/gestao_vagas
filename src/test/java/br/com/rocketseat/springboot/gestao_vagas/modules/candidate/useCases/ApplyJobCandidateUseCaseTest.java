package br.com.rocketseat.springboot.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.springboot.gestao_vagas.exceptions.JobNotFoundException;
import br.com.rocketseat.springboot.gestao_vagas.exceptions.UserNotFoundException;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.entities.JobApplianceEntity;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.repositories.JobApplianceRepository;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.repositories.JobRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ApplyJobCandidateUseCaseTest {

    @Mock
    private CandidateRepository candidateRepository;

    @Mock
    private JobRepository jobRepository;

    @Mock
    private JobApplianceRepository jobApplianceRepository;

    @InjectMocks
    private ApplyJobCandidateUseCase useCase;

    @Test
    @DisplayName("Should not be able to apply if the candidate wasn't found")
    public void shouldNotBeAbleToApplyIfCandidateWasNotFound() {
        assertThrows(UserNotFoundException.class, () -> useCase.execute(null, null));
    }

    @Test
    @DisplayName("Should not be able to apply if a job wasn't found")
    public void shouldNotBeAbleToApplyIfJobWasNotFound() {
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        when(candidateRepository.findById(eq(idCandidate)))
            .thenReturn(Optional.of(candidate));

        assertThrows(JobNotFoundException.class, () -> useCase.execute(idCandidate, null));
    }

    @Test
    @DisplayName("Should be able to create a job appliance")
    public void shouldBeAbleToCreateAJobAppliance() {
        var idCandidate = UUID.randomUUID();
        var candidate = new CandidateEntity();
        candidate.setId(idCandidate);

        var idJob = UUID.randomUUID();
        var job = new JobEntity();
        job.setId(idJob);

        var jobAppliance = JobApplianceEntity.builder()
            .candidateId(idCandidate)
            .jobId(idJob)
            .build();

        when(candidateRepository.findById(eq(idCandidate)))
            .thenReturn(Optional.of(candidate));

        when(jobRepository.findById(eq(idJob)))
            .thenReturn(Optional.of(job));

        when(jobApplianceRepository.save(eq(jobAppliance))).thenAnswer((t) -> {
            jobAppliance.setId(UUID.randomUUID());
            return jobAppliance;
        });

        var result = useCase.execute(idCandidate, idJob);

        assertThat(result).hasFieldOrProperty("id");
        assertNotNull(result.getId());
    }
}
