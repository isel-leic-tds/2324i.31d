package isel.tds.stack.immutable

interface Stack<T> : Iterable<T> {
    fun push(elem: T): Stack<T>
    fun pop(): Stack<T>
    val top: T
    fun isEmpty(): Boolean
}

@Suppress("UNCHECKED_CAST")
fun <T> Stack(): Stack<T> = EmptyStack as Stack<T>

private class Node<T>(val elem: T, val next: Node<T>?)

private object EmptyStack: Stack<Any> {
    private fun throwEmpty(): Nothing = throw NoSuchElementException("empty stack")
    override fun push(elem: Any) = NoEmptyStack(Node(elem,null))
    override fun pop() = throwEmpty()
    override val top get() = throwEmpty()
    override fun isEmpty() = true
    override fun iterator() = object : Iterator<Nothing> {
        override fun hasNext() = false
        override fun next() = throwEmpty()
    }
}

private class NoEmptyStack<T>(val head: Node<T>): Stack<T> {
    override fun push(elem: T) = NoEmptyStack( Node(elem,head))
    override fun pop() = head.next?.let { NoEmptyStack(it) } ?: Stack()
    override val top get() = head.elem
    override fun isEmpty() = false
    override fun iterator() = object : Iterator<T> {
        private var node: Node<T>? = head
        override fun hasNext() = node!=null
        override fun next(): T =
            (node ?: throw NoSuchElementException("no more elements"))
                .also { node = it.next }.elem
    }
    @Suppress("UNCHECKED_CAST")
    override fun equals(other: Any?): Boolean =
        other is NoEmptyStack<*> && equalNodes(head,other.head as Node<T>)

    override fun hashCode() = fold(0){ acc, e -> acc*31 + e.hashCode() }
}

private tailrec fun <T> equalNodes(a: Node<T>, b: Node<T>): Boolean = when {
    a.elem != b.elem -> false
    a.next==null -> b.next==null
    b.next==null -> false
    else -> equalNodes(a.next, b.next)
}

fun <T> stackOf(vararg elems: T): Stack<T> =
    elems.fold(Stack()){ stk, e -> stk.push(e) }
