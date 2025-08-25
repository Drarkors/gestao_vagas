package br.com.rocketseat.springboot.gestao_vagas.modules.candidate.repositories;

import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.entities.JobApplianceEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JobApplianceRepository extends JpaRepository<JobApplianceEntity, UUID> {
}
