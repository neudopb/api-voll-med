package med.voll.api.controller;

import med.voll.api.dto.AgendamentoConsultaDTO;
import med.voll.api.dto.ConsultaDetailDTO;
import med.voll.api.enuns.EspecialidadeEnum;
import med.voll.api.service.ConsultaService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ConsultaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<AgendamentoConsultaDTO> agendamentoConsultaDTOJson;

    @Autowired
    private JacksonTester<ConsultaDetailDTO> consultaDetailDTOJson;

    @MockBean
    private ConsultaService consultaService;

    @Test
    @DisplayName("Deve devolver codigo http 400 quando informacoes estao invalidas")
    @WithMockUser
    void scheduleTest1() throws Exception {
        var response = mvc
                .perform(post("/consultas"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deve devolver codigo http 200 quando informacoes estao validas")
    @WithMockUser
    void scheduleTest2() throws Exception {
        var data = LocalDateTime.now().plusHours(1);
        var especialidade = EspecialidadeEnum.DERMATOLOGIA;

        var consultaDetail = new ConsultaDetailDTO(null, 1L, 4L, data);

        when(consultaService.schedule(any())).thenReturn(consultaDetail);

        var response = mvc
                .perform(
                        post("/consultas")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(agendamentoConsultaDTOJson.write(
                                        new AgendamentoConsultaDTO(1L, 4L, data, especialidade)
                                ).getJson())
                ).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonExpected = consultaDetailDTOJson.write(
                consultaDetail
        ).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonExpected);
    }

}