package br.com.rocketseat.springboot.gestao_vagas.modules.candidate.useCases;

import br.com.rocketseat.springboot.gestao_vagas.exceptions.JobNotFoundException;
import br.com.rocketseat.springboot.gestao_vagas.exceptions.UserNotFoundException;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.entities.JobApplianceEntity;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.repositories.CandidateRepository;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.repositories.JobApplianceRepository;
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

    @Autowired
    private JobApplianceRepository jobApplianceRepository;

    public JobApplianceEntity execute(UUID idCandidate, UUID idJob) {
        // Validar se o candidato existe
        var candidate = candidateRepository.findById(idCandidate).orElseThrow(UserNotFoundException::new);

        // Validar se a vaga existe
        var job = this.jobRepository.findById(idJob).orElseThrow(JobNotFoundException::new);

        // Inscrever candidato na vaga
        var jobAppliance = JobApplianceEntity.builder()
            .candidateId(candidate.getId())
            .jobId(job.getId())
            .build();

        jobAppliance = jobApplianceRepository.save(jobAppliance);

        return jobAppliance;
    }

}
