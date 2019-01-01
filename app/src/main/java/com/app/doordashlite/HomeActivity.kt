package com.app.doordashlite

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Color
import android.support.v4.app.Fragment
import com.app.doordashlite.extensions.android.support.v7.app.replaceFragmentSafely
import com.app.doordashlite.restaurants.RestaurantFragment
import com.app.doordashlite.restaurants.detail.RestaurantDetailFragment
import com.app.doordashlite.restaurants.repo.entity.local.RestaurantEvent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.layout_toolbar.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject

class HomeActivity : AppCompatActivity(), HasSupportFragmentInjector {

    @Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: HomeViewModel

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector

    // Activity lifecycle methods //////////////////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        EventBus.getDefault().register(this)
        initUI()
        initData()
    }

    override fun onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy()
    }

    // Private methods /////////////////////////////////////////////////////////////////////////////
    private fun initUI() {
        app_bar.setTitleTextColor(Color.WHITE)
        setSupportActionBar(app_bar)
        supportActionBar!!.title = getString(R.string.discover)
    }

    private fun initData() {
        this.replaceFragmentSafely(RestaurantFragment.newInstance(), getString(R.string.restaurant_fragment_tag), R.id.content)
    }

    // Event Bus methods ///////////////////////////////////////////////////////////////////////////

    /**
     * Received events when clicked on an item in the recycler view.
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onReceiveEvent(event: RestaurantEvent) {
        this.replaceFragmentSafely(RestaurantDetailFragment.newInstance(event.restaurant), getString(R.string.restaurant_detail_fragment_tag), R.id.content)
    }
}
