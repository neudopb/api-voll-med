package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.AgendamentoConsultaDTO;
import med.voll.api.dto.CancelamentoConsultaDTO;
import med.voll.api.dto.ConsultaDetailDTO;
import med.voll.api.service.ConsultaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("consultas")
public class ConsultaController {

    @Autowired
    private ConsultaService consultaService;

    @PostMapping
    @Transactional
    public ResponseEntity schedule(@RequestBody @Valid AgendamentoConsultaDTO agendamento) {
        var detail = consultaService.schedule(agendamento);
        return ResponseEntity.ok(detail);
    }

    @DeleteMapping
    @Transactional
    public ResponseEntity cancel(@RequestBody @Valid CancelamentoConsultaDTO cancelamento) {
        consultaService.cancel(cancelamento);
        return ResponseEntity.noContent().build();
    }
}
