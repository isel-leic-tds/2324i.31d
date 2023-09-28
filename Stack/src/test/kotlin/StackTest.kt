import kotlin.test.*
import isel.tds.stack.immutable.*

class StackTest {
    @Test fun `empty stack conditions`() {
        val stk: Stack<Int> = Stack()
        assertTrue(stk.isEmpty())
        assertFailsWith<NoSuchElementException> { stk.top }
        assertFailsWith<NoSuchElementException> { stk.pop() }
    }
    @Test fun `not empty stack conditions`() {
        val stk = Stack<String>().push("ISEL")
        assertFalse(stk.isEmpty())
        assertEquals("ISEL", stk.top)
        assertTrue(stk.pop().isEmpty())
    }
    @Test fun `stack operations`() {
        val stk = Stack<Char>().push('A').push('B').push('C')
        assertEquals('C', stk.top)
        assertEquals('B', stk.pop().top)
        val one = stk.pop().pop()
        assertEquals('A', one.top)
    }
}
