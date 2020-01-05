package tech.openEdgn.logger4k

import java.lang.Exception
import java.lang.RuntimeException
import java.text.SimpleDateFormat

abstract class OutputFormat<T>(private val format: String) : Comparable<OutputFormat<Any>> {
    abstract val formatHeader: String
    abstract val formatFoot: String
    protected abstract val defaultParams: String
    private var index: Int = -1
    private var rawParams:String = ""
    fun init():OutputFormat<T>{
        params.javaClass
        return this
    }
    protected val params by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
        index = format.indexOf(formatHeader)
        if (index == -1) {
            throw RuntimeException("Item not found.")
        }
        val endIndex = format.substring(index + formatHeader.length)
                .indexOf(formatFoot) + formatHeader.length + index
        if (endIndex == -1) {
            throw RuntimeException("Item end not found.")
        }
        rawParams = format.substring(index + formatHeader.length, endIndex)
        rawParams.let {
            if (it.trim().isEmpty() || (it.trim() == ":")) {
                defaultParams
            }else{
                it.substring(1)
            }
        }
    }
    fun toPropString() = "$formatHeader$rawParams$formatFoot"

    @Throws(Exception::class)
    abstract fun format(data: T): String

    override fun compareTo(other: OutputFormat<Any>): Int {
        return other.index - index
    }

}

//[@{date:YYYY-mm-dd}]-@{level:%5s}-@{classPath:name}-@{message}\n@{throwable:all}


class DateFormat(format: String) : OutputFormat<Long>(format) {
    private val simpleDateFormat = SimpleDateFormat(params)
    override fun format(data: Long): String = simpleDateFormat.format(data)
    override val formatHeader
        get() = "@{date"
    override val formatFoot
        get() = "}"
    override val defaultParams
        get() = "yyyy-MM-dd HH:mm:ss"
}

class LevelFormat(format: String) : OutputFormat<LoggerLevel>(format) {
    override fun format(data: LoggerLevel): String = String.format(params, data.toString())
    override val formatHeader
        get() = "@{level"
    override val formatFoot
        get() = "}"
    override val defaultParams
        get() = "%s"
}
class ClassFormat(format: String) : OutputFormat<Class<out Any>>(format) {
    override fun format(data: Class<out Any>): String = when (params) {
        "name" -> data.simpleName
        else -> data.name
    }
    override val formatHeader
        get() = "@{classPath"
    override val formatFoot
        get() = "}"
    override val defaultParams
        get() = "name"
}
class MessageFormat(format: String) : OutputFormat<String>(format) {
    override fun format(data: String): String = data
            .let { if(it.length > params.toInt()){it.substring(0,params.toInt())}else{it} }
    override val formatHeader
        get() = "@{message"
    override val formatFoot
        get() = "}"
    override val defaultParams
        get() = Integer.MAX_VALUE.toString()
}