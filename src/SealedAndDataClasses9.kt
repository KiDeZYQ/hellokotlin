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
}

