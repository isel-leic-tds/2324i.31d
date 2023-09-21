import kotlin.test.*
import isel.tds.stack.mutable.MutableStack

class MutableStackTest {
    @Test fun `empty stack conditions`() {
        val stk = MutableStack<Int>()
        assertTrue(stk.isEmpty())
        assertFailsWith<NoSuchElementException> { stk.top }
        assertFailsWith<NoSuchElementException> { stk.pop() }
        stk.push(10)
        assertFalse(stk.isEmpty())
    }
    @Test fun `not empty stack conditions`() {
        val stk = MutableStack<String>()
        stk.push("ISEL")
        stk.push("TDS")
        assertFalse(stk.isEmpty())
        assertEquals("TDS", stk.top)
        assertEquals("TDS", stk.pop())
        assertEquals("ISEL", stk.pop())
        assertTrue(stk.isEmpty())
    }
}