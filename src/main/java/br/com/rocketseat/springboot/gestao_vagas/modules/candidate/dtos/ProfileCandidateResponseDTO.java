package br.com.rocketseat.springboot.gestao_vagas.modules.candidate.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileCandidateResponseDTO {

  private UUID id;
  @Schema(example = "john.doe")
  private String username;
  @Schema(example = "john.doe@email.com")
  private String email;
  @Schema(example = "John Doe")
  private String name;
  @Schema(example = "Desenvolvedor Java")
  private String description;

}
