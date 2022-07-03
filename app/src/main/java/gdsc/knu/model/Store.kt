package gdsc.knu.model

data class Store(
    val id: Int,
    val name: String,
    val description: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val representMenu: Array<String>,
    val category: String,
    val openingTime: String
)
