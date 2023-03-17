package com.example.scope106.task3;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.scope106.R;
import com.example.scope106.databinding.Layout3Binding;

import java.lang.ref.WeakReference;

//For this we will subclass Async task, define 3 generic types for the params,
// the progress and the result and override 4 methods:
// onPreExecute, where we do preparations on the UI thread, doInBackground where we do the heavy work on a background thread,
// onProgressUpdate where we publish the progress to the UI thread (for example to a ProgressBar),
// and onPostExecute where we get back the result from the background task.

public class Activity extends AppCompatActivity implements View.OnClickListener {
    Layout3Binding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = Layout3Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.button.setOnClickListener(this);
        binding.progressBar.setOnClickListener(this);
    }

    public void startAsyncTask(View view) {
        ExampleAsyncTask task = new ExampleAsyncTask(this);
        task.execute(10);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button) {
            startAsyncTask(v);
        }
    }

    private static class ExampleAsyncTask extends AsyncTask<Integer, Integer, String> {
        private WeakReference<Activity> activityWeakReference;

        ExampleAsyncTask(Activity activity) {
            activityWeakReference = new WeakReference<Activity>(activity);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Activity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.binding.progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected String doInBackground(Integer... integers) {
            for (int i = 0; i < integers[0]; i++) {
                publishProgress((i * 100) / integers[0]);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return "Finished!";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            Activity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            activity.binding.progressBar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Activity activity = activityWeakReference.get();
            if (activity == null || activity.isFinishing()) {
                return;
            }
            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show();
            activity.binding.progressBar.setProgress(0);
            activity.binding.progressBar.setVisibility(View.INVISIBLE);
        }
    }
}


