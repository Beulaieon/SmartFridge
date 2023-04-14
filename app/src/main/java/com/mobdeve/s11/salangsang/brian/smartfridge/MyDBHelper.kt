package com.mobdeve.s11.salangsang.brian.smartfridge

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.String

class MyDBHelper(context: Context?) :
    SQLiteOpenHelper(context, DbReferences.DATABASE_NAME, null, DbReferences.DATABASE_VERSION) {

    // The singleton pattern design
    companion object {
        private var instance: MyDBHelper? = null

        @Synchronized
        fun getInstance(context: Context): MyDBHelper? {
            if (instance == null) {
                instance = MyDBHelper(context.applicationContext)
            }
            return instance
        }

        fun getInstance(context: homeFragment): MyDBHelper? {
            if (instance == null) {
                instance = MyDBHelper(context.activity)
            }
            return instance
        }
    }

    override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
        sqLiteDatabase.execSQL(DbReferences.CREATE_TABLE_STATEMENT)
    }

    // Called when a new version of the DB is present; hence, an "upgrade" to a newer version
    override fun onUpgrade(sqLiteDatabase: SQLiteDatabase, i: Int, i1: Int) {
        sqLiteDatabase.execSQL(DbReferences.DROP_TABLE_STATEMENT)
        onCreate(sqLiteDatabase)
    }

    fun getAllFoodsDefault(): ArrayList<Food> {

        val database: SQLiteDatabase = this.readableDatabase

        val c : Cursor = database.query(
                DbReferences.TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            DbReferences.COLUMN_NAME_FOOD_NAME + " ASC, " + DbReferences.COLUMN_NAME_FOOD_NAME + " ASC",
            null
        )

        val foods : ArrayList<Food> = ArrayList()

        while (c.moveToNext()) {
            foods.add(
                Food(
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_FOOD_NAME)),
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_FOOD_DESCRIPTION)),
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_FOOD_EXPIRY)),
                c.getString(c.getColumnIndexOrThrow(DbReferences.COLUMN_NAME_IMAGE_URI)),
                c.getLong(c.getColumnIndexOrThrow(DbReferences._ID))
            )
            )
        }

        c.close()
//        database.close()

        return foods
    }


    fun insertFood(f: Food): Long {
        val database = this.writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DbReferences.COLUMN_NAME_FOOD_NAME, f.foodname)
        values.put(DbReferences.COLUMN_NAME_FOOD_DESCRIPTION, f.description)
        values.put(DbReferences.COLUMN_NAME_FOOD_EXPIRY, f.expiry)
        values.put(DbReferences.COLUMN_NAME_IMAGE_URI, f.imageUri)

        // Insert the new row
        // Inserting returns the primary key value of the new row, but we can ignore that if we don’t need it
        val key = database.insert(DbReferences.TABLE_NAME, null, values)
        database.close()

        return key
    }

    fun deleteFood(f: Food) {
        val database = this.writableDatabase
        database.delete(
            DbReferences.TABLE_NAME,
            DbReferences._ID + " = ?", arrayOf(String.valueOf(f.getFoodId()))
        )
        database.close()
    }





    private object DbReferences {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "my_FoodDatabase.db"

        const val TABLE_NAME = "foods"
        const val _ID = "id"
        const val COLUMN_NAME_FOOD_NAME = "food_name"
        const val COLUMN_NAME_FOOD_DESCRIPTION = "food_description"
        const val COLUMN_NAME_FOOD_EXPIRY = "food_expiry"
        const val COLUMN_NAME_IMAGE_URI = "image_uri"

        const val CREATE_TABLE_STATEMENT =
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                    _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_NAME_FOOD_NAME + " TEXT, " +
                    COLUMN_NAME_FOOD_DESCRIPTION + " TEXT, " +
                    COLUMN_NAME_FOOD_EXPIRY + " TEXT, " +
                    COLUMN_NAME_IMAGE_URI + " TEXT)"

        const val DROP_TABLE_STATEMENT = "DROP TABLE IF EXISTS $TABLE_NAME"
    }
}