package me.ajinkyashinde.stplassignment.ui.customerlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import me.ajinkyashinde.stplassignment.data.model.Customer
import me.ajinkyashinde.stplassignment.data.repository.STPLRepository
import javax.inject.Inject

@HiltViewModel
class CustomerListViewModel @Inject constructor(private val stplRepository: STPLRepository) :
    ViewModel() {

    private val queryFlow = MutableStateFlow("")

    fun setQuery(query: String) {
        queryFlow.value = query
    }

    fun fetchCustomerDetails(): Flow<PagingData<Customer>> {
        return queryFlow.flatMapLatest { query ->
            Pager(
                config = PagingConfig(
                    pageSize = 10,
                    enablePlaceholders = false
                ),
                pagingSourceFactory = {
                    CustomerPagingSource(stplRepository = stplRepository, query = query)
                }
            ).flow.cachedIn(viewModelScope)
        }
    }
}
