package com.example.rxpractice.ui.localsearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.databinding.adapters.TextViewBindingAdapter
import com.example.rxpractice.R
import com.example.rxpractice.databinding.ActivityLocalsearchBinding
import com.example.rxpractice.model.Contact
import com.example.rxpractice.network.ApiClient
import com.example.rxpractice.network.ApiServer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

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
        binding.progressBar.visibility = View.GONE

        disposable.add(
            apiService.getContacts(null, null)
                .subscribeOn(Schedulers.io())
                .map {
                    Thread.sleep(1000)
                    Timber.d(Thread.currentThread().name)
                    it
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { res: List<Contact>? ->
                    if (res != null) {
                        adapter.setData(res)
                        binding.progressBar.visibility = View.GONE
//                        Timber.d(Thread.currentThread().name)
                    }
                }
        )

        binding.inputSearch.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {
                adapter.filter.filter(p0)
            }
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

        })
    }

    override fun onContactSelected(contact: Contact?) {

    }
}