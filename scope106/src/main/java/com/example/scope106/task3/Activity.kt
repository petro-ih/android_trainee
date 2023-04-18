package com.example.scope106.task3

import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.scope106.R
import com.example.scope106.databinding.Layout3Binding
import java.lang.ref.WeakReference

//For this we will subclass Async task, define 3 generic types for the params,
// the progress and the result and override 4 methods:
// onPreExecute, where we do preparations on the UI thread, doInBackground where we do the heavy work on a background thread,
// onProgressUpdate where we publish the progress to the UI thread (for example to a ProgressBar),
// and onPostExecute where we get back the result from the background task.
class Activity : AppCompatActivity(), View.OnClickListener {
    var binding: Layout3Binding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Layout3Binding.inflate(layoutInflater)
        setContentView(binding!!.root)
        binding!!.button.setOnClickListener(this)
        binding!!.progressBar.setOnClickListener(this)
    }

    fun startAsyncTask(view: View?) {
        val task = ExampleAsyncTask(this)
        task.execute(10)
    }

    override fun onClick(v: View) {
        if (v.id == R.id.button) {
            startAsyncTask(v)
        }
    }

    private class ExampleAsyncTask internal constructor(activity: Activity) :
        AsyncTask<Int?, Int?, String>() {
        private val activityWeakReference: WeakReference<Activity>

        init {
            activityWeakReference = WeakReference(activity)
        }

        override fun onPreExecute() {
            super.onPreExecute()
            val activity = activityWeakReference.get()
            if (activity == null || activity.isFinishing) {
                return
            }
            activity.binding!!.progressBar.visibility = View.VISIBLE
        }

        protected override fun doInBackground(vararg integers: Int): String {
            for (i in 0 until integers[0]) {
                publishProgress(i * 100 / integers[0])
                try {
                    Thread.sleep(1000)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
            return "Finished!"
        }

        protected override fun onProgressUpdate(vararg values: Int) {
            super.onProgressUpdate(*values)
            val activity = activityWeakReference.get()
            if (activity == null || activity.isFinishing) {
                return
            }
            activity.binding!!.progressBar.progress = values[0]
        }

        override fun onPostExecute(s: String) {
            super.onPostExecute(s)
            val activity = activityWeakReference.get()
            if (activity == null || activity.isFinishing) {
                return
            }
            Toast.makeText(activity, s, Toast.LENGTH_SHORT).show()
            activity.binding!!.progressBar.progress = 0
            activity.binding!!.progressBar.visibility = View.INVISIBLE
        }
    }
}