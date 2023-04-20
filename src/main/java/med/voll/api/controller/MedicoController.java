package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.dto.EntityUpdateDTO;
import med.voll.api.dto.MedicoDTO;
import med.voll.api.dto.MedicoListingDTO;
import med.voll.api.model.Medico;
import med.voll.api.repository.MedicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;

    @PostMapping
    @Transactional
    public Medico save(@RequestBody @Valid MedicoDTO medicoDTO) {
        return repository.save(new Medico(medicoDTO));
    }

    @GetMapping
    public Page<MedicoListingDTO> findAll(@PageableDefault(size = 10, sort = {"nome"}) Pageable page) {
        return repository.findAllByAtivoTrue(page).map(MedicoListingDTO::new);
    }

    @GetMapping("/{id}")
    public MedicoListingDTO findById(@PathVariable Long id) {
        Medico medico = repository.getReferenceByIdAndAtivoTrue(id);
        return new MedicoListingDTO(medico);
    }

    @PutMapping
    @Transactional
    public void update(@RequestBody @Valid EntityUpdateDTO medicoUpdateDTO) {
        Medico medico = repository.getReferenceById(medicoUpdateDTO.id());
        medico.updateInformations(medicoUpdateDTO);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public void delete(@PathVariable Long id) {
        Medico medico = repository.getReferenceById(id);
        medico.inactivate();
    }

}
