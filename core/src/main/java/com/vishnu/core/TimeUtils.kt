package com.vishnu.core


import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


object  TimeUtils {

    private fun convertToMillis(duration: String):Long{
        val result = duration.replace(
            "[0-9]".toRegex(), ""
        );

        return if(result == "hrs"){
            TimeUnit.HOURS.convert(
                duration.replace("[a-z]".toRegex(), "").trim().toLong(),
                TimeUnit.MILLISECONDS
            )
        } else{
            TimeUnit.MINUTES.convert(
                duration.replace("[a-z]".toRegex(), "").trim().toLong(),
                TimeUnit.MILLISECONDS
            )

        }
    }


   fun convertToDate(tripStartTime: String):Date{
       val tempTime =  tripStartTime.split(":")
       val calendar = Calendar.getInstance()
       calendar.set(Calendar.HOUR_OF_DAY,tempTime[0].toInt())
       calendar.set(Calendar.MINUTE,tempTime[1].toInt())

       return calendar.time
   }

    fun formatDate(date:Date):String{
        val format = SimpleDateFormat("MMM dd HH:mm")
       return  format.format(date)

    }


    fun addTime(duration:String,tripStartTime: String):Date{
        val calendar = Calendar.getInstance()
        calendar.time =  convertToDate(tripStartTime)
        calendar.add(Calendar.MILLISECOND, convertToMillis(duration).toInt())

        return calendar.time
    }

    fun getDepartsIn(tripStartTime:String):Int{
        val diffTime:Long
        val calendar = Calendar.getInstance()
        val tripStart = convertToDate(tripStartTime).time
        diffTime = tripStart- calendar.timeInMillis

        return (diffTime/ (1000 * 60)).toInt()

    }

    fun isTimeAfter(time:String):Boolean{
        val calendar = Calendar.getInstance()
        return convertToDate(time).after(calendar.time)
    }

    // Source time  Destination time  departsin
}