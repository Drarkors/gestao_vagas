package br.com.rocketseat.springboot.gestao_vagas.modules.candidate.controllers;

import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.dtos.ProfileCandidateResponseDTO;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.entities.CandidateEntity;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.useCases.CreateCandidateUseCase;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.useCases.ListAllJobsByFilter;
import br.com.rocketseat.springboot.gestao_vagas.modules.candidate.useCases.ProfileCandidateUseCase;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.entities.JobEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Tag(name = "Candidato", description = "Informações do candidato")
@RestController
@RequestMapping("/candidate")
public class CandidateController {

  @Autowired
  private CreateCandidateUseCase createCandidateUseCase;

  @Autowired
  private ProfileCandidateUseCase profileCandidateUseCase;

  @Autowired
  private ListAllJobsByFilter listAllJobsByFilter;

  @Operation(
      summary = "Cadastro de candidato",
      description = "Função responsável por cadastrar um novo candidato."
  )
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "Usuário já existe")
  })
  @PostMapping("/")
  public ResponseEntity<?> create(@Valid @RequestBody CandidateEntity candidate) {
    try {
      var result = this.createCandidateUseCase.execute(candidate);
      return ResponseEntity.ok(result);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Operation(
      summary = "Perfil do candidato",
      description = "Função responsável por buscar as informações de perfil do candidato autenticado."
  )
  @SecurityRequirement(name = "jwt_auth")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(schema = @Schema(implementation = ProfileCandidateResponseDTO.class))
      }),
      @ApiResponse(responseCode = "400", description = "User not found")
  })
  @GetMapping("/")
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<?> getProfile(HttpServletRequest request) {
    var idCandidate = request.getAttribute("candidate_id");

    try {
      var profile = this.profileCandidateUseCase
          .execute(UUID.fromString(idCandidate.toString()));

      return ResponseEntity.ok(profile);
    } catch (Exception e) {
      return ResponseEntity.badRequest().body(e.getMessage());
    }
  }

  @Operation(
      summary = "Listagem de vagas disponíveis para o candidato",
      description = "Função responsável por listar todas as vagas disponíveis, baseando-se no filtro (descrição) informado"
  )
  @SecurityRequirement(name = "jwt_auth")
  @ApiResponses({
      @ApiResponse(responseCode = "200", content = {
          @Content(array = @ArraySchema(schema = @Schema(implementation = JobEntity.class)))
      })
  })
  @GetMapping("/job")
  @PreAuthorize("hasRole('CANDIDATE')")
  public ResponseEntity<List<JobEntity>> findJobsByFilter(@RequestParam String filter) {
    return ResponseEntity.ok(listAllJobsByFilter.execute(filter));
  }

}
