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
    @Test fun `equality of stacks`() {
        val stk1 = MutableStack<Int>()
        stk1.push(10)
        stk1.push(20)
        val stk2 = MutableStack<Int>()
        stk2.push(10)
        stk2.push(20)
        //assertTrue(stk1.equals(stk2))
        assertEquals(stk1, stk2)
        assertEquals(stk1.hashCode(), stk2.hashCode())
        stk2.push(30)
        assertNotEquals(stk1, stk2)
        assertNotEquals(stk1.hashCode(), stk2.hashCode())
    }
}