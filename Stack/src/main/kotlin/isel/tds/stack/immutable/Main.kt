package isel.tds.stack.immutable

/**
 * Example that uses the Stack Immutable.
 */
fun main() {
    var stk = stackOf('A','B')
    println(stk.top)  // Output: B
    stk = stk.push('C')
    print("ForEach:")
    for(e in stk) print(e)
    println()
    while( ! stk.isEmpty() ) {
        print(stk.top)
        stk = stk.pop()
    }
    print( sizeOf(Stack<Char>()) )
    print( sizeOf(Stack<Int>().push(3).push(5)) )
    // Output: BCBA02
}

fun <T> sizeOf(s: Stack<T>): Int {
    var size = 0
    val it: Iterator<T> = s.iterator()
    while( it.hasNext() ) {
        ++size
        it.next()
    }
    return size
}