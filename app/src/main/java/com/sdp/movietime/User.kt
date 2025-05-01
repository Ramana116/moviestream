package com.sdp.movietime

class User {
    val email:String
    val username: String
    val password: String
    val reEnterPassword:String

    constructor(email: String,username:String,password:String,reEnterPassword:String){
        this.email=email
        this.username=username
        this.password=password
        this.reEnterPassword=reEnterPassword
    }

}