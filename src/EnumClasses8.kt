import java.util.Locale
import java.util.Locale.getDefault

enum class EntityType {
    EASY,
    MEDIUM,
    HARD;

    fun getFormatName(): String {
        return name.lowercase(getDefault()).capitalize()
    }
}

object EntityFactory2 {
    fun create(entityType: EntityType): Entity3 {
        val name = when (entityType) {
            EntityType.EASY -> entityType.name
            EntityType.MEDIUM -> entityType.getFormatName()
            EntityType.HARD -> "hard"
        }
        return Entity3(name = name, id = "id1")
    }
}

class Entity3(val id: String, val name: String) {
    override fun toString(): String {
        return "Entity3(id='$id', name='$name')"
    }
}


fun main() {
    val entity = EntityFactory2.create(EntityType.EASY)
    println(entity)
}