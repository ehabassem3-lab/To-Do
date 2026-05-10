package contact_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.to_do.database.entity.Task
import com.example.to_do.databinding.ContactitemBinding
import com.example.to_do.databinding.TaskItemBinding
import com.example.to_do.fragmets.task.TaskAdabter.TasksViewHolder
import java.util.Date

class ContactsAdapter(
    var contacts: List<ContactsModel> ,
    var DeleteContact :(position : Int) -> Unit
) : RecyclerView.Adapter<ContactsAdapter.ViewHolder>(){
    lateinit var binding : ContactitemBinding

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        binding = ContactitemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {
        val contact = contacts[position]
        holder.binding.ContactName.text = contact.name
        holder.binding.ContactNumber.text = contact.number

        holder.binding.dots.setOnClickListener {
               DeleteContact(position)
        }

    }

    override fun getItemCount(): Int {
 return        contacts.size
    }


    class ViewHolder(var binding: ContactitemBinding) : RecyclerView.ViewHolder(binding.root){

    }
}