package contact_app

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do.databinding.ContactsMainBinding
import com.google.android.material.bottomsheet.BottomSheetBehavior
import contact_app.database.DataBaseContacts
import kotlin.collections.emptyList

class MainContactsApp  : AppCompatActivity(){

        lateinit var binding: ContactsMainBinding
        lateinit var adapter: ContactsAdapter
        lateinit var recyclerView: RecyclerView
       var ContactsList = emptyList<ContactsModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContactsMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        adapter = ContactsAdapter(ContactsList){ it ->
            AlertDialog.Builder(this)
                .setTitle("Delete Contact")
                .setMessage("Are you sure you want to delete this contact?")

                .setPositiveButton("Yes") { dialog, which ->

                    DataBaseContacts.getDataBaseContacts().ContactsDao().deleteContact(ContactsList[it])
                    RefreshContacts()

                }

                .setNegativeButton("No") { dialog, which ->

                    dialog.dismiss()

                }

                .show()

        }
        RefreshContacts()
        recyclerView = binding.RecyclerViewConctactsApp
        recyclerView.adapter = adapter
        println(ContactsList)
        initListners()

    }

    fun initListners(){
        binding.AddContact.setOnClickListener {
            val createContact = BottomSheetContacts{
                println("Data Added")
                RefreshContacts()
            }
            createContact.show(supportFragmentManager,"")

        }
    }
    fun RefreshContacts()
    {
        ContactsList = DataBaseContacts.getDataBaseContacts().ContactsDao().showAll()
        adapter.contacts = ContactsList
        EmptyContacts()
        adapter.notifyDataSetChanged()
    }
    fun EmptyContacts(){
        if (ContactsList.isNotEmpty()) {
            binding.EmptyContacts.visibility = View.GONE
        }
        else{
            binding.EmptyContacts.visibility = View.VISIBLE

        }
    }

}