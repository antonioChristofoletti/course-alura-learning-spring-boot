package antonio.houseapi.repository

import antonio.houseapi.model.Person
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PersonRepository: JpaRepository<Person, Long> {

    fun findByName(name: String, pageable: Pageable): Page<Person>
}