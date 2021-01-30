package com.ivlieva.sstesttask.di.retrofit

import com.google.gson.*
import com.google.gson.JsonSyntaxException
import com.ivlieva.sstesttask.repository.remote_data_source.retrofit.DataNetworkEntity
import com.ivlieva.sstesttask.repository.remote_data_source.retrofit.TaskNetworkEntity
import java.lang.reflect.Type


class DataParser : JsonDeserializer<DataNetworkEntity> {

    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): DataNetworkEntity? {
        val result: DataNetworkEntity = DataNetworkEntity()

        try {
            val map = readServiceUrlMap(json!!.asJsonObject)
            if (map != null) {
                result.task = map
            }

        } catch (ex: JsonSyntaxException) {
            print(ex)
        }

        return result
    }

    @Throws(JsonSyntaxException::class)
    fun readServiceUrlMap(jsonObject: JsonObject?): Map<String, TaskNetworkEntity>? {
        if (jsonObject == null) {
            return null
        }
        val gson = Gson()
        val taskMap: HashMap<String, TaskNetworkEntity> = HashMap()

        for ((key, value1) in jsonObject.entrySet()) {
            val value: TaskNetworkEntity = gson.fromJson(value1, TaskNetworkEntity::class.java)
            taskMap[key] = value
        }
        return taskMap
    }
}

//class DataParser: JsonDeserializer<TaskNetworkEntity> {
//
//    override fun deserialize(
//        json: JsonElement?,
//        typeOfT: Type?,
//        context: JsonDeserializationContext?
//    ): TaskNetworkEntity? {
//        var result: TaskNetworkEntity? = null
//
//        try {
//            result = readServiceUrlMap(json!!.asJsonObject)
//        } catch (ex: JsonSyntaxException) {
//           print(ex)
//        }
//
//        return result
//    }
//
//    @Throws(JsonSyntaxException::class)
//    fun readServiceUrlMap(jsonObject: JsonObject?): TaskNetworkEntity? {
//        if (jsonObject == null) {
//            return null
//        }
//        val gson = Gson()
//        val taskMap: HashMap<String, TaskNetworkEntity> = HashMap()
//
//        for ((key, value1) in jsonObject.entrySet()) {
//            val value: TaskNetworkEntity = gson.fromJson(value1, TaskNetworkEntity::class.java)
//            taskMap[key] = value
//            return value
//        }
//        return null
//    }
//}