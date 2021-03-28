package antonio.houseapi.dto

import antonio.houseapi.model.Person
import com.sun.istack.NotNull
import java.math.BigInteger
import javax.validation.constraints.Min
import javax.validation.constraints.NotEmpty
import org.hibernate.validator.constraints.Length
import org.springframework.data.domain.Page

open class DtoPerson {

    open var id: Long = 0
    open var name: String = ""
    open var age: Int = 0

    constructor(id: Long, name: String, age: Int) {
        this.id = id
        this.name = name
        this.age = age
    }

    companion object {
        fun convert(personPages: Page<Person>): Page<DtoPerson> = personPages.map { convert(it) }

        fun convert(p: Person) = DtoPerson(p.id, p.name, p.age)
    }
}

open class DtoPersonCreate {
    @NotNull
    @NotEmpty
    @Length(min = 5)
    open var name: String = ""

    @NotNull
    @Min(15)
    open var age: Int = 0
}