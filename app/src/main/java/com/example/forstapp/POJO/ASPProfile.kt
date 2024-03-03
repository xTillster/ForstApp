package com.example.forstapp.POJO

data class ASPProfile(
    var customerNumber: String,
    var name: String,
    var surname: String,
    var zipCode: String,
    var city: String,
    var streetNumber: String,
    var phone: String
) {
    companion object {
        var activeProfile: ASPProfile? = null
    }
}