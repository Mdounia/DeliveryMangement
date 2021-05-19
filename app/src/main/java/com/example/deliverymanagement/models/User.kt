package com.example.deliverymanagement.models

import java.io.Serializable

class User(
    var userId:String,
    var name: String,
    var email: String?,
    var password: String,
    var phoneNumber: Int,
    var address: String,
    var img: String

): Serializable {

}