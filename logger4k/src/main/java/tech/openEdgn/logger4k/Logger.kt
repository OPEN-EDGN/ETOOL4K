package tech.openEdgn.logger4k

import kotlin.reflect.KClass

/**
 * 日志实现类
 */
class Logger private constructor(private val clazz: Class<out Any>) :Log{

    override fun debugOnly(t: (Log) -> Unit): Log {
        if (isDebug){
            t(this)
        }
        return this
    }


    override fun debugOnly(debugOnly: DebugOnly): Log {
        if (isDebug){
            debugOnly.run(this)
        }
        return this
    }

    override fun info(message: String, exception: Throwable?): Log {
        outputLogger(System.nanoTime(), Thread.currentThread().name, LoggerLevel.INFO, message, exception)
        return this
    }

    override fun debug(message: String, exception: Throwable?): Log {
        outputLogger(System.nanoTime(), Thread.currentThread().name, LoggerLevel.DEBUG, message, exception)
        return this
    }

    override fun warn(message: String, exception: Throwable?): Log {
        outputLogger(System.nanoTime(), Thread.currentThread().name, LoggerLevel.WARN, message, exception)
        return this
    }

    override fun error(message: String, exception: Throwable? ): Log {
        outputLogger(System.nanoTime(), Thread.currentThread().name, LoggerLevel.ERROR, message, exception)
        return this
    }

     private fun outputLogger(loggerDate: Long, threadName:String, level: LoggerLevel, message: String, exception: Throwable?){
         LoggerConfig.output.writeLine(LoggerOutput.OutputItem(clazz,loggerDate,threadName,level,message,exception))
     }


     override val isDebug: Boolean = LoggerConfig.isDebug

}