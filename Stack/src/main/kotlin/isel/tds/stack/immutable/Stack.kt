package isel.tds.stack.immutable

/**
 * An immutable stack generic.
 */
class Stack<T> private constructor(private val head: Node<T>?): Iterable<T>{
    private class Node<T>(val elem: T, val next: Node<T>?)
    constructor() : this(null)
    private val first get() = head ?: throw NoSuchElementException("stack empty")

    fun push(elem: T) = Stack(Node(elem,head))
    fun pop(): Stack<T> = Stack(first.next)
    val top: T get() = first.elem
    fun isEmpty(): Boolean = head==null
    fun pop2(): Pair<T, Stack<T>> = top to pop()

/*
    fun forEach(action: (T)->Unit) {
        var node = head
        while (node != null) {
            action(node.elem)
            node = node.next
        }
    }
*/
    private class It<E>(private var node: Node<E>?) : Iterator<E> {
        override fun hasNext() = node!=null
        override fun next(): E  {
            val n = node ?: throw NoSuchElementException("no more elements")
            node = n.next
            return n.elem
        }
    }
    override fun iterator(): Iterator<T> = It(head)
}