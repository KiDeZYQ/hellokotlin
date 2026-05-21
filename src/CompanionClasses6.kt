interface IdProvider {
    fun getId(): String
}

// 可以将构造函数定义为私有 然后通过Companion来实现static的效果供外部使用
class Entity private constructor(id: String) {
    // companion object中定义的类似于类的static属性 这个类的名字默认是Companion

    companion object Factory : IdProvider {
        @JvmField // @JVMStatic 使用这两个注解，可以在外部直接使用 Entity.create来访问对应属性
        val create = Entity(getId())

        const val ID = "id"
        override fun getId(): String {
            return "123"
        }
    }
}

fun main() {

    val entity = Entity.Factory.create
    println(Entity.getId())
}