package com.example.easyway.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="userInfo")
data class UserInfo(
    @PrimaryKey(autoGenerate=true)val pid:Int?,
    @ColumnInfo(name="email") val email: String?,
    @ColumnInfo(name="hash_password") val hashPassword: String?,
    @ColumnInfo(name="isEmployee") val isEmployee:Int,
    @ColumnInfo(name="Balance") val Balance:Double,
    @ColumnInfo(name="photoURI") val photoURI: String?
    )
