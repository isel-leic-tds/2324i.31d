package pt.isel.tds.tennis

enum class Player{ A, B }

fun Char.toPlayerOrNull(): Player? = when(this) {
    'A','a' -> Player.A
    'B','b' -> Player.B
    else -> null
}