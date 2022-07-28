package gdsc.knu.model

enum class Category(val displayName: String, val code: String) {
    KOREA("한식", "한식"),
    SNACK("분식", "분식"),
    WESTERN("양식", "양식"),
    JAPANESE("일식", "일식"),
    CAFE("카페/디저트", "카페"),
    CHINESE("중식", "중식"),
    ASIAN("아시안", "아시안"),
    BAR("술집", "술집"),
    ETC("기타", "기타")
}