package isel.tds.stack.mutable

/**
 * A mutable stack generic
 * Using an immutable list to store the elements
 */
class MutableStack1<T> {
    private var elems = emptyList<T>()
    fun push(elem: T) { elems = elems + elem }
    fun pop(): T {
        val elem = elems.last()
        elems = elems.dropLast(1)
        return elem
    }
    val top: T get() = elems.last()
    fun isEmpty() = elems.isEmpty()
}

/**
 * A mutable stack generic
 * Using a mutable list to store the elements
 */
class MutableStack<T> {
    private val elems = mutableListOf<T>()
    fun push(elem: T) { elems.add(elem) }
    fun pop() = top.also { elems.removeLast() }
//    : T {
//        val elem = top
//        elems.removeLast()
//        return elem
//    }
    val top: T get() = elems.last()
    fun isEmpty() = elems.isEmpty()
}