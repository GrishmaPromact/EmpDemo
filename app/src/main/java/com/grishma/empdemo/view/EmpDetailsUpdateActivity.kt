package com.grishma.empdemo.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.grishma.empdemo.R
import com.grishma.empdemo.model.Employee
import com.grishma.empdemo.utils.Constants
import kotlinx.android.synthetic.main.activity_emp_details_update.*

/**
 * Update EmpDetails Activity
 */
class EmpDetailsUpdateActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_emp_details_update)

        //get data from intent
        val empData : Employee.Data? = intent.extras?.getSerializable(Constants.DATA) as Employee.Data?
        val position : Int? = intent.extras?.getInt(Constants.POSITION)

        //set data into edit text
        empName.setText(empData?.employeeName)
        empSalary.setText(empData?.employeeSalary)
        empAge.setText(empData?.employeeAge)

        //update button click listener
        btnUpdate.setOnClickListener {

            //get data from edit text
            empData?.employeeName = empName.text.toString()
            empData?.employeeSalary = empSalary.text.toString()
            empData?.employeeAge = empAge.text.toString()

            //Set Result intent
            val returnIntent = Intent()
            returnIntent.putExtra(Constants.RESULT, empData)
            returnIntent.putExtra(Constants.POSITION,position)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }
    }
}