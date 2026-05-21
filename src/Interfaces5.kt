interface PersonInfoProvider {
    val providerInfo: String
    fun printInfo(person: Person) {
        println(providerInfo)
        person.printPersonInfo()
    }
}

interface SessionInfoProvider {
    fun getSessionId(): String
}

open class BasicPersonProvider : PersonInfoProvider, SessionInfoProvider {
    open val sessionIdPrefix = "sessionId"

    override val providerInfo: String
        get() {
            return "Basic Info Provider"
        }

    override fun printInfo(person: Person) {
        super.printInfo(person)
        println("additional statement")
    }

    override fun getSessionId(): String {
        return sessionIdPrefix
    }
}

/**
 * 类的继承
 */
class FancyInfoProvider : BasicPersonProvider() {
    override val sessionIdPrefix: String
        get() = "Fancy Session Id "

    override val providerInfo: String
        get() = "Fancy Info Provider"

    override fun printInfo(person: Person) {
        super.printInfo(person)
        println("fancy info")
    }
}

fun main() {
    // 基础实现接口的类
    // val basicPerson = BasicPersonProvider()
    // 类继承初始化子类
    // val basicPerson = FancyInfoProvider()
    // 匿名类
    val basicPerson = object : PersonInfoProvider {
        override val providerInfo: String
            get() = "New info provider"

        fun getSessionId() = "id"
    }
    basicPerson.printInfo(Person("John", "Doe"))
    checkTypes(basicPerson)
}

fun checkTypes(personInfoProvider: PersonInfoProvider) {
    // 类似于java的instanceof关键字
    if (personInfoProvider !is SessionInfoProvider) {
        println("Not a session info provider")
    } else {
        println("It is a session info provider")
        // 强转成SessionInfoProvider 然后调用getSessionId方法
        (personInfoProvider as SessionInfoProvider).getSessionId()
        // 其实也能自动推断出是SessionInfoProvider而不需要强制转换
        personInfoProvider.getSessionId()
    }
}