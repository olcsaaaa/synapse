package com.olidev.synapse_mobile

import android.app.Application
import com.olidev.synapse_mobile.data.local.AppDatabase
import com.olidev.synapse_mobile.data.local.SynapseApi
import com.olidev.synapse_mobile.data.repository.DeckRepository
import com.olidev.synapse_mobile.data.repository.FlashcardRepository
import com.olidev.synapse_mobile.data.repository.UserRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Synapse : Application()
