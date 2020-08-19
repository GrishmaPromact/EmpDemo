package com.grishma.empdemo.repository

import android.app.Application
import com.grishma.empdemo.db.EmployeeDb
import com.grishma.empdemo.model.Employee
import com.grishma.empdemo.networking.ApiService
import retrofit2.Call

/**
 * MainRepository
 */
class MainRepository(private val application: Application) {
    private var apiService : ApiService = ApiService.createService()
    private var db : EmployeeDb? = EmployeeDb.getDatabase(application)

    //get all list of employees from api
    fun getListOfEmployees(): Call<Employee> {
        return apiService.getEmployees()
    }

    //insert all employess into db
    fun insertEmployees(data : MutableList<Employee.Data>) {
       // db?.clearAllTables()
        db?.empDao()?.insert(data)
    }

    //delete emp item from db
    fun deleteEmp(employee: Employee.Data){
        db?.empDao()?.deleteEmp(employee)
    }

    //update emp item in db
    fun updateEmp(employee: Employee.Data){
        db?.empDao()?.updateEmp(employee)
    }

    //get all emp list from db
    fun getAllEmpsFromDb(): List<Employee.Data> {
        return db?.empDao()?.getAllEmployees()!!
    }

}