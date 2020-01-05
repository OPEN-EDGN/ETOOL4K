package tech.openEdgn.logger4k

import java.util.concurrent.Executors

class LoggerOutput(private val loggerConfig: LoggerConfig) {
    private val threadPool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors())

    private val format = LineFormat(loggerConfig.outputFormat)
    init {
        Runtime.getRuntime().addShutdownHook(Thread(Runnable {
            threadPool.shutdown() }))
        //注册自动销毁日志模块
    }

    data class LoggerOutputItem(val clazz: Class<out Any>, val  loggerDate: Long, val  threadName: String, val  level: LoggerLevel, val  message: String, val  exception: Throwable?)

    @Synchronized
    fun writeLine(outputItem: LoggerOutputItem) {
        threadPool.execute {
            LoggerConfig.commandOutput.println(format.format(outputItem))
        }
    }




}