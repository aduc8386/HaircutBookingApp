package omt.aduc8386.haircutbookingapp.model

import java.io.Serializable
import java.util.*

class User: Serializable {

    var email: String = ""
    var password: String = ""
    var name: String? = null
    var address: String? = null
    var gender: String? = null
    var phoneNumber: String? = null
    var avatar: String? = null
    val id: String = UUID.randomUUID().toString()

    constructor()
    constructor(
        email: String,
        password: String,
        name: String?,
        address: String?,
        gender: String?,
        phoneNumber: String?,
        avatar: String?
    ) {
        this.email = email
        this.password = password
        this.name = name
        this.address = address
        this.gender = gender
        this.phoneNumber = phoneNumber
        this.avatar = avatar
    }


    override fun toString(): String {
        return "User(email='$email', password='$password', name=$name, address=$address, gender=$gender, phoneNumber=$phoneNumber, avatar=$avatar)"
    }


}