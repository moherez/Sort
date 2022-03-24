package Model

class Project {
    var id : Int? = null
    var project_name : String? = null
    var description : String? = null
    var tesks = ArrayList<ProjectTask>()

    constructor(project_name: String?, description: String?, tesks: ArrayList<ProjectTask>) {
        this.project_name = project_name
        this.description = description
        this.tesks = tesks
    }


    constructor(_id : Int , project_name: String?, description: String?, tesks: ArrayList<ProjectTask>) {
        this.id = _id
        this.project_name = project_name
        this.description = description
        this.tesks = tesks
    }

    constructor(){

    }


}