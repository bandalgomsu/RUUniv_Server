package com.ruuniv.app.students.model

class Student(
    var id: Long? = null,
    var apiKeyId: Long,
    var email: String,
    var universityName: String,
) {
}