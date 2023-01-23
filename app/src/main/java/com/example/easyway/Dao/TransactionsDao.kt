package com.example.easyway.Dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.easyway.Entities.Transaction

@Dao
interface TransactionsDao
{
   /* @Query("SELECT * FROM transaction")
    fun getAllTransactions():List<Transaction>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transaction:Transaction)
*/

}