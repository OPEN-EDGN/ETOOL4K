package tech.openEdgn.logger4k

import java.io.File
import java.io.FileWriter
import java.io.PrintStream
import java.io.PrintWriter
import java.lang.Exception
import java.lang.management.ManagementFactory
import java.text.SimpleDateFormat
import kotlin.reflect.KClass


object LoggerConfig {
    @Volatile
    var outputFormat: String = "[Thread/#(@{thread})] @{date:yyyy/MM/dd HH:mm:ss} - @{level:%-5s} - @{classPath:path} : @{message} @{throwable:all}"



    @Volatile
    var initConfig: (LoggerConfig) -> Unit = {}

    val output: LoggerOutput by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        initConfig(this)
        if (loggerOutputDir.isFile) {
            loggerOutputDir.delete()
        }
        if (!loggerOutputDir.exists()) {
            loggerOutputDir.mkdirs()
        }
        LoggerOutput(this)
    }

    val implClass: KClass<out Log> = Logger::class

    @Volatile
    var commandOutput: PrintStream = System.out

    @Volatile
    var commandErrOutput: PrintStream = System.err

    @Volatile
    var loggerOutputDir: File  = run { val file = File(System.getProperty("java.io.tmpdir"), "Log")
        if (file.isFile) {
            file.delete()
        }
        if (!file.exists()) {
            file.mkdirs()
        }
        file
    }


    private val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
    private val headerFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

    private val logTempFileOutput: Array<PrintWriter?> = Array(2) { null }
    private val pid by lazy {
        try {
            val process = Class.forName("android.os.Process")
            process.getMethod("myPid").invoke(null)
        }catch (e:Exception){
            try {
                ManagementFactory.getRuntimeMXBean().name.split("@")[0]
            }catch (_:Exception){
                0
            }
        }
    }

    private fun getOutputFile(date: Long = System.currentTimeMillis()) =
            File(loggerOutputDir,
                    "log-${simpleDateFormat.format(date)}.log").let {
                if (!it.isFile) {
                    it.createNewFile()
                    it.writeText("此日志开始于 ${headerFormat.format(System.currentTimeMillis())} ,PID: $pid !\r\n")
                }else{
                    it.appendText("\r\n新的日志开始于 ${headerFormat.format(System.currentTimeMillis())}  ,PID: $pid !\r\n")
                }
                it }

    private var startDate = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis())).time
    private const val oneDay = 24 * 60 * 60 * 1000
    val logFileOutput: PrintWriter
        @Synchronized get() {
            if (logTempFileOutput[1] == null) {
                logTempFileOutput[1] = PrintWriter(FileWriter(getOutputFile(), true), true)
            }
            if (System.currentTimeMillis() > startDate + oneDay) {
                try {
                    logTempFileOutput[0]?.close()
                }catch (e:Exception){}
                logTempFileOutput[0] = logTempFileOutput[1]
                logTempFileOutput[1] = PrintWriter(FileWriter(getOutputFile(), true), true)
            }
            return logTempFileOutput[1]!!
        }

    @Volatile
    var isDebug = false


    fun enableDebug() = run {
        isDebug = true
        this
    }

    fun disableDebug() = run {
        isDebug = false
        this
    }

}