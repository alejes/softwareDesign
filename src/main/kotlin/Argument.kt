data class Argument(val cmd: String) {

    /**
     * @return the fill argument to the current values of the environment
     */
    fun eval(): String =
            when {
                cmd.startsWith('$') -> VariableManager[cmd.removePrefix("$")] ?: cmd
                else -> {
                    val parsing = Regex("""(([\w\.!,\$\n\s ])+)|("[\w,\.!\$\n\s ]*")""").find(cmd)?.value?.trim()?.removeSurrounding("\"", "\"").orEmpty()
                    when {
                        cmd.startsWith('"') -> substituteVariables(parsing)
                        else -> parsing
                    }
                }
            }

    /**
     * search all variables in [input] and substitution where we can
     */
    private fun substituteVariables(input: String): String {
        var str = input
        var currentIndex = 0
        while (str.indexOf('$', currentIndex) >= 0) {
            currentIndex = str.indexOf('$', currentIndex)
            val variableName = str.substring(currentIndex + 1).substringBefore(' ')
            val variableValue = VariableManager[variableName] ?: variableName
            str = str.substring(0, currentIndex) + str.substring(currentIndex + 1, str.length).replace(variableName, variableValue)
            currentIndex++
        }
        return str
    }
}