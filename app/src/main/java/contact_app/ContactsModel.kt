package contact_app

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class ContactsModel (
    @ColumnInfo
    val name : String  ,
    @PrimaryKey
    val number : String ,
) : Serializable
