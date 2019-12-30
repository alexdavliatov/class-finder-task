package ru.adavliatov.task.classfinder

import ru.adavliatov.task.classfinder.config.ConfigDSL.Companion.config
import ru.adavliatov.task.classfinder.domain.Input
import ru.adavliatov.task.classfinder.domain.InvalidInputError
import ru.adavliatov.task.classfinder.usecase.*
import ru.adavliatov.task.classfinder.usecase.handler.HandlerExtensions.invoke
import java.io.File

val config = config {
    searchStrategies {
        plusAssign(EndsWithSearchStrategy())
        plusAssign(CaseInsensitiveSearchStrategy())
        plusAssign(WildcardSearchStrategy())
        plusAssign(StartsWithSearchStrategy())
    }
    inputHandlers {
        plusAssign(NonBlankToNullInputHandler())
        plusAssign(TrimLeftHandler())
        plusAssign(MiddleWhitespacesToNullInputHandler())
    }
    itemHandlers {
        plusAssign(NonBlankToNullItemHandler())
        plusAssign(PackageRemoverHandler())
    }
}

const val errorMessage = "Usage: ./class-finder-kotlin <path to classes: classes.txt> <search: 'FoB*ar*rd*Baz_'>"
fun main(vararg args: String) {
    if (args.size != 2) {
        System.err.println(errorMessage)
        throw InvalidArgumentsNumber
    }
    val classesPath = args[0]
    val input = args[1]
    val handledInput: Input = input.let(config.inputHandlers()) ?: throw InvalidInputError(input)
    val classesFile = File(classesPath)
    if (!classesFile.exists()) {
        System.err.println(errorMessage)
        throw ClassesFileIsAbsent(classesPath)
    }
    if (classesFile.isDirectory) throw ClassesFileIsDirectory(classesPath)

    classesFile
        .useLines {
            ClassFinder(config)
                .find(handledInput, it)
                .joinToString("\n")
                .run { println(this) }
        }
}

class ClassesFileIsAbsent(path: String) : IllegalArgumentException("File with path $path does not exists")
class ClassesFileIsDirectory(path: String) : IllegalArgumentException("File with path $path is directory")
object InvalidArgumentsNumber : IllegalArgumentException(errorMessage)

