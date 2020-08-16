package com.example.rxpractice.ui.localsearch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rxpractice.R
import com.example.rxpractice.model.Contact
import java.util.*
import kotlin.collections.ArrayList


class LocalContactAdapter(
    private val context: Context,
    private val contactList: List<Contact>,
    private val listener: ContactsAdapterListener
) :
    RecyclerView.Adapter<LocalContactAdapter.MyViewHolder>(), Filterable {
    private var contactListFiltered: List<Contact>

    init {
        contactListFiltered = contactList
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var name: TextView = view.findViewById(R.id.name)
        var phone: TextView = view.findViewById(R.id.phone)
        var thumbnail: ImageView = view.findViewById(R.id.thumbnail)

        init {
            view.setOnClickListener { listener.onContactSelected(contactListFiltered[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact_row, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        val (name, profileImage, phone) = contactListFiltered[position]
        holder.name.text = name
        holder.phone.text = phone
        Glide.with(context)
            .load(profileImage)
            .apply(RequestOptions.circleCropTransform())
            .into(holder.thumbnail)
    }

    override fun getItemCount(): Int {
        return contactListFiltered.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                contactListFiltered = if (charString.isEmpty()) contactList
                else {
                    val filteredList: MutableList<Contact> = ArrayList()
                    for (row in contactList) {
                        if (row.name.toLowerCase(Locale.ROOT)
                                .contains(charString.toLowerCase(Locale.ROOT)) || row.phone!!.contains(charSequence)) {
                            filteredList.add(row)
                        }
                    }
                    filteredList
                }
                val filterResults = FilterResults()
                filterResults.values = contactListFiltered
                return filterResults
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults) {
                contactListFiltered = filterResults.values as List<Contact>
                notifyDataSetChanged()
            }
        }
    }

    interface ContactsAdapterListener {
        fun onContactSelected(contact: Contact?)
    }

}