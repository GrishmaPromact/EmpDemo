package com.grishma.empdemo.db

import androidx.room.*
import com.grishma.empdemo.model.Employee


/**
 * EmpDao class for Articles
 */
@Dao
interface EmpDao {

  //insert all data of employee in db
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(posts: MutableList<Employee.Data>?)

  //getAllEmp Data from db
  @Query("SELECT * FROM emp")
  fun getAllEmployees(): List<Employee.Data>

  //delete emp item
  @Delete
  fun deleteEmp(employee: Employee.Data?)

  //update emp item
  @Update
  fun updateEmp(employee: Employee.Data?)

}
