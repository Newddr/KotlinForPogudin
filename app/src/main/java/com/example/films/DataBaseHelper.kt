package com.example.films
import android.annotation.SuppressLint
import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.ContentProviderCompat.requireContext
import java.io.FileOutputStream

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Space.db"
        private const val DATABASE_VERSION = 2
    }

    init {
        val dbFile = context.getDatabasePath(DATABASE_NAME)
        if (!dbFile.exists()) {
            val assets = context.assets
            val buffer = ByteArray(1024)
            var length: Int
            val inputStream = assets.open(DATABASE_NAME)
            val outputStream = FileOutputStream(dbFile)
            while (inputStream.read(buffer).also { length = it } > 0) {
                outputStream.write(buffer, 0, length)
            }
            outputStream.flush()
            outputStream.close()
            inputStream.close()
        }
        else{

        }
    }
    private val dbPath: String = context.getDatabasePath(DATABASE_NAME).path


    override fun getReadableDatabase(): SQLiteDatabase {
        return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READONLY)
    }

    override fun getWritableDatabase(): SQLiteDatabase {
        return SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE)
    }

    override fun onCreate(db: SQLiteDatabase) {
        //db.execSQL("CREATE TABLE IF NOT EXISTS series (id INTEGER PRIMARY KEY, name TEXT, date TEXT, description TEXT, poster TEXT, status INTEGER)")


    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS series")
        onCreate(db);

    }

    fun updateFilmStatus(id: Int, status: Int) {
        val values = ContentValues()
        values.put("status", status)

        val db = writableDatabase
        db.beginTransaction()

            db.update("series", values, "$id=?", arrayOf(id.toString()))
            db.execSQL("COMMIT")

    }
    @SuppressLint("Range")
    fun getAllFilms(filter:String): List<FilmsInfo> {
        val films = mutableListOf<FilmsInfo>()

        val selectQuery = "SELECT * FROM series ${filter}"
        val db = readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))
                val name = cursor.getString(cursor.getColumnIndex("name"))
                val date = cursor.getString(cursor.getColumnIndex("date"))
                val description = cursor.getString(cursor.getColumnIndex("description"))
                val image = cursor.getString(cursor.getColumnIndex("poster"))
                val status = cursor.getString(cursor.getColumnIndex("status")).toInt()

                val film = FilmsInfo(name, date, description, image,status,id)
                films.add(film)
            } while (cursor.moveToNext())
        }

        cursor.close()
        db.close()

        return films
    }

}