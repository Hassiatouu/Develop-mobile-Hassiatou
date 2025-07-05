package com.example.financialmanager.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.Date

@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = Account::class,
            parentColumns = ["id"],
            childColumns = ["accountId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Category::class,
            parentColumns = ["id"],
            childColumns = ["categoryId"],
            onDelete = ForeignKey.SET_NULL
        )
    ]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val type: String, // "income" or "expense"
    val label: String,
    val amount: Double,
    val date: Date,

    @ColumnInfo(index = true)
    val accountId: Int,

    @ColumnInfo(index = true)
    val categoryId: Int?
) {
    fun isIncome(): Boolean = type.equals("income", ignoreCase = true)
} 