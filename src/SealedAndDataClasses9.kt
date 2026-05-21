/**
 * To declare a sealed class, use the sealed keyword.
 * Its subclasses must be defined within the same package or module.
 *
 * sealed class可以认为是枚举类的扩展，
 */
sealed class NetworkResult {
    // Subclass A: A regular object (no data needed)
    object Loading : NetworkResult()

    // Subclass B: A data class holding a success payload
    data class Success(val responseBody: String) : NetworkResult()

    // Subclass C: A data class holding error details
    data class Failure(val statusCode: Int, val message: String) : NetworkResult()
}

fun handleNetworkState(result: NetworkResult) {
    // The compiler enforces that ALL 3 subclasses are handled
    when (result) {
        // object这种单例可以用is 也可以不用is  data class这种就必须用is
        NetworkResult.Loading -> println("Loading...")
        /**
        is NetworkResult.Loading -> {
            // showLoadingSpinner()
        }*/
        is NetworkResult.Success -> {
           // parseData(result.responseBody) // Smart cast: result is automatically treated as 'Success'
        }
        is NetworkResult.Failure -> {
            // showErrorMessage(result.message)
        }
        // NO 'else' block needed!
    }

    val suc = NetworkResult.Success("asdas")
    val suc2 = NetworkResult.Success("asdas")
    val suc3 = suc.copy("asdas")  // suc == suc2 == suc3
    /**
     * 注意 == 表示比较两个class的属性是否完全一样，相当于java中调用equals方法
     * === 表示比较两个class的地址是否相等，相当于java中的==
     */
    if (suc2 == suc) {
        // 最终这两个dataclass是equals的
        println("data class are equals")
    } else {
        println("data class are not equals")
    }
    if (suc == suc3) {
        println("copy data class are equals")
    }

    val normal = Normal("1")
    val normal2 = Normal("1")
    // 这种非data class 即使属性一样，也返回false
    println(normal ==  normal2)
}

class Normal(val key: String)


fun main() {
    handleNetworkState(NetworkResult.Loading)
}
