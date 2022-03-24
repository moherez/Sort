package Model

class UserNotificationDay {
    var id : Int? = null
    var dayDate : String? = null
    var notifications = ArrayList<UserNotification>()

    constructor(dayDate: String?, notifications: ArrayList<UserNotification>) {
        this.dayDate = dayDate
        this.notifications = notifications
    }

    constructor(){

    }
}