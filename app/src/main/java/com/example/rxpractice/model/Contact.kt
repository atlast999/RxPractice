package com.example.rxpractice.model

import com.google.gson.annotations.SerializedName


data class Contact(var name: String,
                   @SerializedName("image") var profileImage: String?,
                   var phone: String?,
                   var email: String?) {

    override fun equals(other: Any?): Boolean {
        return if (other != null && other is Contact) {
            other.email.equals(email, ignoreCase = true)
        } else false
    }

}