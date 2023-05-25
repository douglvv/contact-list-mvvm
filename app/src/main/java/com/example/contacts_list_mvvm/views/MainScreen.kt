package com.example.contacts_list_mvvm.views

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.contacts_list_mvvm.viewModels.ContactsViewModel

@SuppressLint("UnusedMaterialScaffoldPaddingParameter") // Sò remove o erro no Scaffold ao adicionar esse supress
@Composable
fun MainScreen(
    contactsViewModel: ContactsViewModel = viewModel()
){
    val navController = rememberNavController()

    val uiState by contactsViewModel.mainScreenUiState.collectAsState()
    
    Scaffold(
        topBar = {
            TopAppBar {
                Text(text = uiState.screenName)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { contactsViewModel.navigate(navController)}) {
                androidx.compose.material.Icon(painter = painterResource(id = uiState.fabIcon), contentDescription = null)
            }
        }
    ) {
        NavHost(navController = navController, startDestination = "contacts"){
            composable("contacts"){
                ContactsScreen(navController = navController, contactsViewModel = contactsViewModel)
            }
            composable("createContact"){
                CreateContactScreen(navController = navController, contactsViewModel = contactsViewModel)
            }
        }
    }
}

