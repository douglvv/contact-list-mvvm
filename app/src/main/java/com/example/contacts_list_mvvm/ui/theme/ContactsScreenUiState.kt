package com.example.contacts_list_mvvm.ui.theme

import com.example.contacts_list_mvvm.models.Contact

data class ContactsScreenUiState(
    val allContacts: List<Contact> = listOf(
        Contact(name = "João", phoneNumber = "4212345678", isFavorite = true),
        Contact(name = "Maria", phoneNumber = "4212345678", isFavorite = false),
        Contact(name = "Joana", phoneNumber = "4212345678", isFavorite = true),
        Contact(name = "Mario", phoneNumber = "4212345678", isFavorite = false)
    )
) {
    val favoriteContacts: List<Contact>
        get() =
            allContacts.filter { contact ->
                contact.isFavorite
    }
}
