package ps.maytheu.tipapp.util

fun calculateTip(total: Double, tipPercentage: Int): Double {
    return if (total > 1 && total.toString().isNotEmpty()) (total * tipPercentage) / 100 else 0.0
}

fun calculatePerPerson(total: Double, splitBy: Int, tipPercentage: Int): Double {
    val bill = calculateTip(total = total, tipPercentage = tipPercentage) + total
    return bill / splitBy
}