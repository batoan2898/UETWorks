package com.uet.uetworks.model

class InfoBySchool {
    var diploma: String? = null
    var emailvnu: String? = null
    var gpa: Double = 0.toDouble()
    var grade: String? = null
    var graduationYear: String? = null
    var id: Int = 0
    var major: String? = null
    var studentClass: String? = null
    var studentCode: Int = 0
    var studentName: String? = null

    constructor() {}
    constructor(
        diploma: String,
        emailvnu: String,
        gpa: Double,
        grade: String,
        graduationYear: String,
        id: Int,
        major: String,
        studentClass: String,
        studentCode: Int,
        studentName: String
    ) {
        this.diploma = diploma
        this.emailvnu = emailvnu
        this.gpa = gpa
        this.grade = grade
        this.graduationYear = graduationYear
        this.id = id
        this.major = major
        this.studentClass = studentClass
        this.studentCode = studentCode
        this.studentName = studentName
    }
}
