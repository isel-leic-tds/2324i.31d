
import pt.isel.tds.storage.*
import kotlin.test.*

object IntSerializer: Serializer<Int> {
    override fun serialize(data: Int): String = data.toString()
    override fun deserialize(text: String): Int = text.toInt()
}

class FileStorageTest {
    @Test
    fun `Create, read, update and delete entry`() {
        val st = TextFileStorage<Int,Int>("data", IntSerializer)
        st.create(1, 1)
        assertEquals(1, st.read(1))
        st.update(1, 2)
        assertEquals(2, st.read(1))
        st.delete(1)
    }
    @Test fun `Read non-existing entry`() {
        val st = TextFileStorage<Int,Int>("data", IntSerializer)
        assertNull(st.read(3))
    }
}