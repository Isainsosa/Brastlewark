package com.isain.brastlewark.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.isain.brastlewark.database.entities.Inhabitant

@Dao
interface InhabitantDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(inhabitants: List<Inhabitant>)

    @Query("SELECT * FROM Inhabitant WHERE id = :inhabitantId")
    fun getInhabitant(inhabitantId: Long): Inhabitant?
}