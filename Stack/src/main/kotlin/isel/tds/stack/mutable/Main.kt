package isel.tds.stack.mutable

/**
 * Example of that uses the MutableStack.
 */
fun main() {
    val stk = MutableStack<Char>()
    stk.push('A')
    stk.push('B')
    print(stk.top)  // Output: B
    stk.push('C')
    while( ! stk.isEmpty() ) {
        val elem: Char = stk.pop()
        print(elem)
    }
    // Output: BCBA
}