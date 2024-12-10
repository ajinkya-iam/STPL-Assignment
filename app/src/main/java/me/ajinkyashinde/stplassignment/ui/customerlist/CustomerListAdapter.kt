package me.ajinkyashinde.stplassignment.ui.customerlist

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import me.ajinkyashinde.stplassignment.R
import me.ajinkyashinde.stplassignment.data.model.Customer
import me.ajinkyashinde.stplassignment.databinding.CustomerListItemBinding

class CustomerListAdapter: PagingDataAdapter<Customer, CustomerListAdapter.DataViewHolder>(MOVIE_COMPARATOR) {

    companion object {
        private val MOVIE_COMPARATOR = object : DiffUtil.ItemCallback<Customer>() {
            override fun areItemsTheSame(oldItem: Customer, newItem: Customer): Boolean =
                oldItem.customerId == newItem.customerId

            override fun areContentsTheSame(oldItem: Customer, newItem: Customer): Boolean =
                oldItem == newItem
        }
    }

    class DataViewHolder(private val binding: CustomerListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(customerDetails: Customer?, position: Int) {
            binding.tvCustomerName.text = "${customerDetails?.fName} ${customerDetails?.lName}"
            binding.tvCustomerMobile.text = customerDetails?.mobileNo

            if (customerDetails?.isCow == 1) binding.ivAnimalImage.setImageResource(R.drawable.cow)
            else if (customerDetails?.isBuffalo == 1) binding.ivAnimalImage.setImageResource(R.drawable.buffalo)
            else binding.ivAnimalImage.setImageResource(R.drawable.placeholderimg)

            val textColor = when (position % 3) {
                0 -> Color.RED
                1 -> Color.GREEN
                2 -> Color.BLUE
                else -> Color.BLACK
            }
            binding.tvCustomerName.setTextColor(textColor)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            CustomerListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )


    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }

}