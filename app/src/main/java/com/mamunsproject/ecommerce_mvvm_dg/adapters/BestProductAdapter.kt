package com.mamunsproject.ecommerce_mvvm_dg.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mamunsproject.ecommerce_mvvm_dg.data.Product
import com.mamunsproject.ecommerce_mvvm_dg.databinding.BestDealsRvItemBinding
import com.mamunsproject.ecommerce_mvvm_dg.databinding.ProductRvItemBinding

class BestProductAdapter : RecyclerView.Adapter<BestProductAdapter.BestProductViewHolder>() {
    class BestProductViewHolder(private val binding: ProductRvItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product) {
            binding.apply {
                Glide.with(itemView)
                    .load(product.images[0])
                    .into(imgProduct)

                product.offerPercentage?.let {
                    val remainingPricePercenage = 1f - it
                    val priceAfterOffer = remainingPricePercenage * product.price
                    tvNewPrice.text = "$ ${String.format("%.2f", priceAfterOffer)}"
                }
                if (product.offerPercentage == null) tvNewPrice.visibility = View.INVISIBLE

                tvPrice.text = "$ ${product.price}"
                tvName.text = product.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestProductViewHolder {
        return BestProductViewHolder(
            ProductRvItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: BestProductViewHolder, position: Int) {
        val product = differ.currentList[position]
        holder.bind(product)
    }


    private val differCallback = object : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

}