package br.com.rocketseat.springboot.gestao_vagas.modules.company.repositories;

import br.com.rocketseat.springboot.gestao_vagas.modules.company.entities.JobEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<JobEntity, UUID> {

  // LIKE == contains
  // Select * From job where descirption like '%<filter>%'
  List<JobEntity> findByDescriptionContainingIgnoreCase(String filter);

}
