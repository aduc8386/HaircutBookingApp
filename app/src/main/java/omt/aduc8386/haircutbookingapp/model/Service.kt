package omt.aduc8386.haircutbookingapp.model

import java.util.*

class Service {

    val id: String = UUID.randomUUID().toString()
    var name: String? = null
    var price: Double? = null
    var picture: String? = null

    constructor()
    constructor(name: String?, price: Double?, picture: String?) {
        this.name = name
        this.price = price
        this.picture = picture
    }


}