package isel.tds.stack.immutable

/**
 * An immutable stack generic.
 */
class Stack<T> {
    fun push(elem: T): Stack<T> = TODO()
    fun pop(): Stack<T> = TODO()
    val top: T get() = TODO()
    fun isEmpty(): Boolean = TODO()
    fun pop2(): Pair<T, Stack<T>> = TODO()
}