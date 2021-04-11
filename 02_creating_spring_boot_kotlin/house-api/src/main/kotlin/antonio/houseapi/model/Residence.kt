package antonio.houseapi.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.ManyToOne

@Entity
open class Residence {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

    open var address: String? = null

    open var markedWorth: Double? = null

    @ManyToOne(targetEntity = Person::class)
    open var person: Person? = null
}