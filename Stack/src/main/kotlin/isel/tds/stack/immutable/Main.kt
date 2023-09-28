package isel.tds.stack.immutable

/**
 * Example that uses the Stack Immutable.
 */
fun main() {
    var stk = Stack<Char>().push('A').push('B')
    println(stk.top)  // Output: B
    stk = stk.push('C')
    print("ForEach:")
    for(e in stk) print(e)
    println()
    while( ! stk.isEmpty() ) {
        print(stk.top)
        stk = stk.pop()
        if (! stk.isEmpty()) {
            val res = stk.pop2()
            print(res.first)
            stk = res.second
        }
    }
    // Output: BCBA
}