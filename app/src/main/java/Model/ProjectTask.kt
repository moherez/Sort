package Model

class ProjectTask {
    var id : Int? = null
    var task_name :String? = null
    var description : String? = null
    var importance : Int? = null
    var task_date : String? = null

    constructor(task_name: String?, description: String?, importance: Int?, task_date: String?) {
        this.task_name = task_name
        this.description = description
        this.importance = importance
        this.task_date = task_date
    }

    constructor(){

    }
}