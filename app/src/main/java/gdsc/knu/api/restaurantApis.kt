package gdsc.knu.api

import gdsc.knu.model.Restaurant

fun getRestaurant(id:Long): Restaurant = Restaurant(
    id,
    "모이다식탁",
    "경북대학교정문에서 대구공고로 내려오는 길에 해비치커피1층에 있습니다.^,^",
    "0507-1328-1097",
    "대구 북구 경대로7길 2 1층",
    66.87,
    127.32,
    listOf("소세지오므라이스","베이컨새우볶음밥"),
    "한식"
)

fun getRestaurants(): Array<Restaurant> = arrayOf(
    Restaurant(
        1,
        "김밥천국",
        "김밥을 팝니다.",
        "010-1234-5678",
        "위치1",
        35.88479847978367,
        128.61380871085896,
        listOf(),
        "분식"
    ),
    Restaurant(
        2,
        "CU",
        "편의점입니다.",
        "010-1234-5678",
        "위치2",
        35.88501360108289,
        128.61392511273948,
        listOf(),
        "편의점",
    )
)