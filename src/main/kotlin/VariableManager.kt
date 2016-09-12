/**
 * A repository of all environment variables
 */

object VariableManager {
    private val variableManager = hashMapOf<String, String>()
    operator fun get(variable: String) =
            variableManager[variable]

    operator fun set(variable: String, value: String) =
            variableManager.set(variable, value)

}