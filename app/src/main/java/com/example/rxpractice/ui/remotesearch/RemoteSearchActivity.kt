package com.example.rxpractice.ui.remotesearch

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import com.example.rxpractice.R
import com.example.rxpractice.databinding.ActivityRemotesearchBinding
import com.example.rxpractice.model.Contact
import com.example.rxpractice.network.ApiClient
import com.example.rxpractice.network.ApiServer
import com.example.rxpractice.ui.localsearch.LocalContactAdapter
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.Timed
import timber.log.Timber
import java.util.concurrent.TimeUnit

class RemoteSearchActivity: AppCompatActivity(), LocalContactAdapter.ContactsAdapterListener {

    private lateinit var binding: ActivityRemotesearchBinding
    private lateinit var apiService: ApiServer
    private val disposable = CompositeDisposable()
    private lateinit var adapter: LocalContactAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_remotesearch)

        adapter = LocalContactAdapter(this, listOf(), this)
        binding.recyclerView.adapter = adapter
        apiService = ApiClient.client.create(ApiServer::class.java)

        val observable = Observable.create<String> { emitter ->
            binding.inputSearch.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(p0: Editable?) {
                    emitter.onNext(p0.toString())
                }
                override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }
                override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                }

            })
        }

        disposable.add(
            observable.subscribeOn(Schedulers.io())
                .debounce(300, TimeUnit.MILLISECONDS)
                .switchMap {
                    Timber.d(Thread.currentThread().name)
                    apiService.getContacts(null, it).toObservable().subscribeOn(Schedulers.io()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { res ->
                    adapter.setData(res)
                }
        )

    }

    override fun onContactSelected(contact: Contact?) {

    }
}