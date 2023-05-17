package med.voll.api.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import med.voll.api.dto.EntityUpdateDTO;
import med.voll.api.dto.PacienteDTO;
import med.voll.api.dto.PacienteDetailDTO;
import med.voll.api.dto.PacienteListingDTO;
import med.voll.api.model.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
@SecurityRequirement(name = "bearer-key")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity save(@RequestBody @Valid PacienteDTO pacienteDTO, UriComponentsBuilder uriComponentsBuilder) {
        var paciente = new Paciente(pacienteDTO);
        repository.save(paciente);

        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();

        return ResponseEntity.created(uri).body(new PacienteDetailDTO(paciente));
    }

    @GetMapping
    public ResponseEntity<Page<PacienteListingDTO>> findAll(Pageable page) {
        var pageResponse = repository.findAllByAtivoTrue(page).map(PacienteListingDTO::new);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        var paciente = repository.getReferenceByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new PacienteDetailDTO(paciente));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid EntityUpdateDTO pacienteUpdateDTO) {
        var paciente = repository.getReferenceByIdAndAtivoTrue(pacienteUpdateDTO.id());
        paciente.updateInformations(pacienteUpdateDTO);

        return ResponseEntity.ok(new PacienteDetailDTO(paciente));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var paciente = repository.getReferenceByIdAndAtivoTrue(id);
        paciente.inactivate();

        return ResponseEntity.noContent().build();
    }
}
