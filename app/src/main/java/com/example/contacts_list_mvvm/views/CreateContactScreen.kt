package com.example.contacts_list_mvvm.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavController
import com.example.contacts_list_mvvm.viewModels.ContactsViewModel

@Composable
fun CreateContactScreen(
    navController: NavController,
    contactsViewModel: ContactsViewModel
){
    val uiState by contactsViewModel.createContactScreenUiState.collectAsState()
    InsertCreateContactForm(
        name = uiState.name,
        onNameChange = {contactsViewModel.onNameChange(it)},
        phoneNumber = uiState.phoneNumber,
        onPhoneNumberChange = { contactsViewModel.onPhoneNumberChange(it)},
        isFavorite = uiState.isFavorite,
        onIsFavoriteChange = { contactsViewModel.onIsFavoriteChange(it)}
    )
}

@Composable
fun InsertCreateContactForm(
    name: String,
    onNameChange: (String) -> Unit,
    phoneNumber: String,
    onPhoneNumberChange:(String) -> Unit,
    isFavorite: Boolean,
    onIsFavoriteChange: (Boolean) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(horizontalAlignment = Alignment.Start) {
            OutlinedTextField(
                label = { Text(text = "Name") },
                value = name,
                onValueChange = onNameChange
            )
            OutlinedTextField(
                label = { Text(text = "Phone Number") },
                value = phoneNumber,
                onValueChange = onPhoneNumberChange,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )
            Row() {
                Checkbox(checked = isFavorite, onCheckedChange = onIsFavoriteChange)
                Text(text = "Favorite contact")
            }
        }

    }
}
