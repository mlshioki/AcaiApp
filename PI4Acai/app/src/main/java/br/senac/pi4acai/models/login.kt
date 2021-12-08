package br.senac.pi4acai.models

data class login(
    var email : String,
    var password : String,
    var device_name: String = "android"
)
