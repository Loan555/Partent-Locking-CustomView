package com.loan555.customview_canvas_animation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AppViewModel : ViewModel() {
    private val _pass = MutableLiveData<String>().apply {
        value = ""
    }
    private val _page = MutableLiveData<Int>().apply {
        value = 0
    }
    val page: LiveData<Int> = _page

    fun setPass(newPass: String) {
        _pass.value = newPass
    }

    fun checkPass(commitPass: String): Boolean {
        if (commitPass == _pass.value)
            return true
        return false
    }

    fun currentPage(number: Int) {
        _page.value = number
    }
}