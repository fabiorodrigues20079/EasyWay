package com.example.easyway.Entities
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="meal")
data class Meal(
    @PrimaryKey(autoGenerate = false )val mealDate:String,
    @ColumnInfo(name="price") val price: String?,
    @ColumnInfo(name="Did") val Did:Int?,
    @ColumnInfo(name="MPId") val MPId:Int?,
    @ColumnInfo(name="Cid") val Cid:Int?,
    @ColumnInfo(name="description") val description:String?
)
