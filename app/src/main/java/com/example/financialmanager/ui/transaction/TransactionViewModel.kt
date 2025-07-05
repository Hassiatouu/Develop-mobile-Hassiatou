package com.example.financialmanager.ui.transaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financialmanager.data.model.Account
import com.example.financialmanager.data.model.Category
import com.example.financialmanager.data.model.Transaction
import com.example.financialmanager.data.repository.FinancialRepository
import kotlinx.coroutines.launch
import java.util.Date

class TransactionViewModel(private val repository: FinancialRepository) : ViewModel() {

    // Assuming a single account for simplicity for now. We'll use accountId = 1.
    private val accountId = 1
    val allTransactions: LiveData<List<Transaction>> = repository.getAllTransactionsForAccount(accountId)
    val allCategories: LiveData<List<Category>> = repository.getAllCategories()

    // We can expose the account details if needed
    val account: LiveData<Account> = repository.getAccountById(accountId)

    fun insertTransaction(type: String, label: String, amount: Double, date: Date, categoryId: Int?) {
        val transaction = Transaction(
            type = type,
            label = label,
            amount = amount,
            date = date,
            accountId = accountId,
            categoryId = categoryId
        )
        viewModelScope.launch {
            repository.insertTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }

    // Dummy data for initial setup
    fun addInitialData() {
        viewModelScope.launch {
            // Check if data exists
            if (repository.getAccountById(1).value == null) {
                repository.insertAccount(Account(id = 1, name = "Portefeuille principal", balanceInitial = 1000.0))
                repository.insertCategory(Category(id = 1, name = "Alimentation", color = "#FF0000"))
                repository.insertCategory(Category(id = 2, name = "Transport", color = "#00FF00"))
                repository.insertCategory(Category(id = 3, name = "Salaire", color = "#0000FF"))

                insertTransaction("income", "Salaire Juillet", 2500.0, Date(), 3)
                insertTransaction("expense", "Courses", 85.50, Date(), 1)
            }
        }
    }
} 