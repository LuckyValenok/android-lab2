package net.luckyvalenok.myapplication

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel : ViewModel() {
    val errorMessage = MutableLiveData<String>()
    val cards = MutableLiveData<List<CardInfo>>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val response = RetrofitService.getInstance().getCards()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    cards.postValue(response.body())
                } else {
                    errorMessage.postValue("Error : ${response.message()} ")
                }
            }
        }
    }
}