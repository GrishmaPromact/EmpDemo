package com.grishma.empdemo.model


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class Employee(
    val `data`: List<Data>,
    val status: String
) {
    @Entity(tableName = "emp")
    data class Data(
        @PrimaryKey(autoGenerate = true)
        var empId : Int,

        var id: String,
        @SerializedName("employee_age")
        var employeeAge: String,
        @SerializedName("employee_name")
        var employeeName: String,
        @SerializedName("employee_salary")
        var employeeSalary: String,
        @SerializedName("profile_image")
        var profileImage: String
    ) : Serializable
}