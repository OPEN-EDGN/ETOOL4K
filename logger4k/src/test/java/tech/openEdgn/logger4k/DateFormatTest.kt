package tech.openEdgn.logger4k

import org.junit.Test


internal class DateFormatTest{
    @Test
    fun test() {
        val date = "[@{date:yyyy-MM-dd HH:mm:ss}]-@{level}-@{classPath:path}-@{message:1}\n@{throwable:all}"
        val dateFormat = MessageFormat(date)
        dateFormat.init()
        var oldValue = dateFormat.toPropString()
        println(date.replace(oldValue, "sasasasa"))
        println(dateFormat.format("sasaas"))
    }
}