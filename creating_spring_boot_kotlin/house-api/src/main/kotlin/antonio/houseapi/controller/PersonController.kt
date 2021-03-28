package antonio.houseapi.controller

import antonio.houseapi.Utils.CrudRepositoryUtils.Companion.getValueOrNull
import antonio.houseapi.dto.DtoPerson
import antonio.houseapi.dto.DtoPersonCreate
import antonio.houseapi.model.Person
import antonio.houseapi.repository.PersonRepository
import javax.transaction.Transactional
import javax.validation.Valid
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.data.web.PageableDefault
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.UriComponentsBuilder

@RestController
@RequestMapping("/person")
class PersonController(
    @Autowired val personRepository: PersonRepository
) {
    @GetMapping
    @Cacheable(value = ["PersonController.getPersonList"])
    fun getPersonList(
        @RequestParam(required = false) name: String?,
        @PageableDefault(sort = ["id"], direction = Sort.Direction.DESC) pageable: Pageable
    ): Page<DtoPerson> {
        val pagePerson = if (name.isNullOrEmpty()) {
            personRepository.findAll(pageable)
        } else {
            personRepository.findByName(name, pageable)
        }

        return DtoPerson.convert(pagePerson)
    }

    @GetMapping("/{id}")
    fun getOnePerson(@PathVariable id: Long): ResponseEntity<DtoPerson> {
        val person = personRepository.findById(id).getValueOrNull() ?: return ResponseEntity.notFound().build()
        return ResponseEntity.ok(DtoPerson.convert(person))
    }

    @PostMapping
    @Transactional
    @CacheEvict(value = ["PersonController.getPersonList"], allEntries = true)
    fun create(@RequestBody @Valid dtoPersonCreate: DtoPersonCreate, uriBuilder: UriComponentsBuilder): ResponseEntity<DtoPerson> {
        val createdPerson = personRepository.save(Person.convert(dtoPersonCreate))

        val uri = uriBuilder.path("/person/${createdPerson.id}").buildAndExpand().toUri()

        return ResponseEntity.created(uri).body(DtoPerson.convert(createdPerson))
    }

    @PatchMapping("/{id}")
    @Transactional
    @CacheEvict(value = ["PersonController.getPersonList"], allEntries = true)
    fun update(
        @PathVariable id: Long,
        @RequestBody @Valid dtoPersonCreate: DtoPersonCreate
    ): ResponseEntity<DtoPerson> {
        val person = personRepository.findById(id).getValueOrNull() ?: return ResponseEntity.notFound().build()

        person.apply {
            this.applyChanges(dtoPersonCreate)
            personRepository.save(this)
        }

        return ResponseEntity.ok(DtoPerson.convert(person))
    }

    @DeleteMapping("{id}")
    @Transactional
    @CacheEvict(value = ["PersonController.getPersonList"], allEntries = true)
    fun delete(@PathVariable id: Long): ResponseEntity<DtoPerson> {
        val person = personRepository.findById(id).getValueOrNull() ?: return ResponseEntity.notFound().build()
        personRepository.delete(person)
        return ResponseEntity.ok().build()
    }
}