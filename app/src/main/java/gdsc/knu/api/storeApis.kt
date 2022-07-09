package gdsc.knu.api

import gdsc.knu.model.Store

fun getStores(): Array<Store> = arrayOf(
    Store(
        1,
        "김밥천국",
        "김밥을 팝니다.",
        "010-1234-5678",
        "위치1",
        35.88479847978367,
        128.61380871085896,
        arrayOf(),
        "분식"
    ),
    Store(
        2,
        "CU",
        "편의점입니다.",
        "010-1234-5678",
        "위치2",
        35.88501360108289,
        128.61392511273948,
        arrayOf(),
        "편의점",
    )
)