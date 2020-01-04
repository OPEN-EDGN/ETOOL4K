package tech.openEdgn.log4k

class CommandLogger(clazz: Class<out Any>) : Logger(clazz) {
    override fun outputLogger(loggerDate: Long, thread: Thread, level: LoggerLevel, message: String, exception: Throwable?) {

    }



}