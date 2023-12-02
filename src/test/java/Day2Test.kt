import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test

internal class Day2Test {


    @Test
    fun testSum() {
        val resource = this::class.java.getResource("input/day2.txt")
        val readAllLines = Files.readAllLines(Path.of(resource.toURI()))

        readAllLines.forEach {
            val regex = """\d""".toRegex()

            val matchResults = regex.findAll("abcb abbd")

            println("The element is $it")
            println("matchResults $matchResults")
            matchResults.forEach {
                println("The element is $it")
            }
        }
    }
}