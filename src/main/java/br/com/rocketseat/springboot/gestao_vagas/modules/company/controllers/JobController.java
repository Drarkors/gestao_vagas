package br.com.rocketseat.springboot.gestao_vagas.modules.company.controllers;

import br.com.rocketseat.springboot.gestao_vagas.modules.company.dtos.CreateJobDTO;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.entities.JobEntity;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.useCases.CreateJobUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/company/job")
public class JobController {

  @Autowired
  private CreateJobUseCase createJobUseCase;

  @Tag(name = "Vagas", description = "Informações das vagas")
  @Operation(
      summary = "Cadastro de vaga",
      description = "Função responsável por cadastrar uma vaga na empresa autenticada"
  )
  @SecurityRequirement(name = "jwt_auth")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = JobEntity.class))
      })
  })
  @PostMapping("/")
  @PreAuthorize("hasRole('COMPANY')")
  public ResponseEntity<?> create(@RequestBody @Valid CreateJobDTO createJobDTO, HttpServletRequest request) {
    var companyId = request.getAttribute("company_id");

    var job = JobEntity.builder()
        .benefits(createJobDTO.getBenefits())
        .description(createJobDTO.getDescription())
        .level(createJobDTO.getLevel())
        .companyId(UUID.fromString(companyId.toString()))
        .build();

    return ResponseEntity.ok(this.createJobUseCase.execute(job));
  }

}
