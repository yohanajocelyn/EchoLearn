package com.yohana.echolearn.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel

class BottomNavBarViewModel(): ViewModel() {
    var currentViewString: String by mutableStateOf("")
        private set

    fun setCurrentView(view: String){
        currentViewString = view
    }

    fun getColor(text: String): Color {
        if(currentViewString == text){
            return Color(0xFF3DB2FF)
        }else{
            return Color.Black
        }
    }
}