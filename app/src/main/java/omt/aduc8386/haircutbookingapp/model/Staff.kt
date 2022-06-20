package omt.aduc8386.haircutbookingapp.model

import java.util.*

class Staff {

    val id: String = UUID.randomUUID().toString()
    var name: String? = null
    var address: String? = null
    var phoneNumber: String? = null
    var avatar: String? = null

    constructor()
    constructor(name: String?, address: String?, phoneNumber: String?) {
        this.name = name
        this.address = address
        this.phoneNumber = phoneNumber
    }

}