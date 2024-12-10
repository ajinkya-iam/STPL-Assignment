package me.ajinkyashinde.stplassignment.ui.customerlist

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import me.ajinkyashinde.stplassignment.R
import me.ajinkyashinde.stplassignment.data.model.Customer
import me.ajinkyashinde.stplassignment.databinding.ActivityCustomerListBinding
import me.ajinkyashinde.stplassignment.utils.Helper.isInternetAvailable
import me.ajinkyashinde.stplassignment.utils.Helper.showInternetNotFoundDialog
import javax.inject.Inject

@AndroidEntryPoint
class CustomerListActivity : AppCompatActivity() {

    @Inject
    lateinit var adapter: CustomerListAdapter
    private lateinit var data: PagingData<Customer>

    private lateinit var customerListViewModel: CustomerListViewModel
    private lateinit var binding : ActivityCustomerListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupViewModel()
        setupObserver()
        setupUI()

        binding.searchView.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                s?.let {
                    customerListViewModel.setQuery(s.toString())
                }
            }

            override fun afterTextChanged(s: Editable?) {}
        })
    }

    private fun setupViewModel() {
        customerListViewModel = ViewModelProvider(this)[CustomerListViewModel::class.java]
        if (isInternetAvailable(this)) customerListViewModel.fetchCustomerDetails()
        else {
            val alertDialog = AlertDialog.Builder(this)
                .setTitle("No Internet Connection")
                .setMessage("Please check your internet connection and try again.")
                .setIcon(R.drawable.baseline_wifi_off_24)
                .setCancelable(false)
                .setPositiveButton("Try Again") { dialog, _ ->
                    if (isInternetAvailable(this)) {
                        customerListViewModel.fetchCustomerDetails()
                    } else {
                        showInternetNotFoundDialog(this)
                    }
                }
                .create()
            alertDialog.show()
        }
    }

    private fun setupUI() {
        setSupportActionBar(binding.toolbar)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }


    @SuppressLint("SetTextI18n")
    private fun setupObserver() {
        lifecycleScope.launch {
            customerListViewModel.fetchCustomerDetails().collectLatest { pagingData ->
                data = pagingData
                adapter.submitData(data)
            }
        }



        adapter.addLoadStateListener { loadState ->
            if (loadState.refresh is LoadState.Loading) {
                binding.progressBar.visibility = View.VISIBLE
                binding.emptyView.visibility = View.GONE
            } else if (loadState.refresh is LoadState.NotLoading) {
                binding.progressBar.visibility = View.GONE
                if (adapter.itemCount > 0) {
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.emptyView.visibility = View.GONE
                } else {
                    binding.recyclerView.visibility = View.GONE
                    binding.emptyView.visibility = View.VISIBLE
                    binding.emptyView.text = "No data found"
                }
            } else if (loadState.refresh is LoadState.Error) {
                binding.progressBar.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
                binding.emptyView.text = "Error: Failed to fetch data"
            } else if (loadState.append.endOfPaginationReached) {
                binding.progressBar.visibility = View.GONE
                binding.emptyView.visibility = View.VISIBLE
                Toast.makeText(this, "No more pages to load", Toast.LENGTH_SHORT).show()
            }
        }
    }
}