package ru.effectivemobile.ui.smartphonedetails

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import ru.effectivemobile.Result
import ru.effectivemobile.usecase.SmartphoneDetailsUseCase
import javax.inject.Inject

class SmartphoneDetailsViewModel @Inject constructor(
    private val smartphoneDetailsUseCase: SmartphoneDetailsUseCase,
): ViewModel() {

    private val smartphoneDetailsFragmentStateLiveData =
        MutableLiveData<Result<SmartphoneDetailsFragmentsState>>(null)

    internal val notifyColorOptionChanged: (Int)->Unit = { newIndex->
        val currentState = smartphoneDetailsFragmentStateLiveData.value
        if (currentState is Result.Success && currentState.data.colorOptionIndex!=newIndex) {
            smartphoneDetailsFragmentStateLiveData
                .value = Result.Success(currentState.data.copy(colorOptionIndex = newIndex))
        }
    }

    internal val notifyFavoriteStatusChanged: ()->Unit = {
        val currentState = smartphoneDetailsFragmentStateLiveData.value

        if (currentState is Result.Success) {
            val copy = currentState.data.copy(isFavorite = !currentState.data.isFavorite)
                smartphoneDetailsFragmentStateLiveData
                .value = Result.Success(copy)
        }
    }

    internal val notifyStorageOptionChanged: (Int)->Unit = { newIndex->
        val currentState = smartphoneDetailsFragmentStateLiveData.value
        if (currentState is Result.Success && currentState.data.storageOptionIndex!=newIndex) {
            smartphoneDetailsFragmentStateLiveData
                .value = Result.Success(currentState.data.copy(storageOptionIndex = newIndex))
        }
    }

    init {

        viewModelScope.launch {
            smartphoneDetailsUseCase
                .invoke()
                .flowOn(Dispatchers.IO)
                .collect {
                    smartphoneDetailsFragmentStateLiveData
                        .value = when(it) {
                            is Result.Loading -> Result.Loading
                            is Result.Error -> Result.Error(it.error)
                            is Result.Success -> {
                                Result.Success(SmartphoneDetailsFragmentsState(it.data))
                            }
                        }
                }
        }
    }

    internal val currentStateResult get() = smartphoneDetailsFragmentStateLiveData.value!!

    internal fun subscribeFragment(
        lo: LifecycleOwner,
        lambda: (SmartphoneDetailsFragmentsState)->Unit
    ) {
        smartphoneDetailsFragmentStateLiveData.observe(lo) {
            if (it is Result.Success) {
                lambda(it.data)
            }
        }
    }

}

