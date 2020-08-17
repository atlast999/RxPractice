package com.example.rxpractice.ui.main

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.rxpractice.ui.localsearch.LocalSearchActivity
import com.example.rxpractice.R
import com.example.rxpractice.ui.remotesearch.RemoteSearchActivity
import com.example.rxpractice.databinding.ActivityMainBinding
import io.reactivex.disposables.CompositeDisposable


class MainActivity : AppCompatActivity() {
    private var disposable = CompositeDisposable()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
            R.layout.activity_main
        )
        setSupportActionBar(binding.toolbar)
        whiteNotificationBar(binding.toolbar)

        binding.btnLocalSearch.setOnClickListener { startActivity(Intent(this, LocalSearchActivity::class.java)) }
        binding.btnRemoteSearch.setOnClickListener { startActivity(Intent(this, RemoteSearchActivity::class.java)) }
    }

    private fun whiteNotificationBar(view: View) {
        var flags: Int = view.systemUiVisibility
        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        view.systemUiVisibility = flags
        window.statusBarColor = Color.WHITE
    }




//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
//        binding.tvTapResult.text = "0"
//        binding.tvTapResultMax.text = "0"
//
//        Observable.create<Int>{emitter ->
//            while (true){
//                Thread.sleep(10)
//                Timber.d(Thread.currentThread().name)
//                emitter.onNext(1)
//            }
////            binding.btnTap.setOnClickListener { emitter.onNext(1) }
//         }.debounce(1, TimeUnit.SECONDS)
//            .subscribeOn(Schedulers.io())
////            .buffer(3, TimeUnit.SECONDS)
//            .observeOn(AndroidSchedulers.mainThread())
//
//            .subscribe(object : Observer<Int> {
//                override fun onComplete() {
//                }
//
//                override fun onSubscribe(d: Disposable) {
//                }
//
//                override fun onNext(t: Int) {
//                    current += t
//                    binding.tvTapResult.text = current.toString()
//                    Timber.d(current.toString())
////                    var maxTaps = binding.tvTapResultMax.text.toString().toInt()
////                    maxTaps = if (t.size > maxTaps) t.size else maxTaps
////
////                    binding.tvTapResult.text = t.size.toString()
////                    binding.tvTapResultMax.text = maxTaps.toString()
//                }
//
//                override fun onError(e: Throwable) {
//                }
//
//            })
//
//        val observableAnimals = Observable.fromArray(
//            "Buffalo", "Cow",
//            "Pig", "Cock", "Dog", "Ghost", "Me"
//        )
//
//        val normalObserver = object : DisposableObserver<List<String>>() {
//            override fun onComplete() {
//                Timber.d("normal observer complete!")
//            }
//
//            override fun onNext(t: List<String>) {
//                Timber.d("normal item: %s", t)
//            }
//
//            override fun onError(e: Throwable) {
//                Timber.d(e)
//            }
//
//        }
//
//        disposable.add(
//            observableAnimals
//                .observeOn(AndroidSchedulers.mainThread())
//                .buffer(2)
//                .subscribeWith(normalObserver)
//        )
//    }
//
//    private fun getFakeObservable(dumb: String) = Observable.create<String> { emitter ->
//        Timber.d(Thread.currentThread().name)
//        val sleepTime = Random().nextInt(1000) + 500
//        Thread.sleep(sleepTime.toLong())
//        emitter.onNext("$dumb modified: $sleepTime")
//        emitter.onComplete()
//    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}
