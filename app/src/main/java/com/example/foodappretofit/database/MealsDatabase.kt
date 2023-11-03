package com.example.foodappretofit.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.foodappretofit.pojo.MealDB
import com.example.foodappretofit.pojo.MealList
import com.example.foodappretofit.utils.Contract.USER_DATABASE

@Database(entities = [MealDB::class], version = 6, exportSchema = false)
abstract class MealsDatabase: RoomDatabase() {
    abstract fun dao() : Dao
    companion object{
        @Volatile
        private var INSTANCE: MealsDatabase? = null
        @Synchronized
        fun getInstance(context: Context): MealsDatabase{
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MealsDatabase::class.java,
                    USER_DATABASE
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}