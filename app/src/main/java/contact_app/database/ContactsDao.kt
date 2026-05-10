package contact_app.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.to_do.database.entity.Task
import contact_app.ContactsModel
@Dao
interface ContactsDao {

    @Insert
    fun addContact(contact: ContactsModel)

    @Delete
    fun deleteContact(contact: ContactsModel)

    @Update
    fun updateContact(contact: ContactsModel)
    @Query("SELECT * FROM ContactsModel ")
    fun showAll() : List<ContactsModel>


}