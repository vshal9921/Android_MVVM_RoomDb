package com.kinected.myapplication.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query


@Entity(tableName = "country_table")
data class CountryResponseItem(
	@PrimaryKey(autoGenerate = true)
	val id: Int,
	val name: String? = "",
	val flagPNG: String? = ""
)

@Dao
interface CountryDao{

	@Insert(onConflict = OnConflictStrategy.REPLACE)
	suspend fun insertCountry(countryResponseItem: CountryResponseItem)

	@Query("DELETE FROM country_table")
	suspend fun deleteAllList()

	@Query("SELECT * FROM country_table ORDER BY id ASC")
	suspend fun getAllCountries(): List<CountryResponseItem>
}


