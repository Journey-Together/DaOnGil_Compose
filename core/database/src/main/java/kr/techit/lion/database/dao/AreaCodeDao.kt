package kr.techit.lion.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kr.techit.lion.database.entity.AreaCodeEntity

@Dao
interface AreaCodeDao {
    @Query("SELECT * FROM area_code_table")
    suspend fun getAreaCodes(): List<AreaCodeEntity>

    @Query("SELECT code FROM area_code_table WHERE name LIKE :areaName || '%'")
    fun getAreaCode(areaName: String): String?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAreaCode(areaCode: List<AreaCodeEntity>)
}

