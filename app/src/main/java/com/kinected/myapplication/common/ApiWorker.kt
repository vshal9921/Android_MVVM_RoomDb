package com.kinected.myapplication.common

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import androidx.work.WorkerParameters
import com.kinected.myapplication.data.CountryRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit

@HiltWorker
class ApiWorker @AssistedInject constructor(
    private val countryRepo: CountryRepo,
    @Assisted context: Context,
    @Assisted workerParameters: WorkerParameters
) : CoroutineWorker(context, workerParameters){

    override suspend fun doWork(): Result {
        return try{
            countryRepo.fetchCountryList()
            Result.success()
        }
        catch (e: Exception){
            e.printStackTrace()
            return Result.failure()
        }
    }
}

object WorkerUtil{

    fun startPeriodicWorker() : PeriodicWorkRequest{

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        return PeriodicWorkRequestBuilder<ApiWorker>(15, TimeUnit.MINUTES)
            .setInitialDelay(15, TimeUnit.MINUTES)
            .setConstraints(constraints = constraints)
            .build()
    }
}
