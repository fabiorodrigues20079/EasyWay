package com.example.easyway.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easyway.Entities.Person
import com.example.easyway.Entities.UserInfo


@Dao
interface PersonDao
{
    @Query("SELECT * FROM person")
    fun getUserInfo(): List<Person>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(person: Person)

    @Query("SELECT * FROM person WHERE person.pid =:pid")
    fun getPerson(pid:Int):List<Person>
}