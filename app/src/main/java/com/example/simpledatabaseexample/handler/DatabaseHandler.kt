package com.example.simpledatabaseexample.handler
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.example.simpledatabaseexample.constants.Constants.COL_AGE
import com.example.simpledatabaseexample.constants.Constants.COL_ID
import com.example.simpledatabaseexample.constants.Constants.COL_NAME
import com.example.simpledatabaseexample.constants.Constants.DATABASE_NAME
import com.example.simpledatabaseexample.constants.Constants.TABLE_NAME
import com.example.simpledatabaseexample.model.User

class DatabaseHandler(context: Context) : SQLiteOpenHelper(
    context,DATABASE_NAME,null, 1
) {
    override fun onCreate(db: SQLiteDatabase?) {
        createTable(db)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    private fun createTable(db: SQLiteDatabase?) {
        val createtable = "CREATE TABLE " + TABLE_NAME +" (" +
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_NAME + " VARCHAR(256), " +
                COL_AGE + " INTEGER);"

        db?.execSQL(createtable)
    }

    fun insertData(context : Context, user: User){
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COL_NAME, user.name)
        contentValues.put(COL_AGE, user.age)
        val result = db.insert(TABLE_NAME, null, contentValues)
        if (result == (-1).toLong()){
            Toast.makeText(context,
                "Failed",
                Toast.LENGTH_LONG).show()
        }
        else{
            Toast.makeText(context,
                "Success",
                Toast.LENGTH_LONG).show()
        }
        db.close()
    }

    fun readData(context: Context) : MutableList<User>{
        val datalist : MutableList<User> = ArrayList()

        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                val user = User()
                user.id = result.getString(result.getColumnIndex(COL_ID)).toInt()
                user.name = result.getString(result.getColumnIndex(COL_NAME))
                user.age = result.getString(result.getColumnIndex(COL_AGE)).toInt()
                datalist.add(user)
            }while (result.moveToNext())
        }
        result.close()
        db.close()
        return datalist
    }

    fun deleteData(){
        val database = this.writableDatabase
        database.delete(TABLE_NAME, "$COL_ID=?", arrayOf(1.toString()))
        database.close()
    }

    fun updateData(context: Context){
        val db = this.writableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val contentValues = ContentValues()
        val result = db.rawQuery(query, null)
        if (result.moveToFirst()){
            do {
                contentValues.put(COL_AGE, result.getInt(result.getColumnIndex(COL_AGE)) + 1)
                db.update(TABLE_NAME,
                    contentValues,
                    "$COL_ID=? AND $COL_NAME=?",
                    arrayOf(
                        result.getString(result.getColumnIndex(COL_ID)),
                        result.getString(result.getColumnIndex(COL_NAME))))
            }while (result.moveToNext())
        }
        result.close()
        db.close()
    }
}