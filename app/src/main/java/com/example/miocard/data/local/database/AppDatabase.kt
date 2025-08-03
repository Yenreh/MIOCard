package com.example.miocard.data.local.database

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import android.content.Context
import com.example.miocard.data.local.converters.DateConverters
import com.example.miocard.data.local.dao.CardDao
import com.example.miocard.data.local.entity.CardEntity

@Database(
    entities = [CardEntity::class],
    version = 2,
    exportSchema = false
)
@TypeConverters(DateConverters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao

    companion object {
        const val DATABASE_NAME = "miocard_database"
        
        val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Add position column with default value of 0
                database.execSQL("ALTER TABLE cards ADD COLUMN position INTEGER NOT NULL DEFAULT 0")
                
                // Update existing records to have position values based on their order
                database.execSQL("""
                    UPDATE cards 
                    SET position = (
                        SELECT COUNT(*) + 1 
                        FROM cards AS c2 
                        WHERE c2.name < cards.name
                    )
                """)
            }
        }
    }
}