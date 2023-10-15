package pt.isel.tds.tennis

enum class Player{ A, B;
    val other get() = if(this==A) B else A
}

fun Char.toPlayerOrNull(): Player? = when(this) {
    'A','a' -> Player.A
    'B','b' -> Player.B
    else -> null
}