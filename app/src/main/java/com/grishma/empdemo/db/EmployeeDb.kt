package com.grishma.empdemo.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.grishma.empdemo.model.Employee
import com.grishma.empdemo.utils.Converters

/**
 * Employee Database initialization
 */
@Database(
    entities = [Employee.Data::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class EmployeeDb : RoomDatabase() {

    companion object {
        private var empDbInstance: EmployeeDb? = null
        open fun getDatabase(context: Context): EmployeeDb? {
            if (empDbInstance == null) {
                synchronized(EmployeeDb::class.java) {
                    if (empDbInstance == null) {
                        empDbInstance = Room.databaseBuilder(
                            context.applicationContext,
                            EmployeeDb::class.java, "emp_database"
                        ) // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return empDbInstance
        }
    }

  abstract fun empDao(): EmpDao
}
