package cz.vikk.aoc23.common

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import reactor.core.publisher.Flux
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.net.URL

class ResourceDataLoader(private val path: String){

    private val resource: URL? = ResourceDataLoader::class.java.getResource(path)

    fun loadLinesFromFile() : Either<AocException,List<String>> {
        if (resource == null) {
          return FileNotFoundException(path).left()
        }
        val uri = resource.toURI()
        return File(uri).bufferedReader().readLines().right()
    }

    fun loadLinesFromFileFlux(): Flux<String> = Flux.using( // resource factory creates FileReader instance
        { FileReader(File(resource!!.toURI())) },  // transformer function turns the FileReader into a Flux
        { reader: FileReader? ->
            Flux.fromStream(
                BufferedReader(reader!!).lines()
            )
        },
        { reader: FileReader -> reader.close() }
    )

}
