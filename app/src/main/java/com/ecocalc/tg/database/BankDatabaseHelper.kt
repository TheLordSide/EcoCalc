package com.ecocalc.tg.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class BankDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "banks.db"
        private const val DATABASE_VERSION = 1

        // Table and column names
        const val TABLE_NAME = "banks"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_FEE_RATE = "fee_rate"

        // SQL for creating the table
        private const val CREATE_TABLE = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT NOT NULL,
                $COLUMN_FEE_RATE REAL NOT NULL
            )
        """

        // SQL for deleting the table
        private const val DROP_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
        seedData(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_TABLE)
        onCreate(db)
    }

    private fun seedData(db: SQLiteDatabase) {
        val banks = listOf(
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_FEE_RATE) VALUES ('Ecobank', 0.02)",
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_FEE_RATE) VALUES ('Orange Bank', 0.03)",
            "INSERT INTO $TABLE_NAME ($COLUMN_NAME, $COLUMN_FEE_RATE) VALUES ('MTN Bank', 0.01)"
        )
        for (query in banks) {
            db.execSQL(query)
        }
    }
}
