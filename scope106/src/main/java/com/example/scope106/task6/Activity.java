package com.example.scope106.task6;

import static android.util.Log.*;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scope106.R;
import com.example.scope106.databinding.Layout6Binding;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.Function3;
import io.reactivex.rxjava3.internal.util.AppendOnlyLinkedArrayList;

public class Activity extends AppCompatActivity implements View.OnClickListener {
    Layout6Binding binding;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Random r = new Random();
        compositeDisposable = new CompositeDisposable();
        binding = Layout6Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.btnEx1.setOnClickListener(this);
        binding.btnEx2.setOnClickListener(this);
        binding.btnEx3.setOnClickListener(this);
        binding.btnEx4.setOnClickListener(this);
        binding.btnEx6.setOnClickListener(this);
        binding.btnEx7.setOnClickListener(this);
        Observable<Integer> a = Observable.create(emitter -> {
            binding.a.setOnClickListener(v -> {
                int x = Math.abs(r.nextInt() % 100);
                emitter.onNext(x);
                binding.a.setText(String.valueOf(x));
            });
        });
        Observable<Integer> b = Observable.create(emitter -> {
            binding.b.setOnClickListener(v -> {
                int x = Math.abs(r.nextInt() % 100);
                emitter.onNext(x);
                binding.b.setText(String.valueOf(x));
            });
        });
        Observable<Integer> c = Observable.create(emitter -> {
            binding.c.setOnClickListener(v -> {
                int x = Math.abs(r.nextInt() % 100);
                emitter.onNext(x);
                binding.c.setText(String.valueOf(x));
            });
        });

//        Disposable disposable = Observable.merge(a, b, c).subscribe(
//                value -> d("LOGGG", value.toString()),
//                error -> e("LOGGG", "error", error),
//                () -> v("LOGGG", "Complete")
//        );
        Disposable disposable = Observable.combineLatest(a, b, c, (x, y, z) -> x.toString() + ", " + y.toString() + ", " + z.toString()).subscribe(
                value -> d("LOGGG", value.toString()),
                error -> e("LOGGG", "error", error),
                () -> v("LOGGG", "Complete")
        );
        compositeDisposable.add(disposable);
    }

    private void startStream() {
        Disposable disposable = Observable
                .fromIterable(Arrays.asList("1", "2", "3", "4", "5", "6"))
                .subscribe(
                        s -> d("LOGGG", s),
                        t -> e("LOGGG", "error", t)
                );
        compositeDisposable.add(disposable);
        // todo - use composite disposables
    }

    private void startStream2() {
        Disposable disposable = Observable
                .just("Hello", "world", "How", "are", "you?")
                .subscribe(
                        value -> d("LOGGG", value),
                        error -> e("LOGGG", "error", error),
                        () -> v("LOGGG", "Complete")
                );
        compositeDisposable.add(disposable);
    }

    private void startStream3() {
        Disposable disposable = Observable
                .fromArray("Apple", "Orange", "banana")
                .subscribe(
                        value -> d("LOGGG", value)
                );
        compositeDisposable.add(disposable);
    }

    private void startStream4(){
        Disposable disposable = Observable
                .fromIterable(Arrays.asList("Titan", "Fastrack", "Sonata"))
                .subscribe(
                        value -> d("LOGGG", value),
                        error -> e("LOGGG", "error", error),
                        () -> v("LOGGG", "Done")
                );
        compositeDisposable.add(disposable);
    }

    private void startStream6(){
        Disposable disposable = Observable
                .just("A", "B", "C", "D", "E", "F", "G", "H")
                .buffer(2)
                .subscribe(
                        value -> d("LOGGG", String.valueOf(value)),
                        error -> e("LOGGG", "error", error),
                        () -> v("LOGGG", "Done")
                );
        compositeDisposable.add(disposable);
    }

    private void startStream7(){
        Disposable disposable = Observable
                .range(1, 10)
                .filter(a -> a % 2 == 0)
                .map(a -> {
                    switch (a){
                        case 2:
                            return "two";
                        case 4:
                            return "four";
                        case 6:
                            return "six";
                        case 8:
                            return "eight";
                        case 10:
                            return "ten";
                    }
                    return "unknown";
                })
                .sorted()
                .subscribe(
                        value -> d("LOGGG", String.valueOf(value)),
                        error -> e("LOGGG", "error", error),
                        () -> v("LOGGG", "Sorted")
                );
        compositeDisposable.add(disposable);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.btnEx1) {
            startStream();
        } else if (id == R.id.btnEx2) {
            startStream2();
        } else if (id == R.id.btnEx3) {
            startStream3();
        } else if (id == R.id.btnEx4) {
            startStream4();
        }else if (id == R.id.btnEx6) {
            startStream6();
        }else if (id == R.id.btnEx7) {
            startStream7();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }
}
