import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import java.io.File
import java.math.RoundingMode
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

const val OUTPUT_PATH = "output.json"

val skipList = listOf(4)

val mapper = ObjectMapper().registerModule(JavaTimeModule())

fun main() {

    val testCases = LinkedList<ScheduleTestCase>()

    val lineIterator = File("src/main/resources/source.txt").readLines().filter { it.isNotBlank() }.iterator()

    while (lineIterator.hasNext())
        testCases.add(readCase(lineIterator))

    mapper.writerWithDefaultPrettyPrinter().writeValue(File(OUTPUT_PATH), testCases)

}

fun readCase(lineIterator: Iterator<String>): ScheduleTestCase {

    val conf = TestScheduleConfiguration(
        capital = lineIterator.next().fixNumFormat().toBigDecimal(),
        installmentAmount = lineIterator.next().toInt(),
        installmentType = InstallmentType.valueOf(lineIterator.next()),
        interestRate = lineIterator.next().toDouble() / 100,
        withdrawalDate = LocalDate.parse(lineIterator.next(), DateTimeFormatter.ofPattern("M/d/yyyy")),
        age = lineIterator.next().toInt(),
        commissionRate = lineIterator.skip(1).next().toDouble() / 100,
        monthFrame = lineIterator.next().toInt(),
        insurance = true,
    )

    val aprc = lineIterator.next().fixNumFormat().toBigDecimal() / 100.toBigDecimal()

    val installments = LinkedList<Installment>()
    val insurancePremiums = LinkedList<InsurancePremium>()

    repeat(conf.installmentAmount) {
        val data = lineIterator
            .next()
            .split("""\s""".toRegex())
            .toMutableList()
            .apply { skipList.forEach { removeAt(it) } }
            .iterator()

        installments.add(Installment(
            index = data.next().fixNumFormat().toInt(),
            installmentDate = LocalDate.parse(data.next(), DateTimeFormatter.ofPattern("M/d/yyyy")),
            capitalInstallment = data.next().fixNumFormat().bd(),
            interestInstallment = data.next().fixNumFormat().bd(),
            remainingDebt = data.next().fixNumFormat().bd(),
        ))
        
        val insuranceValue = data.next().bd()

        if(insuranceValue > 0.toBigDecimal()) insurancePremiums.add(InsurancePremium(
            index = insurancePremiums.size + 1,
            insurancePremiumDate = installments.last.installmentDate,
            insuranceValue
        ))
    }

    val summary = lineIterator
        .next()
        .split("""\s""".toRegex())
        .iterator()

    return ScheduleTestCase(
        testScheduleConfiguration = conf,
        installmentList = installments,
        insurancePremiumList = insurancePremiums,
        loanPaidOutAmount = summary.next().fixNumFormat().bd(),
        commissionAmount = summary.next().fixNumFormat().bd(),
        sumUpCapitalInstallment = summary.next().fixNumFormat().bd(),
        loanTotalCost = summary.skip(2).next().fixNumFormat().bd(),
        insuranceTotalAmount = summary.next().fixNumFormat().bd(),
        aprc = aprc,
    )
}

fun String.fixNumFormat() = this.replace(",", "")

fun <T> Iterator<T>.skip(amount: Int): Iterator<T> = apply { repeat(amount) { next() } }

fun String.bd() = toBigDecimal().setScale(2, RoundingMode.HALF_UP)
