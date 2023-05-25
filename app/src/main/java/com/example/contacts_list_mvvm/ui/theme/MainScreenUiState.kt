package com.example.contacts_list_mvvm.ui.theme

import androidx.annotation.DrawableRes
import com.example.contacts_list_mvvm.R

data class MainScreenUiState(
    val screenName: String = "Contacts",
    @DrawableRes val fabIcon: Int = R.drawable.baseline_person_add_alt_24
)