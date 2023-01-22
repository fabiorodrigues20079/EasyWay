package com.example.easyway.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easyway.Entities.UserInfo

@Dao
interface UserDao
{
    @Query("SELECT * FROM userInfo")
    fun getUserInfo(): List<UserInfo>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user:UserInfo)


}