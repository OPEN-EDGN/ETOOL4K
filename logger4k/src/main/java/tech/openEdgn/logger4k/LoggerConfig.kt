package tech.openEdgn.logger4k

import java.io.PrintStream
import kotlin.reflect.KClass



object LoggerConfig {

    val output: LoggerOutput by lazy { LoggerOutput(this) }
    val implClass: KClass<out Log> = Logger::class

    val  outputFormat = mapOf(Pair("info","asa"))

    @Volatile
    var commandOutput:PrintStream = System.out

    @Volatile
    var commandErrOutput:PrintStream = System.err

    @Volatile
    var isDebug = false

    init {

    }


    fun enableDebug() = run {
        isDebug = true
        this
    }

    fun disableDebug() = run {
        isDebug = false
        this
    }

}