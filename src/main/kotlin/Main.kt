import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.io.File
import java.io.PrintStream
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

const val OUTPUT_PATH = "output.json"

val skipList = listOf(4)

val mapper = ObjectMapper().registerModule(JavaTimeModule())

fun main() {

    val testCases = LinkedList<TestCase>()

    val lineIterator = File("src/main/resources/source.txt").readLines().filter { it.isNotBlank() }.iterator()

    while (lineIterator.hasNext())
        testCases.add(readCase(lineIterator))

    mapper.writerWithDefaultPrettyPrinter().writeValue(File(OUTPUT_PATH), testCases)

}

fun readCase(lineIterator: Iterator<String>): TestCase {

    val conf = ScheduleConfiguration(
        capital = lineIterator.next().fixNumFormat().toBigDecimal(),
        installmentAmount = lineIterator.next().toInt(),
        installmentType = InstallmentType.valueOf(lineIterator.next()),
        interestRate = lineIterator.next().toDouble() / 100,
        withdrawalDate = LocalDate.parse(lineIterator.next(), DateTimeFormatter.ofPattern("M/dd/yyyy")),
    )

    val installments = LinkedList<Installment>()

    repeat(conf.installmentAmount) {
        val data = lineIterator.next().split("""\s""".toRegex()).toMutableList().apply { skipList.forEach { removeAt(it) } }

        installments.add(Installment(
            data.removeAt(0).fixNumFormat().toInt(),
            LocalDate.parse(data.removeAt(0), DateTimeFormatter.ofPattern("M/dd/yyyy")),
            data.removeAt(0).fixNumFormat().toBigDecimal(),
            data.removeAt(0).fixNumFormat().toBigDecimal(),
            data.removeAt(0).fixNumFormat().toBigDecimal(),
        ))

    }

    return TestCase(conf, installments)
}

fun String.fixNumFormat() = this.replace(",", "")
