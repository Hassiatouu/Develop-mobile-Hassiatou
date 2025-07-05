package com.example.financialmanager.data.repository

import androidx.lifecycle.LiveData
import com.example.financialmanager.data.dao.AccountDao
import com.example.financialmanager.data.dao.CategoryDao
import com.example.financialmanager.data.dao.TransactionDao
import com.example.financialmanager.data.model.Account
import com.example.financialmanager.data.model.Category
import com.example.financialmanager.data.model.Transaction

class FinancialRepository(
    private val accountDao: AccountDao,
    private val categoryDao: CategoryDao,
    private val transactionDao: TransactionDao
) {

    // Account operations
    fun getAllAccounts(): LiveData<List<Account>> = accountDao.getAllAccounts()
    fun getAccountById(accountId: Int): LiveData<Account> = accountDao.getAccountById(accountId)
    suspend fun insertAccount(account: Account) {
        accountDao.insert(account)
    }

    // Category operations
    fun getAllCategories(): LiveData<List<Category>> = categoryDao.getAllCategories()
    suspend fun insertCategory(category: Category) {
        categoryDao.insert(category)
    }

    // Transaction operations
    fun getAllTransactionsForAccount(accountId: Int): LiveData<List<Transaction>> {
        return transactionDao.getAllTransactionsForAccount(accountId)
    }
    suspend fun insertTransaction(transaction: Transaction) {
        transactionDao.insert(transaction)
    }
    suspend fun updateTransaction(transaction: Transaction) {
        transactionDao.update(transaction)
    }
    suspend fun deleteTransaction(transaction: Transaction) {
        transactionDao.delete(transaction)
    }
} 