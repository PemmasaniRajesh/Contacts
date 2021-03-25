package rajeshfsd.contacts

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class ContactsListAdapter(private val context: Context, val listener: OnContactItemClickListener, val isFavContacts: Boolean) :
        RecyclerView.Adapter<ContactsListAdapter.ContactViewHolder>() {

    private var contactsList: List<Contact> = arrayListOf<Contact>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        return ContactViewHolder(LayoutInflater.from(context).inflate(R.layout.adapter_contact_list_item, parent, false))
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        holder.tvName.setText(contactsList.get(position).nm)
        Glide.with(context).load(contactsList.get(position).fp).circleCrop().into(holder.ivPhoto)

        if (!isFavContacts) {
            if (contactsList.get(position).fav == true) {
                holder.ivFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_star))
            } else {
                holder.ivFav.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_baseline_star_outline))
            }
        }

    }

    override fun getItemCount(): Int {
        return contactsList.size
    }

    fun updateContact(position: Int) {
        val contact: Contact = contactsList.get(position)
        contact.fav = contact.fav != true
        listener.onUpdateFav(contact)
    }

    fun setContacts(contacts: List<Contact>) {
        contactsList = contacts;
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val tvName: TextView = view.findViewById(R.id.tv_item_name)
        val ivPhoto: ImageView = view.findViewById(R.id.iv_item_photo)
        var ivFav: ImageView = view.findViewById(R.id.iv_item_fav)

        init {
            ivFav.setOnClickListener(this)
            if(isFavContacts){
                ivFav.visibility = View.GONE
            }
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = adapterPosition;
            if (position == RecyclerView.NO_POSITION) {
                return
            }
            if (v!!.id == R.id.iv_item_fav) {
                updateContact(position)
            }else{
                listener.onItemClicked(contactsList.get(position))
            }
        }
    }
}