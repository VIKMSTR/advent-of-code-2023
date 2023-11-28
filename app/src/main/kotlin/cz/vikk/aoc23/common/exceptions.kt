package cz.vikk.aoc23.common

abstract class AocException(
    val errorMessage: String,
    cause: Throwable? = null,
) : Exception(errorMessage, cause) {
    val name = "${this::class.simpleName}"
}

class FileIOException(path: String, cause: Throwable? = null): AocException(
    errorMessage = "Error during file $path operation.",
    cause = cause,
)

class FileNotFoundException(path: String): AocException(
    errorMessage = "File $path was not found!",
)
