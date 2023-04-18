package com.example.scope106.task6

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.scope106.R
import com.example.scope106.databinding.Layout6Binding
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.disposables.CompositeDisposable
import java.util.*

class Activity : AppCompatActivity(), View.OnClickListener {
    var binding: Layout6Binding? = null
    var compositeDisposable: CompositeDisposable? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val r = Random()
        compositeDisposable = CompositeDisposable()
        binding = Layout6Binding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.btnEx1.setOnClickListener(this)
        binding!!.btnEx2.setOnClickListener(this)
        binding!!.btnEx3.setOnClickListener(this)
        binding!!.btnEx4.setOnClickListener(this)
        binding!!.btnEx6.setOnClickListener(this)
        binding!!.btnEx7.setOnClickListener(this)
        val a = Observable.create { emitter: ObservableEmitter<Int> ->
            binding!!.a.setOnClickListener { v: View? ->
                val x = Math.abs(r.nextInt() % 100)
                emitter.onNext(x)
                binding!!.a.text = x.toString()
            }
        }
        val b = Observable.create { emitter: ObservableEmitter<Int> ->
            binding!!.b.setOnClickListener { v: View? ->
                val x = Math.abs(r.nextInt() % 100)
                emitter.onNext(x)
                binding!!.b.text = x.toString()
            }
        }
        val c = Observable.create { emitter: ObservableEmitter<Int> ->
            binding!!.c.setOnClickListener { v: View? ->
                val x = Math.abs(r.nextInt() % 100)
                emitter.onNext(x)
                binding!!.c.text = x.toString()
            }
        }

//        Disposable disposable = Observable.merge(a, b, c).subscribe(
//                value -> d("LOGGG", value.toString()),
//                error -> e("LOGGG", "error", error),
//                () -> v("LOGGG", "Complete")
//        );
        val disposable =
            Observable.combineLatest(a, b, c) { x: Int, y: Int, z: Int -> "$x, $y, $z" }
                .subscribe(
                    { value: String? -> Log.d("LOGGG", value!!) },
                    { error: Throwable? -> Log.e("LOGGG", "error", error) }
                ) { Log.v("LOGGG", "Complete") }
        compositeDisposable!!.add(disposable)
    }

    private fun startStream() {
        val disposable = Observable
            .fromIterable(Arrays.asList("1", "2", "3", "4", "5", "6"))
            .subscribe(
                { s: String? -> Log.d("LOGGG", s!!) }
            ) { t: Throwable? -> Log.e("LOGGG", "error", t) }
        compositeDisposable!!.add(disposable)
        // todo - use composite disposables
    }

    private fun startStream2() {
        val disposable = Observable
            .just("Hello", "world", "How", "are", "you?")
            .subscribe(
                { value: String? -> Log.d("LOGGG", value!!) },
                { error: Throwable? -> Log.e("LOGGG", "error", error) }
            ) { Log.v("LOGGG", "Complete") }
        compositeDisposable!!.add(disposable)
    }

    private fun startStream3() {
        val disposable = Observable
            .fromArray("Apple", "Orange", "banana")
            .subscribe { value: String? -> Log.d("LOGGG", value!!) }
        compositeDisposable!!.add(disposable)
    }

    private fun startStream4() {
        val disposable = Observable
            .fromIterable(Arrays.asList("Titan", "Fastrack", "Sonata"))
            .subscribe(
                { value: String? -> Log.d("LOGGG", value!!) },
                { error: Throwable? -> Log.e("LOGGG", "error", error) }
            ) { Log.v("LOGGG", "Done") }
        compositeDisposable!!.add(disposable)
    }

    private fun startStream6() {
        val disposable = Observable
            .just("A", "B", "C", "D", "E", "F", "G", "H")
            .buffer(2)
            .subscribe(
                { value: List<String> -> Log.d("LOGGG", value.toString()) },
                { error: Throwable? -> Log.e("LOGGG", "error", error) }
            ) { Log.v("LOGGG", "Done") }
        compositeDisposable!!.add(disposable)
    }

    private fun startStream7() {
        val disposable = Observable
            .range(1, 10)
            .filter { a: Int -> a % 2 == 0 }
            .map { a: Int? ->
                when (a) {
                    2 -> return@map "two"
                    4 -> return@map "four"
                    6 -> return@map "six"
                    8 -> return@map "eight"
                    10 -> return@map "ten"
                }
                "unknown"
            }
            .sorted()
            .subscribe(
                { value: String -> Log.d("LOGGG", value) },
                { error: Throwable? -> Log.e("LOGGG", "error", error) }
            ) { Log.v("LOGGG", "Sorted") }
        compositeDisposable!!.add(disposable)
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == R.id.btnEx1) {
            startStream()
        } else if (id == R.id.btnEx2) {
            startStream2()
        } else if (id == R.id.btnEx3) {
            startStream3()
        } else if (id == R.id.btnEx4) {
            startStream4()
        } else if (id == R.id.btnEx6) {
            startStream6()
        } else if (id == R.id.btnEx7) {
            startStream7()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable!!.dispose()
    }
}