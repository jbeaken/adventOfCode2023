import java.nio.file.Files
import java.nio.file.Path
import kotlin.test.Test

internal class Day2Test {

    val idRegex = """Game (?<id>\d{1,3}):""".toRegex()
    val redRegex = """(?<number>\d{1,2}) red""".toRegex()
    val greenRegex = """(?<number>\d{1,2}) green""".toRegex()
    val blueRegex = """(?<number>\d{1,2}) blue""".toRegex()

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
    }

    private fun getIsPossible(line: String): Boolean {
//red
        var matchResults = redRegex.findAll(line)
        val maxRedValue = matchResults.maxByOrNull{ it.groups["number"]?.value?.toInt() ?: 0 }?.groups?.get("number")?.value?.toInt()
        if (maxRedValue != null) {
            if(maxRedValue > 12) return false
        }

//green
        matchResults = greenRegex.findAll(line)
        val maxGreenValue = matchResults.maxByOrNull{ it.groups["number"]?.value?.toInt() ?: 0 }?.groups?.get("number")?.value?.toInt()
        if (maxGreenValue != null) {
            if(maxGreenValue > 13) return false
        }

//blue
        matchResults = blueRegex.findAll(line)
        val maxBlueValue = matchResults.maxByOrNull{ it.groups["number"]?.value?.toInt() ?: 0 }?.groups?.get("number")?.value?.toInt()
        if (maxBlueValue != null) {
            if(maxBlueValue > 14) return false;
        }

        return true
    }
}