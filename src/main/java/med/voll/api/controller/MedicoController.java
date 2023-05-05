package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.EntityUpdateDTO;
import med.voll.api.dto.MedicoDTO;
import med.voll.api.dto.MedicoDetailDTO;
import med.voll.api.dto.MedicoListingDTO;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity save(@RequestBody @Valid MedicoDTO medicoDTO, UriComponentsBuilder uriComponentsBuilder) {
        var medico = new Medico(medicoDTO);
        repository.save(medico);

        var uri = uriComponentsBuilder.path("/medicos/{id}").buildAndExpand(medico.getId()).toUri();

        return ResponseEntity.created(uri).body(new MedicoDetailDTO(medico));
    }

    @GetMapping
    public ResponseEntity<Page<MedicoListingDTO>> findAll(@PageableDefault(size = 10, sort = {"nome"}) Pageable page) {
        var pageResponse = repository.findAllByAtivoTrue(page).map(MedicoListingDTO::new);
        return ResponseEntity.ok(pageResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id) {
        var medico = repository.getReferenceByIdAndAtivoTrue(id);
        return ResponseEntity.ok(new MedicoDetailDTO(medico));
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid EntityUpdateDTO medicoUpdateDTO) {
        var medico = repository.getReferenceByIdAndAtivoTrue(medicoUpdateDTO.id());
        medico.updateInformations(medicoUpdateDTO);

        return ResponseEntity.ok(new MedicoDetailDTO(medico));
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id) {
        var medico = repository.getReferenceByIdAndAtivoTrue(id);
        medico.inactivate();

        return ResponseEntity.noContent().build();
    }

}
