package gdsc.knu.model

data class RestaurantCreateRequest (
    val name: String,
    val description: String,
    val tel: String,
    val address: String,
    val menu: List<String>,
    val category: String
)