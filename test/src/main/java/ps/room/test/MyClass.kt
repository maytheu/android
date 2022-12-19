package ps.room.test

fun main() {
    val immu = "Val cannot be change"
    var mutt = "var can be change"

    println("$immu, $mutt")

    println("Condition ..............")
    val amount = 1000
    if (amount >= 1000) {
        println("You're rich")
    } else {
        println("You'll be fine")
    }

    println("USing Kotlin when and range similar to switch.......")
    when (amount) {
        1000 -> println("Wow, youre rich")
        in 1..999 -> println("Not too rich")
        else -> {
            println("extremely reach")
        }
    }

    println("loops in kotlin .......................")
    for (i in 1..10) {
        when (i % 3) {
            0 -> println("$i is multiple of 3")
        }
        //if (i % 3 == 0) println("multiples of 3 - $i")

    }
}
