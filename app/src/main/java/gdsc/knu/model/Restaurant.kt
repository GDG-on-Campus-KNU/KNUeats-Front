package gdsc.knu.model

data class Restaurant(
    val id: Long,
    val name: String,
    val description: String,
    val tel: String,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val location: String,
    val menu: List<MenuItem>,
    val category: Category,
    val score: Double,
    val review: Int
)
