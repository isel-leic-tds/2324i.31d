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
class MutableStack2<T> {
    private val elems = mutableListOf<T>()
    fun push(elem: T) { elems.add(elem) }
    fun pop() = top.also { elems.removeLast() }
    val top: T get() = elems.last()
    fun isEmpty() = elems.isEmpty()
    override fun equals(other: Any?) = other is MutableStack2<*> && elems == other.elems
    override fun hashCode() = elems.hashCode()
}

/**
 * A mutable stack generic
 * Using a single linked list to store the elements.
 */
class MutableStack<T> {
    private class Node<E>(val elem: E, val next: Node<E>?)
    private var head: Node<T>? = null
    private val first: Node<T> get() = head ?: throw NoSuchElementException()

    fun push(elem: T) { head = Node(elem, head) }
    val top: T get() = first.elem
    fun pop() = first.also { head = it.next }.elem
    fun isEmpty() = head == null

    override fun equals(other: Any?): Boolean {
        if (other !is MutableStack<*>) return false
        var n1 = head
        var n2 = other.head
        while( n1 != null && n2 != null ) {
            if (n1.elem != n2.elem) return false
            n1 = n1.next
            n2 = n2.next
        }
        return n1 == null && n2 == null
    }
    override fun hashCode(): Int {
        var hash = 0
        var node = head
        while( node != null ) {
            hash = hash * 31 + node.elem.hashCode()
            node = node.next
        }
        return hash
    }
}
