package com.petro.scope104.presentation.list

import android.annotation.SuppressLint
import android.app.ActivityOptions
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.petro.scope104.R
import com.petro.scope104.data.network.RetrofitInstance
import com.petro.scope104.databinding.ActivityScope104Binding
import com.petro.scope104.domain.entity.WorkerEntity
import com.petro.scope104.presentation.details.UserDetailsActivity
import com.petro.scope104.presentation.list.WorkerListFragment.ListFilter
import com.petro.scope104.presentation.list.WorkerListFragment.WorkerListInteractions
import com.petro.scope104.presentation.select.SelectBottomSheetFragment
import com.petro.scope104.presentation.select.SelectBottomSheetFragment.OnItemSelected
import com.petro.scope104.presentation.select.SelectItem
import dagger.hilt.android.AndroidEntryPoint
import java.io.Serializable
import java.util.*
import java.util.stream.Collectors

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), WorkerListInteractions, OnItemSelected, ListFilter {
    private val currentSelectedCountries = HashSet<String>()
    override var currentSelectedGender: Gender = Gender.UNKNOWN
        private set
    private var binding: ActivityScope104Binding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScope104Binding.inflate(layoutInflater)
        setContentView(binding!!.root)
        val viewPager = binding!!.pager
        viewPager.adapter = MyFragmentAdapter(supportFragmentManager)
    }

    override fun onItemClick(workerEntity: WorkerEntity?, activityOptions: ActivityOptions) {
        UserDetailsActivity.start(this, workerEntity, activityOptions)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    @SuppressLint("NonConstantResourceId")
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val fragment: Fragment
        fragment = when (item.itemId) {
            R.id.gender -> {
                val items = Arrays
                    .stream(Gender.values())
                    .filter { gen: Gender -> gen != Gender.UNKNOWN }
                    .map { gen: Gender ->
                        SelectItem(
                            gen.displayName,
                            currentSelectedGender == gen,
                            gen
                        )
                    }
                    .collect(Collectors.toList())
                SelectBottomSheetFragment.newInstance(
                    REQUEST_CODE_GENDER,
                    "SELECT SEX",
                    items
                )
            }
            R.id.city -> {
                val items = RetrofitInstance.supportLocations.stream()
                    .map { current: String ->
                        val locale = Locale("", current)
                        SelectItem(
                            locale.displayCountry,
                            currentSelectedCountries.contains(current),
                            current
                        )
                    }
                    .collect(Collectors.toList())
                SelectBottomSheetFragment.newInstance(
                    REQUEST_CODE_COUNTRY,
                    "Select Country",
                    items
                )
            }
            else -> return false
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment)
            .commit()
        return true
    }

    override fun onBackPressed() {
        supportFragmentManager
        super.onBackPressed()
    }

    override fun onItemSelected(requestCode: Int, items: List<SelectItem>) {
        when (requestCode) {
            REQUEST_CODE_GENDER -> {
                var checkedCount = 0
                for (item in items) {
                    if (item.value) {
                        checkedCount++
                        currentSelectedGender = item.`object` as Gender
                    }
                }
                if (checkedCount == 0 || checkedCount == 2) {
                    currentSelectedGender = Gender.UNKNOWN
                }
            }
            REQUEST_CODE_COUNTRY -> {
                val selected = items
                    .stream()
                    .filter { obj: SelectItem -> obj.value }
                    .map { obj: SelectItem -> obj.`object` }
                    .map { obj: Serializable -> obj.toString() }
                    .collect(Collectors.toList())
                currentSelectedCountries.clear()
                currentSelectedCountries.addAll(selected)
            }
            else -> throw IllegalStateException("Unexpected value: $requestCode")
        }
        for (i in 0 until binding!!.pager.adapter!!.count) {
            val `object` = binding!!.pager.adapter!!.instantiateItem(binding!!.pager, i)
            if (`object` is WorkerListFragment) {
                `object`.refresh()
            }
        }
    }

    override val countries: Set<String>
        get() = currentSelectedCountries

    companion object {
        private const val REQUEST_CODE_GENDER = 1
        private const val REQUEST_CODE_COUNTRY = 2
    }
}