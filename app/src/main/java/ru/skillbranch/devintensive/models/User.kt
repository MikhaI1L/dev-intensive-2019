package ru.skillbranch.devintensive.models

import ru.skillbranch.devintensive.utils.Utils
import java.util.*

data class User  (
    val id : String,
    var firstName : String?,
    var lastName : String?,
    var avatar : String?,
    var raiting : Int = 0,
    var respect : Int = 0,
    val lastVisit : Date? = null,
    val isOnline : Boolean = false
    ){

    constructor(id :String, firstName: String?, lastName: String?) : this (
        id = id,
        firstName = firstName,
        lastName = lastName,
        avatar = null
        )


    constructor(id: String) : this (id, "John", "Pupkin")

    init {
        println("Yo!!! \n" +
                "${if(lastName==="Pupkin") "His name is $firstName $lastName" else "And his name is $firstName $lastName!!!"}\n")
    }

    fun printMe() =
        println("""
            id : $id
            firstName : $firstName
            lastName : $lastName
            avatar : $avatar
            raiting : $raiting
            respect : $respect
            lastVisit : $lastVisit
            isOnline : $isOnline
        """.trimIndent()
        )

    companion object Factory {
        private var lastId : Int = -1
        fun makeUser(fullName : String?) : User {
            lastId++

            val (firstName, lastName) = Utils.parseFullName(fullName)

            return User(id = "$lastId", firstName = firstName, lastName = lastName)
        }
    }

}