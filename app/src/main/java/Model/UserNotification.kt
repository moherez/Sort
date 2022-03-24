package Model

class UserNotification {
    var id : Int? = null
    var notification_image_path : String? = null
    var notification_text : String? = null

    constructor(notification_image_path: String?, notification_text: String?) {
        this.notification_image_path = notification_image_path
        this.notification_text = notification_text
    }

    constructor(){
    }
}