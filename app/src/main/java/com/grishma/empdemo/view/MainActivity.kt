package com.grishma.empdemo.view

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.grishma.empdemo.R
import com.grishma.empdemo.adapter.EmpListAdapter
import com.grishma.empdemo.model.Employee
import com.grishma.empdemo.repository.MainRepository
import com.grishma.empdemo.repository.ViewModelFactory
import com.grishma.empdemo.utils.Constants
import com.grishma.empdemo.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Main Activity
 */
class MainActivity : AppCompatActivity() {

    private lateinit var empListAdapter : EmpListAdapter
    private lateinit var mainViewModel : MainViewModel
    private lateinit var mainRepository : MainRepository


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mainRepository = MainRepository(application = application)
        mainViewModel = ViewModelProvider(this , ViewModelFactory(mainRepository)).get(MainViewModel::class.java)

        initRecyclerview() //Initialize recyclerview

        //observe live data
        mainViewModel.mutableLiveDataListOfEmp.observe(this, Observer {
            empListAdapter.update(it)
            progressBar.visibility = View.GONE
        })

        // Add the functionality to swipe items left in the
        // recycler view to delete that item
        val helper = ItemTouchHelper(
            object : ItemTouchHelper.SimpleCallback(
                0,
                ItemTouchHelper.LEFT
            ) {
                // We are not implementing onMove() in this app
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                // When the use swipes left,
                // delete that word from the database.
                override fun onSwiped(
                    viewHolder: RecyclerView.ViewHolder,
                    direction: Int
                ) {
                    val position = viewHolder.adapterPosition
                    val employee: Employee.Data? = empListAdapter.getEmpAtPosition(position)
                    Toast.makeText(
                        this@MainActivity, "Delete Employee item id : " + employee?.id, Toast.LENGTH_LONG).show()

                    // Delete the emp item
                    AsyncTask.execute {
                        employee?.let { mainViewModel.deleteEmpFromDb(employee = it) }
                    }
                    employee?.let { empListAdapter.deleteItem(it,position) }

                }
            })
        // Attach the item touch helper to the recycler view
        helper.attachToRecyclerView(rvEmployee)
    }

    private fun initRecyclerview() {
        progressBar.visibility = View.VISIBLE
        rvEmployee.apply {
            empListAdapter = EmpListAdapter(mutableListOf())
            rvEmployee.adapter = empListAdapter
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.LAUNCH_SECOND_ACTIVITY) if (resultCode == Activity.RESULT_OK) {

            //get data from intent
            val empData : Employee.Data = data?.getSerializableExtra(Constants.RESULT) as Employee.Data
            val position : Int? =  data.extras?.getInt(Constants.POSITION)

            //update item into db
            AsyncTask.execute {
                mainViewModel.updateEmpFromDb(employee = empData)
            }

            empListAdapter.updateItem(empData,position)

            Toast.makeText(
                this@MainActivity, "Update Employee item id : " + empData.id, Toast.LENGTH_LONG).show()

        }
    }
}