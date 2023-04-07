package com.kamrul_hasan.shosti_ecommerce.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kamrul_hasan.shosti_ecommerce.model.cart.CartProduct
import com.kamrul_hasan.shosti_ecommerce.model.user.User

@Database(entities = [User::class, CartProduct::class], version = 1, exportSchema = false)

abstract class MyDatabase : RoomDatabase() {
    abstract fun ShostirBazaarDao(): ShostirBazaarDao

    companion object {
        @Volatile
        private var INSTANCE: MyDatabase? = null

        fun getDatabase(context: Context): MyDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, MyDatabase::class.java, "shostir_bazaar_database"
                ).fallbackToDestructiveMigration().build()

                INSTANCE = instance
                return instance
            }
        }
    }
}