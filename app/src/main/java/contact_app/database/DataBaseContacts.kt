package contact_app.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.to_do.database.Data_Base
import com.example.to_do.database.daos.Task_Dao
import com.example.to_do.database.entity.Task
import contact_app.ContactsModel

@Database(entities = [ContactsModel::class], version = 1)
open abstract class DataBaseContacts (): RoomDatabase()  {
    abstract fun ContactsDao() : ContactsDao

    companion object{

        private   var dataBase : DataBaseContacts? = null
        fun createDataBase(context: Context){

            dataBase =
                Room
                    .databaseBuilder(context, DataBaseContacts::class.java,"ContactsDB")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration(true)
                    .build()


        }
        fun getDataBaseContacts() : DataBaseContacts {
            return dataBase!!
        }
    }
}