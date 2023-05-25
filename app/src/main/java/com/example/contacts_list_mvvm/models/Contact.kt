package com.example.contacts_list_mvvm.models

data class Contact(
    val name: String = "",
    val phoneNumber: String = "",
    var isFavorite: Boolean = false
)
