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

    /////////////////////////// COLLECTION IN KOTLIN
    //similar to array in js is list
    val fixedList = listOf<String>("1", "2")
    val mutableList = mutableListOf<Int>(1, 2, 3, 4, 5, 6)
    println("$fixedList list of cant be changed")
    println("$mutableList list of can be modified")

    println()
    println("Similar to listof, setOf cannot be modified, use mutatblesetof")
    println("setof accept only unique values")
    println()

    println("Mapof()")
    val setMap = mapOf<String, Int>("up" to 1, "down" to 2)
    println("mapof $setMap, keys: ${setMap.keys}, \nentries: ${setMap.values}")
    println("mutablemapof(), allows us to mutate map")
    println("https://medium.com/mobile-app-development-publication/kotlin-collection-functions-cheat-sheet-975371a96c4b")

    println("////////////////////////////////////////OOP")
    val car = Car()
    car.desc = "Mutable with var"
    println("Class pps of Car ${car.model}, ${car.desc}")
    println("invoking methoda ${car.drive()}")

    val kontCar = KotlinCar(model = "Toyota")
    println("using kotlin syntax ${kontCar.model}")

    val anotherCar = KotlinCar()
    println(anotherCar.model)

}

//////////////OOP
class Car {
    val color = "Red"
    val model = "Benz"
    var desc: String = ""

    ///////////////Methods
    fun drive() {
        println("Vrooooooooooooooooooooooom")
    }
}

class KotlinCar(var color: String = "Red", var model: String = "Benz") {
    init {
        println("init blco conatins the initial block to run within a lass")
        if(model !== "Benz"){
            println("The model is not initialize by the class")
        }else{
            println("it is initialize by the class")
        }
    }
}
