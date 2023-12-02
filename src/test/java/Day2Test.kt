import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test

internal class Day2Test {

    val colourMap = mapOf("red" to 12, "green" to 13, "blue" to 14)

    val idRegex = """Game (?<id>\d{1,3}):""".toRegex()

    @Test
    fun test1() {
        val resource = this::class.java.getResource("input/day2.txt")
        val readAllLines = Files.readAllLines(Path.of(resource.toURI()))

        var result: Int = 0
        readAllLines.forEach {line ->

            //Get id
            val id = idRegex.find(line)?.groups?.get("id")?.value?.toInt()

            val isPossible = getIsPossible(line)
            if(isPossible) {
                if (id != null) {
                    result += id
                }
            }
        }
        println("Added up possible game ids for $result")

        assert(result == 2776)
    }

    private fun getIsPossible(line: String): Boolean {

        colourMap.forEach { entry ->
            val regex : Regex = """(?<number>\d{1,2}) ${entry.key}""".toRegex()
            val matchResults = regex.findAll(line)

            val maxValue = matchResults.maxByOrNull {
                it.groups["number"]?.value?.toInt() ?: 0
            }?.groups?.get("number")?.value?.toInt()

            if (maxValue != null) {
                if (maxValue > entry.value) return false
            }
        }

        return true
    }
}