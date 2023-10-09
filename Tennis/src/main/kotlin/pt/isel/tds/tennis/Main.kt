package pt.isel.tds.tennis

import pt.isel.tds.tennis.one.*

fun main() {
    var score: Score = initialScore
    while(true) {
        println(score.placard)
        if (score.isGame) break
        score = score.next(readWinner())
    }
}

tailrec fun readWinner(): Player {
    print("Winner A or B ? ")
    return readln().trim().firstOrNull()?.toPlayerOrNull() ?: readWinner()
}