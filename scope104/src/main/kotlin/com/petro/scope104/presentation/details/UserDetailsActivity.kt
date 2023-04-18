package com.petro.scope104.presentation.details

import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.petro.scope104.R
import com.petro.scope104.databinding.ActivityUserDetailsBinding
import com.petro.scope104.domain.entity.WorkerEntity
import com.petro.scope104.util.IntentHelper
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class UserDetailsActivity : AppCompatActivity() {
    private var binding: ActivityUserDetailsBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val formater = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        binding = ActivityUserDetailsBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val workerEntity = intent.getSerializableExtra(KEY_WORKER) as WorkerEntity
        binding!!.name.text = workerEntity.name
        binding!!.phoneNumber.text = workerEntity.phone
        binding!!.username1.text = workerEntity.username
        binding!!.email1.text = workerEntity.email
        val dateText = String.format(
            Locale.getDefault(),
            "%s, %d years",
            formater.format(workerEntity.dob),
            workerEntity.age
        )
        binding!!.dob1.text = dateText
        binding!!.nationality1.text = workerEntity.natCountry
        binding!!.location1.text = workerEntity.city
        val dateText1 = formater.format(workerEntity.registered)
        binding!!.registered.text = dateText1
        binding!!.btnMail.ivIcon.setImageResource(R.drawable.ic_mail)
        binding!!.btnMail.tvText.setText(R.string.email)
        binding!!.btnMap.ivIcon.setImageResource(R.drawable.ic_map)
        binding!!.btnMap.tvText.setText(R.string.map)
        val intentHelper = IntentHelper(this)
        binding!!.btnPhone.root.setOnClickListener { v: View? ->
            startActivity(
                intentHelper.getPhoneIntent(
                    workerEntity.phone
                )
            )
        }
        binding!!.btnMail.root.setOnClickListener { v: View? ->
            startActivity(
                intentHelper.getEmail(
                    workerEntity.email,
                    null,
                    null,
                    null
                )
            )
        }
        binding!!.btnMap.root.setOnClickListener { v: View? ->
            startActivity(
                intentHelper.getMaps(
                    workerEntity.city
                )
            )
        }
        binding!!.profileImage.transitionName =
            getString(R.string.avatarTransition, workerEntity.username)
        binding!!.name.transitionName =
            getString(R.string.nameTransition, workerEntity.username)
        postponeEnterTransition()
        Glide.with(this).load(workerEntity.avatarUrlXXL).circleCrop()
            .addListener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    startPostponedEnterTransition()
                    return false
                }
            }).into(binding!!.profileImage)
    }

    override fun onDestroy() {
        binding = null
        super.onDestroy()
    }

    companion object {
        private const val KEY_WORKER = "KEY_WORKER"
        fun start(context: Context, workerEntity: WorkerEntity?, activityOptions: ActivityOptions) {
            val starter = Intent(context, UserDetailsActivity::class.java)
            starter.putExtra(KEY_WORKER, workerEntity)
            context.startActivity(starter, activityOptions.toBundle())
        }
    }
}