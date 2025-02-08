package com.kinected.myapplication.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CountryResponseItem::class], version = 1, exportSchema = false)
abstract class CountryDb: RoomDatabase() {

    abstract fun countryDao(): CountryDao

    companion object{
        @Volatile
        private var INSTANCE: CountryDb? = null

        fun getInstance(context: Context) : CountryDb {

            synchronized(this){
                var instance = INSTANCE

                if(instance == null){
                    instance = Room.databaseBuilder(
                        context,
                        CountryDb::class.java,
                        "country_database"
                    ).build()

                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}