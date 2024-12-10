package me.ajinkyashinde.stplassignment.data.repository

import me.ajinkyashinde.stplassignment.data.api.NetworkService
import me.ajinkyashinde.stplassignment.data.model.CustomerDetailsResponse
import me.ajinkyashinde.stplassignment.utils.AppConstant.DEFAULT_PAGE_INDEX
import me.ajinkyashinde.stplassignment.utils.AppConstant.DEFAULT_PAGE_SIZE
import me.ajinkyashinde.stplassignment.utils.AppConstant.DEFAULT_UNIT_ID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class STPLRepository @Inject constructor(private val networkService: NetworkService) {
    suspend fun getCustomerDetailsList(
        pageNo: Int = DEFAULT_PAGE_INDEX,
        pageSize: Int = DEFAULT_PAGE_SIZE,
        unitId: Int = DEFAULT_UNIT_ID
    ): CustomerDetailsResponse {
        return networkService.getCustomerDetails(pageNo, pageSize, unitId)
    }
}