import java.io.File

object FileReader {

    fun readFile(fileName: String): List<String>
            = File("src/main/resources/$fileName").readLines()

}