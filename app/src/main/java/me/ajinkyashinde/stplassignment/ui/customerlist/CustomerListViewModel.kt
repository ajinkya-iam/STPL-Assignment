package me.ajinkyashinde.stplassignment.ui.customerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import me.ajinkyashinde.stplassignment.data.model.Customer
import me.ajinkyashinde.stplassignment.data.repository.STPLRepository
import javax.inject.Inject

@HiltViewModel
class CustomerListViewModel @Inject constructor(private val stplRepository: STPLRepository) :
    ViewModel() {
    fun fetchCustomerDetails(): Flow<PagingData<Customer>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { CustomerPagingSource(stplRepository = stplRepository) }
        ).flow.cachedIn(viewModelScope)
    }

}