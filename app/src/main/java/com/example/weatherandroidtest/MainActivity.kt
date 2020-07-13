package com.example.weatherandroidtest

import android.content.Intent
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.example.weatherandroidtest.models.WeatherResponse
import com.example.weatherandroidtest.presenter.MainPresenter
import com.example.weatherandroidtest.services.smartlocation.LocationManager
import com.example.weatherandroidtest.services.smartlocation.LocationUpdateListener
import com.example.weatherandroidtest.view.MainViewListener
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.get
import org.koin.android.scope.currentScope
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity(), MainViewListener, LocationUpdateListener {
    private val presenter: MainPresenter by currentScope.inject { parametersOf(this,this) }
    private val locationManager: LocationManager = get { parametersOf(this)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //init location manager
        this.locationManager.locationUpdateListener = this;

        this.initView()
    }

    private fun initView() {
        searchInput.setOnEditorActionListener { v, actionId, event ->

            return@setOnEditorActionListener this.presenter?.initOnEditorAction(actionId)
        }

        swipeRefresh.setOnRefreshListener {
            LocationManager.crrLocation?.let {
                this.presenter?.fetchWeatherByLocation(it)
            }
            setSwipeRefresh(LocationManager.crrLocation != null)

        }

        switchFC.setOnCheckedChangeListener { _, isChecked ->
            this.presenter.setLabelUnitSwitch(isChecked)
            this.presenter.switchTemperatureUnitValue(isChecked)
        }

        containerWeather.setOnClickListener {
            this.presenter.intentHourly()
        }

    }

    override fun onStart() {
        super.onStart()
        this.locationManager?.startLocation()
    }

    override fun onStop() {
        super.onStop()
        this.locationManager?.stopLocation()
    }

    override fun switchFC(): Switch {
        return switchFC;
    }

    override fun textCityName(): TextView {
        return txtCityName;
    }

    override fun txtTemperature(): TextView {
        return txtTemperature;
    }

    override fun txtMoisture(): TextView {
        return txtMoisture;
    }

    override fun searchInput(): EditText {
        return searchInput
    }

    override fun swipeRefresh(): SwipeRefreshLayout {
        return swipeRefresh
    }

    override fun setSwipeRefresh(boolean: Boolean) {
        this.swipeRefresh().isRefreshing = boolean
    }

    override fun txtDateCurrent(): TextView {
        return txtDateCurrent
    }

    override fun intentHourly(weather: WeatherResponse, unit: String) {
        val intent = Intent(this, HourlyActivity::class.java)
        intent.putExtra("WeatherResponse", weather)
        intent.putExtra("unit", unit)
        startActivity(intent)
    }

    override fun txtDescription(): TextView {
        return txtDescription
    }

    override fun setIcClouds(iconPath: String) {
        Glide
            .with(this)
            .load(iconPath)
            .into(icClouds);
    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        this.locationManager?.onRequestPermissionsResult(requestCode, grantResults)
    }

    //TODO เมื่อ location มีการ update ใหม่ สั่งใฟ้ load ข้อมูลสภาพอากาศตาม location ปัจจุบัน
    override fun onLocationUpdate(location: Location) {
        this.presenter?.fetchWeatherByLocation(location)
    }


}
