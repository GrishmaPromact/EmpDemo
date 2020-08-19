package com.grishma.empdemo.viewmodel

import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.grishma.empdemo.model.Employee
import com.grishma.empdemo.repository.MainRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors

/**
 * MainViewModel class
 */
class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    val mutableLiveDataListOfEmp = MutableLiveData<List<Employee.Data>>()
    private val executor = Executors.newSingleThreadExecutor()
    init {
        AsyncTask.execute {
            //check if data is present in db or not
            if(mainRepository.getAllEmpsFromDb().isEmpty())
                fetchListOfEmployees() //get from api
            else {
                //get data from db
                mainRepository.getAllEmpsFromDb()
                getAllEmpListFromDb()
            }
        }
    }

    /**
     * To fetch all employees from api
     */
    private fun fetchListOfEmployees() {
            mainRepository.getListOfEmployees()
                .enqueue(object : Callback<Employee> {
                    override fun onFailure(
                        call: Call<Employee>?,
                        t: Throwable
                    ) {
                        Log.e("TAG::", "Failed to load data! " + t.message)
                    }

                    override fun onResponse(
                        call: Call<Employee>?,
                        response: Response<Employee>
                    ) {
                        val posts = response.body()?.data?.toMutableList()
                        executor.execute {
                            posts?.let { mainRepository.insertEmployees(it) }
                            getAllEmpListFromDb()
                        }
                        Log.d("TAG::", "onResponse:  Successful loaded data!!!")
                    }
                })

    }

    override fun onCleared() {
        super.onCleared()
    }

    /**
     * get All employees data from room db
     */
    fun getAllEmpListFromDb(): Unit {
        mutableLiveDataListOfEmp.postValue(mainRepository.getAllEmpsFromDb())
    }

    /**
     * delete employees data from room db
     */
    fun deleteEmpFromDb(employee: Employee.Data): Unit {
        mutableLiveDataListOfEmp.apply {
            mainRepository.deleteEmp(employee)
        }
    }

    /**
     * update employees data from room db
     */
    fun updateEmpFromDb(employee: Employee.Data): Unit {
        mutableLiveDataListOfEmp.apply {
            mainRepository.updateEmp(employee)
        }
    }

}

