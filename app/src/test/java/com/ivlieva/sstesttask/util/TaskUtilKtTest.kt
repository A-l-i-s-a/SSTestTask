package com.ivlieva.sstesttask.util

import org.junit.Assert
import org.junit.Test
import java.sql.Timestamp
import java.time.*

class TaskUtilKtTest {

    @Test
    fun timeStrToMillis() {
        var timeStrToMillis = timeStrToMillis("13:00")
        Assert.assertEquals(46800000, timeStrToMillis)
        timeStrToMillis = timeStrToMillis("13:01")
        Assert.assertEquals(46860000, timeStrToMillis)
        timeStrToMillis = timeStrToMillis("13:01:01")
        Assert.assertEquals(46861000, timeStrToMillis)
    }

    @Test
    fun formatDate() {
        val date: LocalDate = LocalDate.of(2021, 7, 23)
        Assert.assertEquals("23 JULY 2021", formatDate(date))
    }

    @Test
    fun formatTime() {
        val time: LocalTime = LocalTime.of(2, 15)
        Assert.assertEquals("2:15", formatTime(time))
    }

    @Test
    fun testFormatTime() {
        val time: Timestamp = Timestamp(1613235222000)
        Assert.assertEquals("20:53", formatTime(time))
    }

    @Test
    fun formatDateTime() {
        val dateTime: OffsetDateTime = OffsetDateTime.ofInstant(Instant.ofEpochMilli(1613235222000), ZoneId.systemDefault());
        Assert.assertEquals("SATURDAY, 13 FEBRUARY 2021, 20:53", formatDateTime(dateTime))
    }
}