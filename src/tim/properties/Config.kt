package tim.properties

import tim.Main
import java.io.File
import java.io.FileInputStream
import java.util.*

object Config {
    val productionPath = File(Main::class.java.protectionDomain.codeSource.location.toURI().path).parent + "/"
    val debugPath = "/Users/rjhdby/IdeaProjects/tim/"

    val path = productionPath

    private val properties: Properties by lazy {
        val properties = Properties()
        val url = path + filename
        properties.load(FileInputStream(url))
        return@lazy properties
    }
    private val filename = "probes.properties"


    operator fun get(name: String): String = properties.getProperty(name)
}
