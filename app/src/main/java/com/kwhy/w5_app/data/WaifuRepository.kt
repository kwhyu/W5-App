package com.kwhy.w5_app.data

import com.kwhy.w5_app.model.Waifu
import com.kwhy.w5_app.model.WaifuData.dummyWaifu
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf

class WaifuRepository {
    private val listWaifu = mutableListOf<Waifu>()

    init {
        if (listWaifu.isEmpty()) {
            dummyWaifu.forEach {
                listWaifu.add(it)
            }
        }
    }

    fun getWaifuById(waifuId: Int): Waifu {
        return listWaifu.first {
            it.id == waifuId
        }
    }

    fun searchWaifu(query: String)= flow {
        val data = listWaifu.filter {
            it.name.contains(query, ignoreCase = true)
        }
        emit(data)
    }

    fun favoriteWaifu(): Flow<List<Waifu>> {
        return flowOf(listWaifu.filter { it.isFavorite })
    }

    fun updateFavorite(waifuId: Int, newState: Boolean): Flow<Boolean> {
        val index = listWaifu.indexOfFirst { it.id == waifuId }
        val result = if (index >= 0) {
            val waifu = listWaifu[index]
            listWaifu[index] = waifu.copy(isFavorite = newState)
            true
        } else {
            false
        }
        return flowOf(result)
    }

    companion object {
        @Volatile
        private var instance: WaifuRepository? = null

        fun getInstance(): WaifuRepository =
            instance ?: synchronized(this) {
                WaifuRepository().apply {
                    instance = this
                }
            }
    }

}