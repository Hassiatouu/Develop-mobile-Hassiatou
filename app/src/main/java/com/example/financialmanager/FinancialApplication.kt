package com.example.financialmanager

import android.app.Application
import com.example.financialmanager.data.AppDatabase
import com.example.financialmanager.data.repository.FinancialRepository

class FinancialApplication : Application() {
    // Using by lazy so the database and repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { AppDatabase.getDatabase(this) }
    val repository by lazy {
        FinancialRepository(
            database.accountDao(),
            database.categoryDao(),
            database.transactionDao()
        )
    }
} 