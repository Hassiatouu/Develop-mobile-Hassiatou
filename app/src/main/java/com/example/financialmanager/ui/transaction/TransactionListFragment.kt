package com.example.financialmanager.ui.transaction

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.financialmanager.FinancialApplication
import com.example.financialmanager.R
import com.example.financialmanager.databinding.FragmentTransactionListBinding
import com.example.financialmanager.ui.ViewModelFactory
import java.text.NumberFormat
import java.util.Locale

class TransactionListFragment : Fragment() {

    private var _binding: FragmentTransactionListBinding? = null
    private val binding get() = _binding!!

    private val transactionViewModel: TransactionViewModel by viewModels {
        ViewModelFactory((activity?.application as FinancialApplication).repository)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentTransactionListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = TransactionListAdapter()
        binding.transactionsRecyclerview.adapter = adapter
        binding.transactionsRecyclerview.layoutManager = LinearLayoutManager(context)

        transactionViewModel.allTransactions.observe(viewLifecycleOwner) { transactions ->
            transactions?.let { adapter.submitList(it) }
        }

        transactionViewModel.account.observe(viewLifecycleOwner) { account ->
            account?.let {
                // This is a simplified balance calculation. A real app should sum transactions.
                val balance = it.balanceInitial
                val currencyFormat = NumberFormat.getCurrencyInstance(Locale.FRANCE)
                binding.balanceTextview.text = currencyFormat.format(balance)
            }
        }
        
        // Add some sample data for the first run
        transactionViewModel.addInitialData()

        binding.addTransactionFab.setOnClickListener {
            // TODO: Navigate to an Add/Edit screen
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
} 