package Controller

import Utils.Utils
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.os.Build
import androidx.annotation.RequiresApi

@RequiresApi(Build.VERSION_CODES.P)
class DatabaseHelper(context: Context?, name: String?, version: Int, openParams: SQLiteDatabase.OpenParams) : SQLiteOpenHelper(context, name, version, openParams) {






    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
                "CREATE TABLE " + Utils.TABLE_NAME + " (",



        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

}