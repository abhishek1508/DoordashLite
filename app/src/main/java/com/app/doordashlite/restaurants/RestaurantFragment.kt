package com.app.doordashlite.restaurants

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.doordashlite.R
import com.app.doordashlite.di.Injectable
import com.app.doordashlite.factory.ConnectivityFactory
import com.app.doordashlite.restaurants.repo.entity.Restaurant
import com.app.doordashlite.restaurants.repo.entity.local.CustomItem
import com.app.doordashlite.restaurants.repo.entity.local.RestaurantEvent
import kotlinx.android.synthetic.main.layout_restaurant.*
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject


class RestaurantFragment : Fragment(), Injectable, RestaurantAdapter.OnItemClickListener {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RestaurantViewModel
    private lateinit var adapter: RestaurantAdapter
    private lateinit var manager: LinearLayoutManager
    private lateinit var  preferences: SharedPreferences

    companion object{
        fun newInstance(): RestaurantFragment {
            return RestaurantFragment()
        }
    }

    // Fragment lifecycle methods //////////////////////////////////////////////////////////////////
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_restaurant, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RestaurantViewModel::class.java)
        preferences = activity!!.getSharedPreferences("RESTAURANTS", Context.MODE_PRIVATE)
        initUI()
        observe()
    }

    fun reloadData() {
        adapter.clear()
        observe()
    }

    // Private methods /////////////////////////////////////////////////////////////////////////////
    private fun initUI() {
        adapter = RestaurantAdapter(context!!, preferences)
        adapter.setCallback(this)
        manager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        restaurantRecycler.layoutManager = manager
        restaurantRecycler.addItemDecoration(
                DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
        restaurantRecycler.adapter = adapter
    }

    private fun observe() {
        progress.visibility = View.VISIBLE
        viewModel.getRestaurants(viewModel.getLatitude(),
                                 viewModel.getLongitude(),
                                 viewModel.getOffset(),
                                 viewModel.getLimit())
                .observe(this, Observer<List<Restaurant>> {
                    progress.visibility = View.GONE
                    val restaurantList = it
                    val customList = mutableListOf<CustomItem>()
                    val customItem = CustomItem(null, "Welcome Message")
                    customList.add(customItem)
                    for (item in restaurantList!!) {
                        val custom = CustomItem(item, "")
                        customList.add(custom)
                    }
                    adapter.addAll(customList)
                    restaurantRecycler.scrollToPosition(viewModel.getFirstVisibleItemPosition())
                })
    }

    private fun saveFirstVisibleItemPosition() {
        val layoutManager = restaurantRecycler.layoutManager as LinearLayoutManager
        viewModel.setFirstVisibleItemPosition(layoutManager.findFirstVisibleItemPosition())
    }

    // RestaurantAdapter.OnItemClickListener methods ///////////////////////////////////////////////
    override fun onRestaurantClicked(restaurant: Restaurant) {
        saveFirstVisibleItemPosition()
        EventBus.getDefault().post(RestaurantEvent(restaurant))
    }

    override fun onWelcomeMsgDismissClicked() {

        preferences.edit().putBoolean("IS_DISMISS_CLICKED", true).apply()
        preferences.edit().putBoolean("IS_FIRST_TIME_CLICKED", true).apply()
        adapter.remove()
    }
}