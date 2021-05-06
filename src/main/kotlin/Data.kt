import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDate


data class TestCase(
    var configuration: ScheduleConfiguration,
    var installments: List<Installment>
)

data class ScheduleConfiguration(
    @field:JsonProperty("mCapital")
    var capital: BigDecimal,
    @field:JsonProperty("mInstallmentAmount")
    var installmentAmount: Int,
    @field:JsonProperty("mInstallmentType")
    var installmentType: InstallmentType,
    @field:JsonProperty("mInterestRate")
    var interestRate: Double,
    @field:JsonProperty("mWithdrawalDate")
    var withdrawalDate: LocalDate,
)

data class Installment(
    @field:JsonProperty("mIndex")
    var index: Int,
    @field:JsonProperty("mInstallmentDate")
    var installmentDate: LocalDate,
    @field:JsonProperty("mCapitalInstallment")
    var capitalInstallment: BigDecimal,
    @field:JsonProperty("mInterestInstallment")
    var interestInstallment: BigDecimal,
    @field:JsonProperty("mRemainingDebt")
    var remainingDebt: BigDecimal,
)

enum class InstallmentType {
    DECREASING,
    CONSTANT
}