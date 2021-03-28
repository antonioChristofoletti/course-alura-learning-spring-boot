package antonio.houseapi.model

import antonio.houseapi.dto.DtoPersonCreate
import java.math.BigDecimal
import java.math.BigInteger
import javax.persistence.Convert
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.OneToMany

@Entity
open class Person {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long = 0

    open var name: String = ""

    open var age: Int = 0

    @OneToMany(targetEntity = Residence::class)
    open var residenceList: List<Residence> = listOf()

    constructor() {}

    constructor(name: String, age: Int) {
        this.name = name
        this.age = age
    }

    companion object {
        fun convert(dtoPersonCreate: DtoPersonCreate) = Person(dtoPersonCreate.name, dtoPersonCreate.age)
    }

    fun applyChanges(dtoPersonCreate: DtoPersonCreate) {
        this.age = dtoPersonCreate.age
        this.name = dtoPersonCreate.name
    }
}