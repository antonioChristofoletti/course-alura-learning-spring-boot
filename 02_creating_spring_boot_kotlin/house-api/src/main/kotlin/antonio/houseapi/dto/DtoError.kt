package antonio.houseapi.dto

open class DtoError {
    open var code: Int? = null
    open var description: String? = null

    constructor(code: Int, description: String) {
        this.code = code
        this.description = description
    }
}