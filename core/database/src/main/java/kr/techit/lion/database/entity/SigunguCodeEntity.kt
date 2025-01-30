package kr.techit.lion.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sigungu_code_table")
data class SigunguCodeEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val sigunguName: String,
    val sigunguCode: String,
    val areaCode: String
)




