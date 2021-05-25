package com.example.simpledatabaseexample.model

class User {
    var id = 0
    var name: String? = null
    var age = 0

    constructor() {}
    constructor(name: String?, age: Int) {
        this.name = name
        this.age = age
    }
}