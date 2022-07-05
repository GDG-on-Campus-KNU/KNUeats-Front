package gdsc.knu.model

data class Store(
    val id: Long,
    val name: String,
    val description: String,
    val tel: String,
    val address: String,
    val lat: Double,
    val lon: Double,
    val menu: List<String>,
    val category: String

)
