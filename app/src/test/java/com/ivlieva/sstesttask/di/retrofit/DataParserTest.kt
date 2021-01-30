package com.ivlieva.sstesttask.di.retrofit

import com.google.gson.Gson
import com.google.gson.JsonParser
import org.junit.Test


class DataParserTest {

    @Test
    fun readServiceUrlMap() {
        val dataParser = DataParser()
        val jsonStr = "{\"-MQaHc1-L74G7TU9NuyQ\":{\"date_finish\":\"Jan 2, 1970 6:51:51 AM\",\"date_start\":\"Jan 2, 1970 6:51:51 AM\",\"description\":\"1\",\"id\":6,\"name\":\"1\",\"urls\":[\"\"]},\"-MQaKsKLqc0EeEHSNWzi\":{\"date_finish\":\"Jan 2, 1970 6:51:51 AM\",\"date_start\":\"Jan 2, 1970 6:51:51 AM\",\"description\":\"----\",\"id\":7,\"name\":\"----\",\"urls\":[\"\"]}}"

        val readServiceUrlMap = dataParser.readServiceUrlMap(JsonParser().parse(jsonStr).asJsonObject)
//        print(readServiceUrlMap)
        if (readServiceUrlMap != null) {
            for ((key, v) in readServiceUrlMap) {
                println("$key - ${v.name}")
            }
        }
    }
}