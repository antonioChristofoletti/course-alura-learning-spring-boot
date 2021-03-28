package antonio.houseapi.Utils

import java.util.*

abstract class CrudRepositoryUtils {
    companion object {
        inline fun <reified T : Any> Optional<T>.getValueOrNull(): T? {
            return if (this.isPresent) {
                this.get()
            } else {
                null
            }
        }
    }
}