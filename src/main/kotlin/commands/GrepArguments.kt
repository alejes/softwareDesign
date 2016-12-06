package commands

import com.jshmrsn.karg.Arguments
import com.jshmrsn.karg.RawArguments

/**
 * The class represents settings for parsing the arguments of grep command.
 */

class GrepArguments(raw: RawArguments) : Arguments(raw, name = "default") {
    val ignoreCase = optionalFlag(
            name = "ignore-case",
            description = "Ignore case distinctions, so that characters that differ only in case match each other. ",
            shortNames = listOf('i'),
            default = false
    )

    val wordRegexp = optionalFlag(
            name = "word-regexp",
            description = "Select only those lines containing matches that form whole words. " +
                    "The test is that the matching substring must either be at the beginning of the line, " +
                    "or preceded by a non-word constituent character. " +
                    "Similarly, it must be either at the end of the line or " +
                    "followed by a non-word constituent character. " +
                    "Word-constituent characters are letters, digits, and the underscore.",
            shortNames = listOf('w'),
            default = false
    )

    val afterContext = optionalParameter(
            name = "after-context",
            description = "Print specific lines of trailing  context  after  matching  lines.",
            default = "0",
            shortNames = listOf('A')
    )

    val regexp = positionalArguments(
            name = "regexp",
            description = "Use this as the pattern.",
            minCount = 1,
            maxCount = 1
    )
}