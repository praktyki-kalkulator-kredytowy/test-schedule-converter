import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal
import java.time.LocalDate


data class ScheduleTestCase(
    @field:JsonProperty("mTestScheduleConfiguration")
    var testScheduleConfiguration: TestScheduleConfiguration,
    @field:JsonProperty("mInstallmentList")
    var installmentList: List<Installment>,
    @field:JsonProperty("mInsurancePremiumList")
    var insurancePremiumList: List<InsurancePremium>,
    @field:JsonProperty("mSumUpCapitalInstallment")
    var sumUpCapitalInstallment: BigDecimal,
    @field:JsonProperty("mLoanPaidOutAmount")
    var loanPaidOutAmount: BigDecimal,
    @field:JsonProperty("mCommissionAmount")
    var commissionAmount: BigDecimal,
    @field:JsonProperty("mInsuranceTotalAmount")
    var insuranceTotalAmount: BigDecimal,
    @field:JsonProperty("mLoanTotalCost")
    var loanTotalCost: BigDecimal,
    @field:JsonProperty("mAPRC")
    var aprc: BigDecimal,
)

data class TestScheduleConfiguration(
    @field:JsonProperty("mCapital")
    var capital: BigDecimal,
    @field:JsonProperty("mInstallmentType")
    var installmentType: InstallmentType,
    @field:JsonProperty("mInstallmentAmount")
    var installmentAmount: Int,
    @field:JsonProperty("mInterestRate")
    var interestRate: Double,
    @field:JsonProperty("mWithdrawalDate")
    var withdrawalDate: LocalDate,
    @field:JsonProperty("mCommissionRate")
    var commissionRate: Double,
    @field:JsonProperty("mInsurance")
    var insurance: Boolean,
    @field:JsonProperty("mAge")
    var age: Int,
    @field:JsonProperty("mMonthFrame")
    var monthFrame: Int
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

data class InsurancePremium(
    @field:JsonProperty("mIndex")
    var index: Int,
    @field:JsonProperty("mInsurancePremiumDate")
    var insurancePremiumDate: LocalDate,
    @field:JsonProperty("mInsurancePremiumValue")
    var insurancePremiumValue: BigDecimal,
)

enum class InstallmentType {
    DECREASING,
    CONSTANT
}