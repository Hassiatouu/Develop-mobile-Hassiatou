package com.example.financialmanager.ui.transaction

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.financialmanager.R
import com.example.financialmanager.data.model.Transaction
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Locale

class TransactionListAdapter : ListAdapter<Transaction, TransactionListAdapter.TransactionViewHolder>(TransactionsComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TransactionViewHolder {
        return TransactionViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: TransactionViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    class TransactionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val labelItemView: TextView = itemView.findViewById(R.id.transaction_label)
        private val dateItemView: TextView = itemView.findViewById(R.id.transaction_date)
        private val amountItemView: TextView = itemView.findViewById(R.id.transaction_amount)
        private val categoryIcon: ImageView = itemView.findViewById(R.id.category_icon)

        fun bind(transaction: Transaction?) {
            if (transaction == null) return

            labelItemView.text = transaction.label
            
            val dateFormat = SimpleDateFormat("dd MMMM yyyy", Locale.FRANCE)
            dateItemView.text = dateFormat.format(transaction.date)

            val currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE)
            val amountString = currencyFormat.format(transaction.amount)

            if (transaction.isIncome()) {
                amountItemView.text = "+ $amountString"
                amountItemView.setTextColor(Color.GREEN)
            } else {
                amountItemView.text = "- $amountString"
                amountItemView.setTextColor(Color.RED)
            }
            
            // Here you could load a specific icon based on category
            // For now, just a placeholder
            categoryIcon.setImageResource(R.drawable.ic_launcher_background)

        }

        companion object {
            fun create(parent: ViewGroup): TransactionViewHolder {
                val view: View = LayoutInflater.from(parent.context)
                    .inflate(R.layout.list_item_transaction, parent, false)
                return TransactionViewHolder(view)
            }
        }
    }

    class TransactionsComparator : DiffUtil.ItemCallback<Transaction>() {
        override fun areItemsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Transaction, newItem: Transaction): Boolean {
            return oldItem == newItem
        }
    }
} 