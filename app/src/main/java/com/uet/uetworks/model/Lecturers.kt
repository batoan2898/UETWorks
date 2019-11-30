package com.uet.uetworks.model

class Lecturers {
    var about: String? = null
    var avatar: String? = null
    var email: String? = null
    var emailVnu: String? = null
    var facultyName: String? = null
    var fullName: String? = null
    var phoneNumber: String? = null
    var subject: String? = null
    var id: Int = 0

    constructor() {}

    constructor(
        about: String,
        avatar: String,
        email: String,
        emailVnu: String,
        facultyName: String,
        fullName: String,
        phoneNumber: String,
        subject: String,
        id: Int
    ) {
        this.about = about
        this.avatar = avatar
        this.email = email
        this.emailVnu = emailVnu
        this.facultyName = facultyName
        this.fullName = fullName
        this.phoneNumber = phoneNumber
        this.subject = subject
        this.id = id
    }
}
