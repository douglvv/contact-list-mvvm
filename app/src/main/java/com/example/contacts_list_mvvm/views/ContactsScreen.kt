package com.example.contacts_list_mvvm.views

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.contacts_list_mvvm.R
import com.example.contacts_list_mvvm.models.Contact
import com.example.contacts_list_mvvm.viewModels.ContactsViewModel

@Composable
fun ContactsScreen(
    navController: NavController,
    contactsViewModel: ContactsViewModel
){

    val uiState by contactsViewModel.contactsScreenUiState.collectAsState()
    val showFavorites by contactsViewModel.showFavorites.collectAsState()

    ContactList(
        contacts =
            if (!showFavorites) {uiState.allContacts}
            else uiState.favoriteContacts,
        onEditContact = {contactsViewModel.editContact(it, navController)},
        onDeleteContact = {contactsViewModel.deleteContact(it)},
        contactsViewModel = contactsViewModel,
        showFavorites = showFavorites
    )

}


@Composable
fun ContactElement(
    contact: Contact,
    onEditContact: () -> Unit,
    onDeleteContact: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        elevation = 4.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = contact.name
                )
                if (contact.isFavorite) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_star_24),
                        contentDescription = "Favorite"
                    )
                }
            }
            Row {
                IconButton(onClick = { onEditContact() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_zoom_in_map_24),
                        contentDescription = "Show"
                    )
                }
                IconButton(onClick = { onDeleteContact() }) {
                    Icon(
                        painter = painterResource(id = R.drawable.baseline_delete_24),
                        contentDescription = "Delete"
                    )
                }
            }
        }
    }
}



@Composable
fun ContactList(
    contacts: List<Contact>,
    onEditContact: (Contact) -> Unit,
    onDeleteContact: (Contact) -> Unit,
    contactsViewModel: ContactsViewModel,
    showFavorites: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row {
                Button(
                    modifier = Modifier
                        .background(color = Color.Transparent),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                    onClick = {
                        contactsViewModel.showFavorites()
                    }
                ) {
                    if(!showFavorites)
                        Text(
                            text = "Show Favorites",
                            color = Color.White,
                            style = TextStyle(fontSize = 16.sp)
                    )
                    else Text(
                        text = "Show All",
                        color = Color.White,
                        style = TextStyle(fontSize = 16.sp)
                    )
                }
            }
            LazyColumn {
                items(contacts) { contact ->
                    ContactElement(
                        contact = contact,
                        onEditContact = { onEditContact(contact) },
                        onDeleteContact = { onDeleteContact(contact) }
                    )
                }
            }
        }
    }
}

