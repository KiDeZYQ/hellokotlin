/**
 * In Kotlin, the fundamental difference is that a class is a blueprint (you must create instances of it manually),
 * while an object is a blueprint and a single instance combined (automatically created by the system as a Singleton)
 */
object EntityFactory {
    fun create() = Entity2("id", "Kide")
}

class Entity2(val id: String, val name: String) {
    override fun toString(): String {
        return "id: $id, name: $name"
    }
}

fun main() {
    val entity = EntityFactory.create()
    println(entity)
}