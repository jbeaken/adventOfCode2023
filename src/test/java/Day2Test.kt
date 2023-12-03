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
            val id = idRegex.find(line)!!.groups.get("id")!!.value.toInt()

            if(isPossible( line )) {
                result += id
            }
        }
        println("Added up possible game ids to $result")

        assert(result == 2776)
    }

    private fun isPossible(line: String): Boolean {

        colourMap.forEach { entry ->
            val regex : Regex = """(?<number>\d{1,2}) ${entry.key}""".toRegex()
            val matchResults = regex.findAll(line)

            val maxValueMatch = matchResults.maxByOrNull {
                it.groups["number"]?.value?.toInt() ?: 0
            }

            val maxValue = maxValueMatch?.groups?.get("number")!!.value.toInt()

            if (maxValue > entry.value) return false
        }

        return true
    }
}