package com.uet.uetworks.model

class StudentInfo {
    var address: String? = null
    var avatar: String? = null
    var birthday: String? = null
    var desire: String? = null
    var email: String? = null
    var fullName: String? = null
    var id: Int = 0
    var infoBySchool: InfoBySchool? = null
    var phoneNumber: String? = null
    var skype: String? = null
    var lecturers: Lecturers? = null

    constructor() {}
    constructor(
        address: String,
        avatar: String,
        birthday: String,
        desire: String,
        email: String,
        fullName: String,
        id: Int,
        infoBySchool: InfoBySchool,
        phoneNumber: String,
        skype: String,
        lecturers: Lecturers
    ) {
        this.address = address
        this.avatar = avatar
        this.birthday = birthday
        this.desire = desire
        this.email = email
        this.fullName = fullName
        this.id = id
        this.infoBySchool = infoBySchool
        this.phoneNumber = phoneNumber
        this.skype = skype
        this.lecturers = lecturers
    }
}
