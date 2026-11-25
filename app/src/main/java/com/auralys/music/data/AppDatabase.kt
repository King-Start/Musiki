package com.auralys.music.data

import android.content.Context
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "liked_songs")
data class LikedSongEntity(
    @PrimaryKey val songId: String,
    val addedAt: Long
)

@Entity(tableName = "history")
data class HistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val songId: String,
    val playedAt: Long
)

@Dao
interface LikedDao {
    @Query("SELECT * FROM liked_songs ORDER BY addedAt DESC")
    fun observeAll(): Flow<List<LikedSongEntity>>

    @Query("SELECT COUNT(*) FROM liked_songs")
    fun observeCount(): Flow<Int>

    @Insert
    suspend fun insert(entity: LikedSongEntity)

    @Query("DELETE FROM liked_songs WHERE songId = :songId")
    suspend fun delete(songId: String)
}

@Dao
interface HistoryDao {
    @Query("SELECT * FROM history ORDER BY playedAt DESC")
    fun observeAll(): Flow<List<HistoryEntity>>

    @Query("SELECT COUNT(*) FROM history")
    fun observeCount(): Flow<Int>

    @Insert
    suspend fun insert(entity: HistoryEntity)

    @Query("DELETE FROM history")
    suspend fun clear()
}

@Database(
    entities = [LikedSongEntity::class, HistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun likedDao(): LikedDao
    abstract fun historyDao(): HistoryDao

    companion object {
        @Volatile
        private var instance: AppDatabase? = null

        fun get(context: Context): AppDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "auralys.db"
                ).build().also { instance = it }
            }
    }
}
