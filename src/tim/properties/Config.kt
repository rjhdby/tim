package tim.properties

import tim.Main
import java.io.File
import java.io.FileInputStream
import java.util.*

object Config {
    private val properties: Properties by lazy {
        val properties = Properties()
//        val url = File(Main::class.java.protectionDomain.codeSource.location.toURI().path).parent + "/" + filename
        val url = debug + '/' + filename
        properties.load(FileInputStream(url))
        return@lazy properties
    }
    private val filename = "probes.properties"
    private val debug = "/Users/rjhdby/IdeaProjects/tim"

    operator fun get(name: String): String = properties.getProperty(name)
}
