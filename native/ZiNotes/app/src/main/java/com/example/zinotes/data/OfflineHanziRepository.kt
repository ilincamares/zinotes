package com.example.zinotes.data

import android.util.Log
import com.example.zinotes.data.network.HanziApiService
import com.example.zinotes.room.Hanzi
import com.example.zinotes.room.HanziDao
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener

class OfflineHanziRepository(
    private val hanziDao: HanziDao,
    private val apiService: HanziApiService,
    private val okHttpClient: OkHttpClient
) : HanziRepository {

    private val syncMutex = Mutex()

    init {
        initializeWebSocket()
    }

    private fun refreshData() {
        Log.d("REPO", "Refreshing data...")
        CoroutineScope(Dispatchers.IO).launch{
            syncMutex.withLock {
                try {
                    Log.d("REPO", "Syncing data with server...")

                    syncPending()
                    val serverData = apiService.getHanzi()
                    hanziDao.insertAll(serverData)

                    Log.d("REPO", "Sync complete!")
                } catch (e: Exception) {
                    Log.e("REPO", "Sync failed", e)
                }
            }
        }
    }

    private suspend fun syncPending() {
        val unsynced = hanziDao.getUnsynced()

        if (unsynced.isNotEmpty()) {
            Log.d("REPO", "Syncing ${unsynced.size} hanzi with server...")
        }

        unsynced.forEach { localHanzi ->
            try{
                when {
                    localHanzi.isDeleted -> {
                        if (!localHanzi.isNew) {
                            apiService.deleteHanzi(localHanzi.id)
                        }
                        hanziDao.hardDelete(localHanzi.id)
                    }

                    localHanzi.isNew -> {
                        val hanziToSend = localHanzi.copy(id = 0)
                        val newServerHanzi = apiService.addHanzi(hanziToSend)
                        hanziDao.hardDelete(localHanzi.id)
                        hanziDao.insert(newServerHanzi)
                    }

                    else -> {
                        apiService.updateHanzi(localHanzi.id, localHanzi)
                        hanziDao.markAsSynced(localHanzi.id)
                    }
                }
            } catch (e: Exception) {
                Log.e("REPO", "Failed to sync item ${localHanzi.id}", e)
                throw e
            }

        }
    }

    private fun initializeWebSocket() {
        val request = Request.Builder().url("ws://localhost:8080/zinotes-sync").build()

        val listener = object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("REPO", "WebSocket Connected!")
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("REPO", "Received message: $text")
                if(text == "update"){
                    refreshData()
                }
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("REPO", "WebSocket Error (Offline?)", t)
            }
        }
        okHttpClient.newWebSocket(request, listener)
    }

    private fun triggerSync() {
        CoroutineScope(Dispatchers.IO).launch {
            syncMutex.withLock {
                try {
                    syncPending()
                } catch (e: Exception) {
                    Log.e("REPO", "Background sync failed", e)
                }
            }
        }
    }

    override fun getHanziStream(): Flow<List<Hanzi>> {
        refreshData()
        return hanziDao.getAll()
    }

    override suspend fun getHanzi(id: Long): Hanzi? {
        return hanziDao.getById(id)
    }

    override suspend fun addHanzi(hanzi: Hanzi) {
        val unsyncedHanzi = hanzi.copy(isSynced = false, isNew = true)
        hanziDao.insert(unsyncedHanzi)
        triggerSync()
    }

    override suspend fun updateHanzi(hanzi: Hanzi) {
        val existingHanzi = hanziDao.getById(hanzi.id)
        val wasNew = existingHanzi?.isNew ?: false
        val unsyncedHanzi = hanzi.copy(isSynced = false, isNew = wasNew)
        hanziDao.update(unsyncedHanzi)
        triggerSync()
    }

    override suspend fun deleteHanziById(id: Long) {
        hanziDao.softDelete(id)
        triggerSync()
    }
}