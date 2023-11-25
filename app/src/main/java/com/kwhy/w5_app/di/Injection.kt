package com.kwhy.w5_app.di

import com.kwhy.w5_app.data.WaifuRepository

object Injection {
    fun provideRepository(): WaifuRepository {
        return WaifuRepository.getInstance()
    }
}