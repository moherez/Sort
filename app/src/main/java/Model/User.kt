package Model

 class User {
    var full_name:String? = null
    var username:String? = null
    var email:String? = null
    var photo_path:String? = null
    var id : Int? = null
    var company :String? = null
    var description : String? = null
    var workplaces = ArrayList<String>()
    var notifications = ArrayList<UserNotificationDay>()

    constructor(full_name: String?, username: String?, email: String?, photo_path: String?, company: String?, description: String?, workplaces: ArrayList<String>, notifications: ArrayList<UserNotificationDay>) {
        this.full_name = full_name
        this.username = username
        this.email = email
        this.photo_path = photo_path
        this.company = company
        this.description = description
        this.workplaces = workplaces
        this.notifications = notifications
    }

    constructor(){

    }
}