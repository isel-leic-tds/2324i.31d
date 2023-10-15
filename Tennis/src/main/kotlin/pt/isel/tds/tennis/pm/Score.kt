package pt.isel.tds.tennis.pm

import pt.isel.tds.tennis.Player
import pt.isel.tds.tennis.pm.Points.*

enum class Points(val value: Int) {
    LOVE(0), FIFTEEN(15), THIRTY(30), // FORTY(40) //, GAME(45)
}

fun Points.next() = Points.entries[ordinal+1]

sealed interface Score
class Game(val winner:Player) : Score
class Advantage(val player:Player) : Score
object Deuce : Score
class PointsScore(val pointsA: Points, val pointsB: Points) : Score
class FortyScore(val player: Player, val pointsOther: Points) : Score

val Score.placard get() = when(this) {
    is Game -> "Game $winner"
    is Advantage -> "Advantage $player"
    Deuce -> "Deuce"
    is PointsScore -> "${pointsA.value} - ${pointsB.value}"
    is FortyScore ->
        if (player==Player.A) "40 - ${pointsOther.value}"
        else "${pointsOther.value} - 40"
}

val Score.isGame get() = this is Game

fun Score.next(win: Player): Score = when(this) {
    is Game -> error("Game is over")
    is Advantage ->
        if (win==player) Game(player)
        else Deuce
    Deuce -> Advantage(win)
    is PointsScore -> when {
        pointsOf(win)== THIRTY -> FortyScore(win, pointsOf(win.other))
        win== Player.A -> PointsScore(pointsA.next(), pointsB)
        else -> PointsScore(pointsA, pointsB.next())
    }
    is FortyScore -> when {
        win==player -> Game(player)
        pointsOther==THIRTY -> Deuce
        else -> FortyScore(player, pointsOther.next())
    }
}

fun PointsScore.pointsOf(player: Player) =
    if(player==Player.A) pointsA else pointsB

val initialScore = PointsScore(LOVE,LOVE)