package com.app.doordashlite.restaurants.detail

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.app.doordashlite.R
import com.app.doordashlite.di.Injectable
import com.app.doordashlite.extensions.view.loadImage
import com.app.doordashlite.restaurants.repo.entity.Restaurant
import com.app.doordashlite.restaurants.repo.entity.RestaurantDetail
import kotlinx.android.synthetic.main.layout_restaurant_detail.*
import javax.inject.Inject

class RestaurantDetailFragment : Fragment(), Injectable {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var viewModel: RestaurantDetailViewModel

    companion object{
        private val ARG_RESTAURANT = "restaurant"

        fun newInstance(restaurant: Restaurant): RestaurantDetailFragment {
            val args = Bundle()
            args.putParcelable(ARG_RESTAURANT, restaurant)
            val fragment = RestaurantDetailFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.layout_restaurant_detail, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RestaurantDetailViewModel::class.java)
        showRestaurantDetails()
        observe()
    }

    private fun getRestaurantFromBundle(): Restaurant? {
        return arguments?.getParcelable(ARG_RESTAURANT)
    }

    private fun showRestaurantDetails() {
        val restaurant = getRestaurantFromBundle()
        restaurantImage.loadImage(restaurant?.coverImgUrl)
        restaurantName.text = restaurant?.name
        restaurantDescription.text = restaurant?.description
    }

    private fun observe() {
        progress.visibility = View.VISIBLE
        val restaurant = getRestaurantFromBundle()
        if (restaurant != null) {
            viewModel.getRestaurantDetail(restaurant.id).observe(this, Observer<RestaurantDetail> {
                progress.visibility = View.GONE
                restaurantImage.loadImage(it?.coverImgUrl)
                restaurantName.text = it?.name
                restaurantDescription.text = it?.description
            })
        }
    }
}