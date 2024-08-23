package br.com.rocketseat.springboot.gestao_vagas.modules.company.dtos;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class CreateJobDTO {

  @Schema(example = "Vaga para pessoa desenvolvedora júnior", requiredMode = Schema.RequiredMode.REQUIRED)
  private String description;
  @Schema(example = "PLR, Gympass, Plano de Saúde", requiredMode = Schema.RequiredMode.REQUIRED)
  private String benefits;
  @Schema(example = "JUNIOR", requiredMode = Schema.RequiredMode.REQUIRED)
  private String level;

}
