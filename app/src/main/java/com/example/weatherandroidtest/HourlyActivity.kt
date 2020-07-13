package com.example.weatherandroidtest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.weatherandroidtest.adapter.HourlyAdapter
import com.example.weatherandroidtest.models.Daily
import com.example.weatherandroidtest.models.Hourly
import com.example.weatherandroidtest.presenter.HourlyPresenter
import com.example.weatherandroidtest.services.smartlocation.LocationManager
import com.example.weatherandroidtest.view.HourlyViewListener
import kotlinx.android.synthetic.main.activity_hourly.*
import kotlinx.android.synthetic.main.activity_hourly.swipeRefresh
import kotlinx.android.synthetic.main.activity_hourly.txtCityName
import kotlinx.android.synthetic.main.activity_main.*

class HourlyActivity : AppCompatActivity(), HourlyViewListener {
    private lateinit var presenter: HourlyPresenter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hourly)

        this.presenter = HourlyPresenter(this, this)
        this.bindView();
        this.initIntent();
    }

    private fun bindView() {
        rcListHourly().layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);

        swipeRefresh.setOnRefreshListener {
            this.presenter.initDataIntent(intent.extras)
        }
    }

    private fun initIntent() {
        this.presenter.initDataIntent(intent.extras)
    }

    override fun txtCityName(): TextView {
        return txtCityName
    }

    override fun rcListHourly(): RecyclerView {
        return rcListHourly
    }

    override fun swipeRefresh(): SwipeRefreshLayout {
        return swipeRefresh;
    }

    override fun setSwipeRefresh(boolean: Boolean) {
        this.swipeRefresh().isRefreshing = boolean
    }

    override fun setAdapter(arrayList: ArrayList<Hourly>) {
        val hourlyAdapter = HourlyAdapter(arrayList, this@HourlyActivity)
        rcListHourly().apply {
            adapter = hourlyAdapter
        }
    }

    override fun txtUnit(): TextView {
       return txtUnit
    }
}