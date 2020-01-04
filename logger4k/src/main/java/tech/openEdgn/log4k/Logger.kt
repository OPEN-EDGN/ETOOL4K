package tech.openEdgn.log4k

/**
 * 日志抽象类
 */
abstract class Logger protected constructor(clazz: Class<out Any>) {
    enum class LoggerLevel(levelInt: Int) {
        DEBUG(1),
        INFO(2),
        WARN(3),
        ERROR(4)
    }

    @JvmOverloads
    fun info(message: String, exception: Throwable? = null) {
        outputLogger(System.nanoTime(), Thread.currentThread(), LoggerLevel.INFO, message, exception)
    }

    @JvmOverloads
    fun debug(message: String, exception: Throwable? = null) {
        outputLogger(System.nanoTime(), Thread.currentThread(), LoggerLevel.DEBUG, message, exception)

    }

    @JvmOverloads
    fun warn(message: String, exception: Throwable? = null) {
        outputLogger(System.nanoTime(), Thread.currentThread(), LoggerLevel.WARN, message, exception)
    }

    @JvmOverloads
    fun error(message: String, exception: Throwable? = null) {
        outputLogger(System.nanoTime(), Thread.currentThread(), LoggerLevel.ERROR, message, exception)
    }

    protected abstract fun outputLogger(loggerDate: Long, thread: Thread, level: LoggerLevel, message: String, exception: Throwable?)

    val debug: Boolean
        get() =  isDebug

    protected open val isDebug: Boolean = LoggerConfig.isDebug()

    companion object{
//        @JvmStatic
//        var default:Logger = kotlin.run {
//            Class.forName()
//        }
    }
}