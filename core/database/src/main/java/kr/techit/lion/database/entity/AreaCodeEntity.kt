package kr.techit.lion.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "area_code_table")
data class AreaCodeEntity (
    @PrimaryKey
    val code: String,
    val name: String
)

