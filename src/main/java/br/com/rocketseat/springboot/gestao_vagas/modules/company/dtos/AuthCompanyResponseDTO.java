package br.com.rocketseat.springboot.gestao_vagas.modules.company.dtos;

public record AuthCompanyResponseDTO(String access_token, Long expires_in) {
}