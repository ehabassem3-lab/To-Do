package contact_app

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.to_do.database.Data_Base
import com.example.to_do.database.entity.Task
import com.example.to_do.databinding.BottomSheetContactsBinding
import com.example.to_do.databinding.CreateTaskItemBinding
import com.example.to_do.day
import com.example.to_do.format
import com.example.to_do.month
import com.example.to_do.year
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import contact_app.database.DataBaseContacts
import java.util.Calendar


    class BottomSheetContacts(var AddContact : () -> Unit) : BottomSheetDialogFragment() {
        lateinit var binding: BottomSheetContactsBinding
        override fun onCreate(savedInstanceState: Bundle?) {

            super.onCreate(savedInstanceState)
        }

        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
        ): View? {

            binding = BottomSheetContactsBinding.inflate(layoutInflater,container,false)
            return binding.root
        }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)
            initListeners()
        }


        fun initListeners(){
            binding.CreateContact.setOnClickListener {
                if (!validate())  return@setOnClickListener
                val contact = ContactsModel(
                    number =  binding.CotnactNumber.editText!!.text.toString(),
                    name =  binding.ContactName.editText!!.text.toString()

                )
                DataBaseContacts.getDataBaseContacts().ContactsDao().addContact(contact)
                AddContact()
                dismiss()




            }



        }
        fun validate() : Boolean{
            var isValid = true
            if (

                binding.CotnactNumber.editText!!.text.isEmpty()
                ||
                binding.CotnactNumber.editText!!.text.length < 10

            ){
                binding.CotnactNumber.error = "Task Name Is Required"
                isValid = false
            } else{
                binding.CotnactNumber.error =null


            }
            if (binding.ContactName.editText!!.text.isEmpty()){
                binding.ContactName.error = "Task Details Is Required"
                isValid = false
            } else{
                binding.ContactName.error = null
            }

            return isValid
        }




}