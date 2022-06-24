package omt.aduc8386.haircutbookingapp.model

import java.util.*

class BookingDetail {

    lateinit var user: User
    lateinit var barbershop: Barbershop
    lateinit var service: Service
    lateinit var stylist: Staff
    lateinit var bookTime: String
    var isPaid: Boolean = false
    var id: String = UUID.randomUUID().toString()

    constructor()
    constructor(
        user: User,
        barbershop: Barbershop,
        service: Service,
        stylist: Staff,
        bookTime: String
    ) {
        this.user = user
        this.barbershop = barbershop
        this.service = service
        this.stylist = stylist
        this.bookTime = bookTime
    }

    override fun toString(): String {
        return "BookingDetail(user=$user, barbershop=$barbershop, service=$service, stylist=$stylist, bookTime='$bookTime', isPaid=$isPaid)"
    }


}