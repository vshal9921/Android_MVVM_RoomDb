package com.kinected.myapplication.common

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkRequest
import androidx.work.Worker
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
) : Worker(context, workerParameters){

        override fun doWork(): Result {

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

    fun startPeriodicWorker() : WorkRequest{

        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        return PeriodicWorkRequestBuilder<ApiWorker>(15, TimeUnit.MINUTES)
            .setConstraints(constraints = constraints)
            .build()
    }
}
