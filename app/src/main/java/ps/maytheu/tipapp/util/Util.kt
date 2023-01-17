package ps.maytheu.tipapp.util

fun calculateTip(total: Double, tipPercentage: Int): Double {
    return if (total > 1 && total.toString().isNotEmpty()) (total * tipPercentage) / 100 else 0.0
}