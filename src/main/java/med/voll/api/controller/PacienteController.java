package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.EntityUpdateDTO;
import med.voll.api.dto.PacienteDTO;
import med.voll.api.dto.PacienteListingDTO;
import med.voll.api.model.Paciente;
import med.voll.api.repository.PacienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("pacientes")
public class PacienteController {

    @Autowired
    private PacienteRepository repository;

    @PostMapping
    @Transactional
    public Paciente save(@RequestBody @Valid PacienteDTO pacienteDTO) {
        return repository.save(new Paciente(pacienteDTO));
    }

    @GetMapping
    public Page<PacienteListingDTO> findAll(Pageable page) {
        return repository.findAllByAtivoTrue(page).map(PacienteListingDTO::new);
    }

    @GetMapping("/{id}")
    public PacienteListingDTO findById(@PathVariable Long id) {
        Paciente paciente = repository.getReferenceByIdAndAtivoTrue(id);
        return new PacienteListingDTO(paciente);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid EntityUpdateDTO pacienteUpdateDTO) {
        Paciente paciente = repository.getReferenceById(pacienteUpdateDTO.id());
        paciente.updateInformations(pacienteUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        Paciente paciente = repository.getReferenceById(id);
        paciente.inactivate();
    }
}
