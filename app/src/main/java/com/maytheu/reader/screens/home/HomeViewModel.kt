package com.maytheu.reader.screens.home

import androidx.lifecycle.ViewModel
import com.maytheu.reader.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val fireRepo: FireRepository) : ViewModel() {

}