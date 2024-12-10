package me.ajinkyashinde.stplassignment.data.api

import me.ajinkyashinde.stplassignment.data.model.CustomerDetailsResponse
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface NetworkService {
    @GET("/api/CustomerDetails/GetCustomerDetails")
    suspend fun getCustomerDetails(
        @Query("pageno") pageNo: Int,
        @Query("pagesize") pageSize: Int,
        @Query("UnitId") unitId: Int
    ): CustomerDetailsResponse
}