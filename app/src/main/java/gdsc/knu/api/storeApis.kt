package gdsc.knu.api

import gdsc.knu.model.Store

fun getStore(id:Long): Store = Store(
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