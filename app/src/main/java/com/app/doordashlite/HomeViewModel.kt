package com.app.doordashlite

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import javax.inject.Inject

class HomeViewModel @Inject constructor(val app: Application) : AndroidViewModel(app) {

    init {

    }
}
