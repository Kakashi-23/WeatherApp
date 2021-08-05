package com.example.weatherapp.Utils


data class Event<out T>(val content: T?,  val status: Status, val message: String?) {

    companion object{
       fun <T> success(data: T): Event<T>? {
            return Event<T>(data, Status.SUCCESS,null)
        }
        fun <T> error(message:String?): Event<T>? {
            return Event<T>(null,Status.ERROR,message)
        }
        fun <T> loading(data: T): Event<T>? {
            return Event<T>(data, Status.LOADING,null)
        }
    }

    enum class Status {
        SUCCESS, ERROR, LOADING
    }
}