package winform.models

data class Order(
    val id: Int,
    val pelanggan_Id: Int,
    val weight: Double,
    val status: String
)