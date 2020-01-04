package tech.openEdgn.log4k


object Log4kExtension {
    init {

    }
}


inline fun Any.info() = run {
    println(this)
    println(javaClass.name)
}

inline fun Any.warn() = run { println(this) }
inline fun Any.error() = run { println(this) }