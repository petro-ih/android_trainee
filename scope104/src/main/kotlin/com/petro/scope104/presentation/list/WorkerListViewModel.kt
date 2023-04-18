package com.petro.scope104.presentation.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.petro.scope104.data.UserDataRepository
import com.petro.scope104.domain.entity.WorkerEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class WorkerListViewModel @Inject constructor(private val userDataRepository: UserDataRepository) :
    ViewModel() {
    private val usersLiveData = MutableLiveData<List<WorkerEntity?>>(ArrayList())
    val isLoadingLiveData = MutableLiveData(false)
    private val compositeDisposable = CompositeDisposable()
    private var currentPageNumber = 0
    val users: LiveData<List<WorkerEntity?>>
        get() = usersLiveData

    private fun setLoading(isLoading: Boolean) {
        isLoadingLiveData.value = isLoading
    }

    fun loadMore(refresh: Boolean, gender: Gender, countries: Set<String>) {
        Log.d("userlist", "refreshData: ")
        setLoading(true)
        if (refresh) {
            currentPageNumber = 0
        }
        val disposable = userDataRepository
            .loadUsers(currentPageNumber++, PAGE_SIZE, gender, ArrayList(countries))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ workerUis: List<WorkerEntity?>? ->
                val newList: MutableList<WorkerEntity?> = if (refresh) {
                    ArrayList()
                } else {
                    ArrayList(Objects.requireNonNull(usersLiveData.value))
                }
                newList.addAll(workerUis!!)
                usersLiveData.postValue(newList)
                setLoading(false)
            }) { e: Throwable? -> Log.d("LOGGG", "Error", e) }
        compositeDisposable.add(disposable)
    }

    companion object {
        private const val PAGE_SIZE = 20
    }
}