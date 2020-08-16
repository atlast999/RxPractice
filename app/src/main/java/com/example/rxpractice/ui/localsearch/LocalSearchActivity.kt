package com.example.rxpractice.ui.localsearch

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.rxpractice.R
import com.example.rxpractice.databinding.ActivityLocalsearchBinding
import com.example.rxpractice.model.Contact
import com.example.rxpractice.network.ApiClient
import com.example.rxpractice.network.ApiServer
import io.reactivex.disposables.CompositeDisposable

class LocalSearchActivity: AppCompatActivity(), LocalContactAdapter.ContactsAdapterListener {
    private lateinit var binding: ActivityLocalsearchBinding
    private val disposable = CompositeDisposable()
    private lateinit var apiService: ApiServer
    private lateinit var adapter: LocalContactAdapter
    private val contacts = listOf<Contact>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_localsearch)
        adapter = LocalContactAdapter(this, contacts, this)
        binding.recyclerView.adapter = adapter

        apiService = ApiClient.client.create(ApiServer::class.java)

        disposable.add(

        )
    }

    override fun onContactSelected(contact: Contact?) {
        TODO("Not yet implemented")
    }
}