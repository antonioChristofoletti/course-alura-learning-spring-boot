package antonio.houseapi.dto

open class DtoToken {
    lateinit var token: String
    lateinit var type: String

    constructor()

    constructor(token: String, type: String) {
        this.token = token
        this.type = type
    }
}