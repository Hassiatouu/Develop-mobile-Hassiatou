package com.example.financialmanager.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financialmanager.data.repository.FinancialRepository
import com.example.financialmanager.ui.transaction.TransactionViewModel

class ViewModelFactory(private val repository: FinancialRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return TransactionViewModel(repository) as T
        }
        // Add other ViewModels here
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 