package com.example.deliverymanagement.models

import android.text.format.Time
import java.io.Serializable
import java.util.*

class Delivery(
    var delId :String,
    var description : String,
    var height: Float,
    var width: Float,
    var lenght: Float,
    var weight: Float,
    var deliveryAddress : String,
    var sourceAddress : String,
    var deliveryDate :String,
    var deliveryTime : String,
    var userId:String

): Serializable {
}