package com.droal.testproximate.list

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.ImageLoader
import coil.api.load
import coil.decode.SvgDecoder
import coil.request.LoadRequest
import com.droal.testproximate.R
import com.droal.testproximate.databinding.ItemListProductsBinding
import com.droal.testproximate.data.ProductData
import java.util.*

class ListProductsAdapter(val onClickListener: OnClickListener): RecyclerView.Adapter<ListProductsAdapter.ListProdcutsViewHolder>() {


    class ListProdcutsViewHolder(private var binding: ItemListProductsBinding): RecyclerView.ViewHolder(binding.root){
        val title: TextView = binding.tvTitle
        val image: ImageView = binding.ivImgProd
        val desc: TextView = binding.tvShortDesc
    }

    var data = listOf<ProductData>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListProdcutsViewHolder {
        return ListProdcutsViewHolder(ItemListProductsBinding.inflate(LayoutInflater.from(parent.context)))
    }



    fun ImageView.loadImageFromUrl(myUrl: String?) {
        myUrl?.let {
            if (it.toLowerCase(Locale.ENGLISH).endsWith("svg")) {
                val imageLoader = ImageLoader.Builder(this.context)
                    .componentRegistry {
                        add(SvgDecoder(this@loadImageFromUrl.context))
                    }
                    .build()
                val request = LoadRequest.Builder(this.context)
                    .data(it)
                    .target(this)
                    .placeholder(R.drawable.loading_animation)
                    .error(R.drawable.err_img)
                    .build()
                imageLoader.execute(request)
            } else {
                this.load(myUrl){
                    placeholder(R.drawable.loading_animation)
                    error(R.drawable.err_img)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: ListProdcutsViewHolder, position: Int) {
        val item = data[position]
        holder.title.text = item.title

        holder.image.loadImageFromUrl(item.image)

        holder.desc.text = item.shortDescription

        holder.itemView.setOnClickListener {
            onClickListener.onClick(item)
        }
    }

    override fun getItemCount() = data.size


    class OnClickListener(val clickListener: (product: ProductData) -> Unit) {
        fun onClick(product: ProductData) = clickListener(product)
    }




}