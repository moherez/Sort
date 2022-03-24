package Model

class Workplace {
    var id : String? =null
    var name : String? = null
    var company : String? = null
    var description : String? = null
    var projects =  ArrayList<Project>()
    var members = ArrayList<String>()


    constructor(name: String?, company: String?, description: String?, projects: ArrayList<Project>, members: ArrayList<String>) {
        this.name = name
        this.company = company
        this.description = description
        this.projects = projects
        this.members = members
    }

    constructor(){

    }

}