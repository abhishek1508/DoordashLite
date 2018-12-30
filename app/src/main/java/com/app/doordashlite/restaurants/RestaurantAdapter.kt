package com.app.doordashlite.restaurants

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.doordashlite.R
import com.app.doordashlite.extensions.view.loadImage
import com.app.doordashlite.restaurants.repo.entity.Restaurant
import com.app.doordashlite.restaurants.repo.entity.local.StatusType
import kotlinx.android.synthetic.main.layout_item_restaurant.view.*

class RestaurantAdapter(private val context: Context) :
        RecyclerView.Adapter<RestaurantAdapter.RestaurantViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList: MutableList<Restaurant> = mutableListOf()
    private lateinit var callback: OnItemClickListener

    fun addAll(items: MutableList<Restaurant>) {
        itemList.clear()
        notifyItemRangeRemoved(0, items.size - 1)
        itemList.addAll(items)
        notifyItemRangeInserted(0, items.size - 1)
    }

    fun add(item: Restaurant) {
        itemList.add(item)
        notifyItemInserted(itemList.size - 1)
    }

    fun setCallback(callback: OnItemClickListener) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantViewHolder {
        val v = this.inflater.inflate(R.layout.layout_item_restaurant, parent, false)
        return RestaurantViewHolder(v)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: RestaurantViewHolder, position: Int) {
        holder.bindItems(itemList[position])
    }

    inner class RestaurantViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val closed = "Closed"
        fun bindItems(restaurant: Restaurant) {
            itemView.restaurantImage.loadImage(restaurant.coverImgUrl)
            itemView.restaurantName.text = restaurant.name
            itemView.restaurantDescription.text = restaurant.description
            when(restaurant.statusType) {
                StatusType.STATUS_PREORDER.statusType -> itemView.restaurantStatus.text = closed
                else -> itemView.restaurantStatus.text = restaurant.status
            }
            itemView.setOnClickListener {
                callback.onRestaurantClicked(restaurant)
            }
        }
    }

    interface OnItemClickListener {
        fun onRestaurantClicked(restaurant: Restaurant)
    }
}