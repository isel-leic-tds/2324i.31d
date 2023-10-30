import pt.isel.tds.ttt.model.*
import kotlin.test.*

class GameSerializerTest {
    @Test
    fun `Serialize and deserialize game`() {
        val game = Game(null, Player.O, mapOf(Player.X to 1, Player.O to 2, null to 3))
        val text = GameSerializer.serialize(game)
        val game2 = GameSerializer.deserialize(text)
        assertEquals(game, game2)
    }
}