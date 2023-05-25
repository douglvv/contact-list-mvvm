package com.example.contacts_list_mvvm.ui.theme

data class CreateContactScreenUiState(
    val name: String = "",
    val phoneNumber: String = "",
    var isFavorite: Boolean = false,
)
