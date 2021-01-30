package com.ivlieva.sstesttask.util

import android.content.Context
import com.google.gson.Gson
import java.io.File
import java.io.IOException
import java.util.*

class JSONHelper(context: Context?) {

    private val gson = Gson()
    private val path = context?.filesDir.toString() + File.separator

    /**
     *
     */
    fun getJsonDataFromAsset(calendar: Calendar): String? {
        var jsonString = ""
        try {
            val file = File(setDirPath(calendar) + calendar.get(Calendar.DAY_OF_MONTH) + ".json")
            if (file.exists()) {
                jsonString = file.bufferedReader().use { it.readText() }
            }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
        return jsonString
    }

//    /**
//     *
//     */
//    fun getList(calendar: Calendar): MutableList<Task> {
//        val string = getJsonDataFromAsset(calendar)
//        val listTType = object : TypeToken<List<Task>>() {}.type
//        if (!string.isNullOrEmpty()) {
//            return gson.fromJson(
//                string,
//                listTType
//            )
//        }
//        return mutableListOf()
//    }
//
//    /**
//     *
//     */
//    @RequiresApi(Build.VERSION_CODES.O)
//    fun addObject(t: Task) {
//        val calendar: Calendar = Calendar.Builder().setInstant(t.dateStart).build()
//        val dirPath = setDirPath(calendar)
//        val dir = File(dirPath)
//        val file = File(dirPath + calendar.get(Calendar.DAY_OF_MONTH) + ".json")
//        if (!dir.exists()) {
//            dir.mkdirs()
//            file.createNewFile()
//        }
//        val existingJson: List<Task> = getList(calendar)
//        val newJsonMap = existingJson.plus(t)
//        FileWriter(file).use { writer -> writer.write(gson.toJson(newJsonMap)) }
//    }

    private fun setDirPath(calendar: Calendar): String {
        return path + calendar.get(Calendar.YEAR) + File.separator + calendar.get(Calendar.MONTH) + File.separator
    }
}