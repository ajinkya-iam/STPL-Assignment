package me.ajinkyashinde.stplassignment.ui.customerlist

import androidx.paging.PagingSource
import androidx.paging.PagingState
import me.ajinkyashinde.stplassignment.data.model.Customer
import me.ajinkyashinde.stplassignment.data.repository.STPLRepository
import me.ajinkyashinde.stplassignment.utils.AppConstant.DEFAULT_PAGE_INDEX

class CustomerPagingSource(
    private val stplRepository: STPLRepository,
    private val query: String
) : PagingSource<Int, Customer>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Customer> {
        return try {
            val page = params.key ?: DEFAULT_PAGE_INDEX
            val response = stplRepository.getCustomerDetailsList(pageNo = page)

            val filteredData = if (query.isNotEmpty()) {
                response.responseData.filter {
                    it.fName.contains(query, true) ||
                            it.lName.contains(query, true) ||
                            it.mobileNo.contains(query, true)
                }
            } else {
                response.responseData
            }

            LoadResult.Page(
                data = filteredData,
                prevKey = if (page == 1) null else page - 1,
                nextKey = if (response.responseData.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Customer>): Int? {
        return state.anchorPosition?.let { position ->
            state.closestPageToPosition(position)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(position)?.nextKey?.minus(1)
        }
    }

}