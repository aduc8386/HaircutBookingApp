package omt.aduc8386.haircutbookingapp.model

import java.util.*
import kotlin.collections.ArrayList

class Barbershop{

    val id: String = UUID.randomUUID().toString()
    var name: String = ""
    var address: String = ""
    var avatar: String? = null
    var phoneNumber: String = ""

    constructor()
    constructor(name: String, address: String, avatar: String?, phoneNumber: String) {
        this.name = name
        this.address = address
        this.avatar = avatar
        this.phoneNumber = phoneNumber
    }

    override fun toString(): String {
        return "Barbershop(id='$id', name=$name, address=$address)"
    }


}