package isel.tds.stack.immutable

/**
 * Example of that uses the Stack Immutable.
 */
fun main() {
    var stk = Stack<Char>()
    stk = stk.push('A')
    stk = stk.push('B')
    print(stk.top)  // Output: B
    stk = stk.push('C')
    while( ! stk.isEmpty() ) {
        print(stk.top)
        stk = stk.pop()
//        val res = stk.pop2()
//        print(res.first)
//        stk = res.second
    }
    // Output: BCBA
}