package tech.openEdgn.log4k

object LoggerConfig {
    private var isDebugMode = false
    /**
     * 判断是否为调试模式
     */
    fun isDebug() = isDebugMode


    private val config = HashMap<String, String>()
    private val configDir = arrayOf(
            "classpath:/log4k-default.properties",
            "classpath:/res/log4k.properties",
            "classpath:/res/raw/log4k.properties",
            "classpath:/log4k.properties",
            "file:*.properties",
            "file:config/*.properties"
    )
    init {

    }
}