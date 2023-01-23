package com.example.easyway.Entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName="transaction")
data class Transaction(
    @PrimaryKey(autoGenerate=false) val transactionDate:String,
    @ColumnInfo(name="previousBalance") val previousBalance: String?,
    @ColumnInfo(name="transactionTime") val transactionTime:String?,
    @ColumnInfo(name="Pid") val Pid: Int?
)