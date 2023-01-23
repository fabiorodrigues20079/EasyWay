package com.example.easyway

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.easyway.Dao.MealsDao
import com.example.easyway.Dao.PersonDao
import com.example.easyway.Dao.TransactionsDao
import com.example.easyway.Dao.UserDao
import com.example.easyway.Entities.Meal
import com.example.easyway.Entities.Person
import com.example.easyway.Entities.Transaction
import com.example.easyway.Entities.UserInfo


@Database(entities=[UserInfo::class,Person::class,Meal::class,Transaction::class],version=1)
abstract class IPCADatabase:RoomDatabase()
{
    abstract fun userInfoDao(): UserDao
    abstract fun PersonDao(): PersonDao
    abstract fun MealsDao(): MealsDao
    abstract fun TransactionsDao(): TransactionsDao

    companion object
    {
        @Volatile // Se já existe não cria uma nova, se não cria
        private var INSTANCE: IPCADatabase?=null

        fun getDataBase(context:Context):IPCADatabase
        {
            val tempInstance = INSTANCE
            if(tempInstance!=null) // Se a instância da base de dados já tiver sido criada apenas retorna
            {
                return tempInstance
            }

            synchronized(this)
            {
                val instance = Room.databaseBuilder( // Criar a instância da base de dados
                    context.applicationContext,
                    IPCADatabase::class.java,
                    "IPCA_database2").build()

                INSTANCE = instance
                return instance
            }
        }
    }
}