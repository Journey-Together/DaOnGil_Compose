package kr.techit.lion.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import kr.techit.lion.database.dao.AreaCodeDao
import kr.techit.lion.database.dao.RecentlySearchKeywordDAO
import kr.techit.lion.database.dao.SigunguCodeDao
import kr.techit.lion.database.entity.AreaCodeEntity
import kr.techit.lion.database.entity.RecentlySearchKeywordEntity
import kr.techit.lion.database.entity.SigunguCodeEntity

@TypeConverters(ListConverter::class)
@Database(
    entities = [AreaCodeEntity::class, SigunguCodeEntity::class, RecentlySearchKeywordEntity::class],
    version = 1
)

internal abstract class MainDatabase: RoomDatabase()  {
    abstract fun areaCodeDao(): AreaCodeDao
    abstract fun sigunguCodeDao(): SigunguCodeDao
    abstract fun recentlySearchKeywordDao(): RecentlySearchKeywordDAO
}