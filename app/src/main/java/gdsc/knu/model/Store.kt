package gdsc.knu.model

data class Store(
    val id: Long,
    val name: String,
    val description: String,
    val tel: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val menu: List<String>,
    val category: String
)
