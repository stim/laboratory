package nl.tbvh.lab.experiment.kotlin


interface SuperInterface {
    val overriddenProperty: Int

    val defaulProperty: Int
        get() = overriddenProperty * 2
}

data class DataClass(
        override val overriddenProperty: Int
) : SuperInterface

fun main(args:Array<String>){
    val dc = DataClass(3)
    
    println(dc.defaulProperty)
}
