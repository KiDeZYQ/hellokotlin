// class Person
/**
class Person(_firstName: String, _lastName: String) {
    val firstName: String = _firstName
    val lastName: String = _lastName

    /**除了上面给属性复制的方式之外，还可以通过init代码块来实现
    init {
        firstName = _firstName
        lastName = _lastName
    }
    */
}*/

/**
 * 默认情况下，不加任何修饰的话 kotlin的类、属性、 方法的作用域都是public
 * 常见的作用域关键字和范围对应如下：
 * Modifier      	      Visible Within
 * public (Default)	        Everywhere.
 * internal	             The same module only.
 * protected	        The declaring class and its subclasses (not allowed for top-level functions).
 * private	            The declaring file or class only.
 *
 * 另外，默认kotlin定义的类和属性都是final的，如果想要其他类继承该类，则需要在类和属性定义前增加open关键字
 */
class Person(val firstName: String, val lastName: String) {
    init {
        println("init 1")
    }

    // 可以定义第二个不带参数的构造函数，使用默认的只来初始化
    constructor(): this("Kide", "Zi") {
        println("constructor 2")
    }

    init {
        println("init 2")
    }

    var nickName: String? = null
        // 默认nickName属性就有get、set方法，如果想定制可以在下面接着缩进进行设置
        set(value) {
            field = value
            println("nickName set to $value")
        }
        get() {
            println("getNickName $field")
            return field
        }

    fun printPersonInfo() {
        // val nickNameToPrint = if (nickName != null) nickName else "No NickName"
        val nickNameToPrint = nickName ?:  "No NickName"
        println("$firstName ($nickNameToPrint) $lastName")
    }
}

fun main() {
    val person = Person("Yuqi", "Zi")
    /**
     * 输出为：
     * init 1
     * init 2
     * Zi
     */
    println(person.lastName)

    /**
     * 输出为：
     * init 1
     * init 2
     * constructor 2
     * Yuqi
     */
    val person2 = Person()
    println(person.firstName)

    /**
     * nickName set to Kidee
     * getNickName null
     * null
     */
    person2.nickName = "Kidee"
    println(person.nickName)

    /**
     * getNickName null
     * Yuqi (No NickName) Zi
     * // 注意下面这里会输出两次getNickName ...  替换成 nickName ?: 写法之后只会输出一次
     * getNickName Kidee
     * getNickName Kidee
     * Kide (Kidee) Zi
     *
     */
    println("--------------------------")
    person.printPersonInfo()
    person2.printPersonInfo()
}