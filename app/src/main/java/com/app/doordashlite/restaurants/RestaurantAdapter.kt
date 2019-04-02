package com.app.doordashlite.restaurants

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.doordashlite.R
import com.app.doordashlite.extensions.view.loadImage
import com.app.doordashlite.restaurants.repo.entity.Restaurant
import com.app.doordashlite.restaurants.repo.entity.local.CustomItem
import com.app.doordashlite.restaurants.repo.entity.local.StatusType
import kotlinx.android.synthetic.main.layout_item_restaurant.view.*
import kotlinx.android.synthetic.main.layout_item_welcome.view.*

const val WELCOME_ITEM_TYPE = 0
const val RESTAURANT_ITEM_TYPE = 1

class RestaurantAdapter(private val context: Context, private val preferences: SharedPreferences) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var itemList: MutableList<CustomItem> = mutableListOf()
    private lateinit var callback: OnItemClickListener

    /**
     * Adds a collection of all restaurants at the same time to the list
     */
    fun addAll(items: MutableList<CustomItem>) {
        itemList.addAll(items)
        notifyItemRangeInserted(0, items.size - 1)
    }

    /**
     * Adds only one restaurant at a time to the list
     */
    fun add(item: CustomItem) {
        itemList.add(item)
        notifyItemInserted(itemList.size - 1)
    }

    fun remove() {
        notifyItemRemoved(0)
    }

    fun clear() {
        itemList.clear()
        notifyDataSetChanged()
    }

    fun setCallback(callback: OnItemClickListener) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (preferences.getBoolean("IS_DISMISS_CLICKED", false) && preferences.getBoolean
                ("IS_FIRST_TIME_CLICKED", false)) {
            return RestaurantViewHolder(this.inflater.inflate(R.layout.layout_item_restaurant, parent, false))
        }
        return when(viewType) {
            WELCOME_ITEM_TYPE -> {
                val v = this.inflater.inflate(R.layout.layout_item_welcome, parent, false)
                WelcomeItemViewHolder(v)
            } else -> {
                val v = this.inflater.inflate(R.layout.layout_item_restaurant, parent, false)
                RestaurantViewHolder(v)
            }
        }
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            WELCOME_ITEM_TYPE
        } else {
            RESTAURANT_ITEM_TYPE
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is RestaurantAdapter.RestaurantViewHolder) {
            if (position > 0) {
                holder.bindItems(itemList[position].restaurant!!)
            }
        } else {
            (holder as RestaurantAdapter.WelcomeItemViewHolder).bindItem(itemList[0].welcomeMsg)
        }
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

    inner class WelcomeItemViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bindItem(message: String) {
            itemView.textMessage.text = message
            itemView.dismiss.setOnClickListener {
                callback.onWelcomeMsgDismissClicked()
            }
        }
    }

    interface OnItemClickListener {
        fun onRestaurantClicked(restaurant: Restaurant)

        fun onWelcomeMsgDismissClicked()
    }
}