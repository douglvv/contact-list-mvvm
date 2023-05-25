package com.example.contacts_list_mvvm.viewModels

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.contacts_list_mvvm.R
import com.example.contacts_list_mvvm.models.Contact
import com.example.contacts_list_mvvm.ui.theme.ContactsScreenUiState
import com.example.contacts_list_mvvm.ui.theme.CreateContactScreenUiState
import com.example.contacts_list_mvvm.ui.theme.MainScreenUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ContactsViewModel: ViewModel() {
    var editContact:Boolean = false
    var contactToEdit:Contact = Contact("")
    var contactToDelete:Contact = Contact("")

    // ==================================== MAIN SCREEN ================================================
    private var _mainScreenUiState: MutableStateFlow<MainScreenUiState> = MutableStateFlow(
        MainScreenUiState()
    )
    val mainScreenUiState: StateFlow<MainScreenUiState> = _mainScreenUiState.asStateFlow()

    // ==================================== CONTACTS SCREEN =============================================
    private var _contactsScreenUiState: MutableStateFlow<ContactsScreenUiState> = MutableStateFlow(
        ContactsScreenUiState()
    )
    val contactsScreenUiState: StateFlow<ContactsScreenUiState> = _contactsScreenUiState.asStateFlow()

    // ================================== CREATE CONTACT SCREEN =============================
    private var _createContactScreenUiState: MutableStateFlow<CreateContactScreenUiState> = MutableStateFlow(CreateContactScreenUiState())
    val createContactScreenUiState: StateFlow<CreateContactScreenUiState> = _createContactScreenUiState.asStateFlow()

    // ================================== SHOW FAVORITES ====================================
    private var  _showFavorites: MutableStateFlow <Boolean> = MutableStateFlow(false)
    val showFavorites: StateFlow<Boolean> = _showFavorites.asStateFlow()





    fun onNameChange(newContactName: String){
        _createContactScreenUiState.update { currentState ->
            currentState.copy(name = newContactName)
        }
    }

    fun onPhoneNumberChange(newPhoneNumber: String){
        _createContactScreenUiState.update { currentState ->
            currentState.copy(phoneNumber = newPhoneNumber)
        }
    }

    fun onIsFavoriteChange(newIsFavorite: Boolean){
        _createContactScreenUiState.update { currentState ->
            currentState.copy(isFavorite = newIsFavorite)
        }
    }

    fun navigate(navController: NavController) {
        if (_mainScreenUiState.value.screenName == "Contacts") {
            if (editContact) {
                _mainScreenUiState.update { currentState ->
                    currentState.copy(
                        screenName = "Contact",  // titulo na pagina de alteração
                        fabIcon = R.drawable.baseline_check_24,
                    )
                }
            } else {
                _mainScreenUiState.update { currentState ->
                    currentState.copy(
                        screenName = "New Contact",
                        fabIcon = R.drawable.baseline_check_24,
                    )
                }
            }
            navController.navigate("createContact")
        } else {
            _mainScreenUiState.update { currentState ->
                currentState.copy(
                    screenName = "Contacts",
                    fabIcon = R.drawable.baseline_person_add_alt_24,
                )
            }
            if (editContact) {
                val allContactsTemp = _contactsScreenUiState.value.allContacts.toMutableList()
                var pos = -1
                allContactsTemp.forEachIndexed { index, contact ->
                    if (contactToEdit == contact) { // Encontra a posição do contato
                        pos = index
                    }
                }
                allContactsTemp.removeAt(pos) // Remove o contato da array
                allContactsTemp.add( // Adiciona o contato alterado novamente
                    pos, contactToEdit.copy(
                        name = _createContactScreenUiState.value.name,
                        phoneNumber = _createContactScreenUiState.value.phoneNumber,
                        isFavorite = _createContactScreenUiState.value.isFavorite
                    )
                )
                _contactsScreenUiState.update { currentState ->
                    currentState.copy(allContacts = allContactsTemp.toList())
                }
            } else {
                _contactsScreenUiState.update { currentstate ->
                    currentstate.copy(
                        allContacts = currentstate.allContacts + Contact(
                            name = _createContactScreenUiState.value.name,
                            phoneNumber = _createContactScreenUiState.value.phoneNumber,
                            isFavorite = _createContactScreenUiState.value.isFavorite
                        )
                    )
                }
            }
            _createContactScreenUiState.update { // Altera a view para o estado padrão
                CreateContactScreenUiState()
            }
            editContact = false
            contactToEdit = Contact("")
            navController.navigate("contacts") {
                popUpTo("contacts") {
                    inclusive = true
                }
            }
        }
    }

    fun editContact(contact: Contact, navController: NavController){
        editContact = true
        contactToEdit = contact
        _createContactScreenUiState.update { currentState ->
            currentState.copy(
                name = contact.name,
                phoneNumber = contact.phoneNumber,
                isFavorite = contact.isFavorite
            )
        }
        navigate(navController)
    }

    fun deleteContact(contact: Contact){
        contactToDelete = contact

        val allContactsTemp = _contactsScreenUiState.value.allContacts.toMutableList()
        var pos = -1

        allContactsTemp.forEachIndexed { index, contact -> // Encontra o índice do contato
            if (contact == contactToDelete) {
                pos = index
            }
        }
        allContactsTemp.removeAt(pos)
        _contactsScreenUiState.update{  currentState ->
            currentState.copy(allContacts = allContactsTemp.toList())
        }

        contactToDelete = Contact("")
    }

    fun showFavorites (){
        _showFavorites.value = !_showFavorites.value
    }

}