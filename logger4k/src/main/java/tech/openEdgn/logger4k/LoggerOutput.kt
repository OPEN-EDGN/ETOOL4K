package tech.openEdgn.logger4k

import java.io.Closeable
import java.util.concurrent.Executors

class LoggerOutput(private val loggerConfig: LoggerConfig) {
    private val threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    init {
        Runtime.getRuntime().addShutdownHook(Thread(Runnable { threadPool.shutdownNow() }))
        //注册自动销毁日志模块
    }

    data class OutputItem(val clazz: Class<out Any>,val  loggerDate: Long,val  threadName: String,val  level: LoggerLevel,val  message: String,val  exception: Throwable?)

    @Synchronized
    fun writeLine(outputItem: OutputItem) {
        threadPool.submit {

        }
    }




}