package isel.tds.stack.immutable

/**
 * An immutable stack generic.
 */
class Stack1<T> private constructor(private val head: Node<T>?): Iterable<T>{
    private class Node<T>(val elem: T, val next: Node<T>?)
    constructor() : this(null)
    private val first get() = head ?: throw NoSuchElementException("stack empty")

    fun push(elem: T) = Stack1(Node(elem,head))
    fun pop(): Stack1<T> = Stack1(first.next)
    val top: T get() = first.elem
    fun isEmpty(): Boolean = head==null
    fun pop2(): Pair<T, Stack1<T>> = top to pop()

/* fun forEach(action: (T)->Unit) {
        var node = head
        while (node != null) {
            action(node.elem)
            node = node.next
        }
    }  */
    override fun iterator(): Iterator<T> = object : Iterator<T> {
        private var node: Node<T>? = head
        override fun hasNext() = node!=null
        override fun next(): T =
            (node ?: throw NoSuchElementException("no more elements"))
                .also { node = it.next }.elem
/*     {
            val n = node ?: throw NoSuchElementException("no more elements")
            node = n.next
            return n.elem
        }  */
    }

    override fun equals(other: Any?): Boolean {
        if (other !is Stack1<*>) return false
        var n = other.head
        for(e in this) {
            if (n==null || e != n.elem) return false
            n = n.next
        }
        return n==null
    }

    override fun hashCode(): Int = fold(0){ acc, e -> acc*31 + e.hashCode() }
/*  {
        var res = 0
        for(e in this) res = res*31 + e.hashCode()
        return res
    }   */
}