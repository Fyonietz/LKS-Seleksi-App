package winform.utils

object SessionManager {
    var clientId: Int = 0
    var clientName: String = ""

    fun clear() {
        clientId = 0
        clientName = ""
    }
}