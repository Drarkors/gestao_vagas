package br.com.rocketseat.springboot.gestao_vagas.modules.company.controllers;

import br.com.rocketseat.springboot.gestao_vagas.exceptions.CompanyNotFoundException;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.dtos.CreateJobDTO;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.rocketseat.springboot.gestao_vagas.modules.company.repositories.CompanyRepository;
import br.com.rocketseat.springboot.gestao_vagas.utils.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class CreateJobControllerTest {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private CompanyRepository companyRepository;

    @BeforeEach
    void setup() {
        mvc = MockMvcBuilders
            .webAppContextSetup(context)
            .apply(SecurityMockMvcConfigurers.springSecurity())
            .build();
    }

    @Test
    @DisplayName("Should be able to create a new job")
    void shouldBeAbleToCreateANewJob() throws Exception {
        var company = CompanyEntity.builder()
            .name("NAME")
            .description("DESCRIPTION")
            .email("email@company.com")
            .password("1234567890")
            .username("company_username")
            .build();

        company = companyRepository.saveAndFlush(company);

        var dto = CreateJobDTO.builder()
            .benefits("BENEFITS")
            .description("DESCRIPTION")
            .level("TEST")
            .build();

        var result = mvc.perform(
            MockMvcRequestBuilders.post("/company/job")
                .contentType(MediaType.APPLICATION_JSON)
                .content(TestUtils.objectToJSON(dto))
                .header("Authorization", TestUtils.generateToken(company.getId(), "COMPANY", "JAVAGAS_@123#"))
        ).andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Should not be able to create a new job if a company was not found")
    void shouldNotBeAbleToCreateANewJobIfCompanyNotFound() throws Exception {
        var dto = CreateJobDTO.builder()
            .benefits("BENEFITS")
            .description("DESCRIPTION")
            .level("TEST")
            .build();

        mvc.perform(
                MockMvcRequestBuilders.post("/company/job")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(TestUtils.objectToJSON(dto))
                    .header("Authorization", TestUtils.generateToken(UUID.randomUUID(), "COMPANY", "JAVAGAS_@123#"))
            )
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(result -> assertTrue(result.getResolvedException() instanceof CompanyNotFoundException));
    }
}
