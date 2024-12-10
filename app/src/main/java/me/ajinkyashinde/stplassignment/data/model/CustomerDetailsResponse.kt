package me.ajinkyashinde.stplassignment.data.model

data class CustomerDetailsResponse(
    val statusCode: String,
    val statusMessage: String,
    val responseData: List<Customer>,
    val responseData1: PaginationInfo
)

data class Customer(
    val customerId: Int,
    val fName: String,
    val mName: String?,
    val lName: String,
    val rfName: String?,
    val rmName: String?,
    val rlName: String?,
    val mobileNo: String,
    val isCow: Int,
    val isBuffalo: Int
)

data class PaginationInfo(
    val pageNo: Int,
    val totalPages: Int,
    val pageCount: Int
)