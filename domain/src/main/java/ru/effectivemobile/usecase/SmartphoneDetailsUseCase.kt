package ru.effectivemobile.usecase

import kotlinx.coroutines.flow.flow
import ru.effectivemobile.repository.SmartphoneDetailsRepository
import ru.effectivemobile.utils.generateFlow
import javax.inject.Inject

class SmartphoneDetailsUseCase @Inject constructor(
    private val repository: SmartphoneDetailsRepository
) {
    fun invoke() = generateFlow { repository.getSmartphoneDetails() }
}