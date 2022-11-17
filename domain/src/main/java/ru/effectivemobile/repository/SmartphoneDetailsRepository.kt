package ru.effectivemobile.repository

import ru.effectivemobile.model.SmartphoneGoodDetails

interface SmartphoneDetailsRepository {
    suspend fun getSmartphoneDetails(): SmartphoneGoodDetails
}