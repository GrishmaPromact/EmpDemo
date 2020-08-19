package com.grishma.empdemo.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.grishma.empdemo.R
import com.grishma.empdemo.model.Employee
import com.grishma.empdemo.utils.Constants
import com.grishma.empdemo.view.EmpDetailsUpdateActivity
import kotlinx.android.synthetic.main.item_emp.view.*

/**
 * EmpListAdapter
 */
class EmpListAdapter(private var empList: MutableList<Employee.Data>) :
    RecyclerView.Adapter<EmpListAdapter.EmpViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): EmpListAdapter.EmpViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_emp, parent, false)
        return EmpViewHolder(view)
    }

    override fun getItemCount(): Int {
        return empList.size;
    }

    override fun onBindViewHolder(holder: EmpListAdapter.EmpViewHolder, position: Int) {
        holder.bind(empList[position])
    }


    class EmpViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val id: TextView = view.tvEmpId
        private val name: TextView = view.tvEmpName
        private val salary: TextView = view.tvEmpSalary
        private val age: TextView = view.tvEmpAge
        private val imgUserProfile: ImageView = view.ivUserProfile
        private val clParentLayout: ConstraintLayout = view.clParentLayout

        fun bind(data: Employee.Data) {

            id.text = data.id
            name.text = data.employeeName
            salary.text = data.employeeSalary
            age.text = data.employeeAge

            clParentLayout.setOnClickListener {
                val intent = Intent(clParentLayout.context, EmpDetailsUpdateActivity::class.java)
                intent.putExtra(Constants.DATA, data)
                intent.putExtra(Constants.POSITION,adapterPosition)
                (clParentLayout.context as Activity).startActivityForResult(intent, Constants.LAUNCH_SECOND_ACTIVITY)
            }
            Glide.with(imgUserProfile.context).load(data.profileImage)
                .placeholder(R.drawable.user_placeholder)
                .diskCacheStrategy(DiskCacheStrategy.ALL).into(imgUserProfile)
        }

    }

    /**
     * Update and notify adapter
     */
    fun update(data: List<Employee.Data>) {
        empList.addAll(data)
        notifyDataSetChanged()
    }

    /**
     * Get the emp at a given position.
     * This method is useful for identifying which word
     * was clicked or swiped in methods that handle user events.
     *
     * @param position
     * @return The word at the given position
     */
    fun getEmpAtPosition(position: Int): Employee.Data? {
        return empList[position]
    }


    /**
     * delete item and notify adapter
     */
    fun deleteItem(data: Employee.Data, position: Int) {
        empList.remove(data)
        notifyItemRemoved(position)
    }

    /**
     * update item and notify adapter
     */
    fun updateItem(data: Employee.Data, position: Int?) {
        position?.let { empList.removeAt(it) }
        position?.let { empList.add(it,data) }
        position?.let { notifyItemChanged(it) }
    }
}