package tech.openEdgn.logger4k

import java.io.PrintStream
import kotlin.reflect.KClass



object LoggerConfig {


    const val outputFormat: String= "[@{date:yyyy/MM/dd HH:mm:ss}]-[@{level:%-5s}]-@{classPath:name}-@{message}\n@{throwable:all}"

    val output: LoggerOutput by lazy (LazyThreadSafetyMode.SYNCHRONIZED){ LoggerOutput(this) }

    val implClass: KClass<out Log> = Logger::class

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