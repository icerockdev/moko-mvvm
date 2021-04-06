import kotlin.native.internal.GC

fun collect() {
    GC.collect()
}

fun cycles() {
    val cycles = GC.detectCycles()
    println("cycles count: ${cycles?.size}")
    cycles?.forEachIndexed { index, item -> println("cycle $index : $item") }
}
