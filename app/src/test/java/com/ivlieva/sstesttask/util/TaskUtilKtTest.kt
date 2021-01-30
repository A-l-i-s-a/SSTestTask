package com.ivlieva.sstesttask.util

import org.junit.Assert
import org.junit.Test

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
}