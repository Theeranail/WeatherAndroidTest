package com.example.weatherandroidtest.adapter.viewholder

import android.content.Context
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weatherandroidtest.R
import com.example.weatherandroidtest.models.Daily
import com.example.weatherandroidtest.models.Hourly
import kotlinx.android.synthetic.main.content_item_list_hourly.view.*

class HourlyViewHolder(itemView: View, context: Context) : RecyclerView.ViewHolder(itemView),
    HourlyViewHolderListener {

    private lateinit var presenter: HourlyViewHolderPresenter
    fun setView(hourly: Hourly) {
        presenter = HourlyViewHolderPresenter(this@HourlyViewHolder)
        presenter.initTime(hourly.dt)
        presenter.initTemp(hourly.temp)
        presenter.initRain(hourly.rain)
        presenter.initIcon(hourly.weathers[0].icon)
        presenter.initDescription(hourly.weathers[0])
    }

    override fun setTextTime(time: String) {
        this.itemView.apply {
            txtTime.text = time
        }
    }

    override fun setTextTemperature(temperature: String) {
        this.itemView.apply {
            txtTemperature.text = temperature
        }
    }

    override fun setTextRain(rain: String) {
        this.itemView.apply {
            txtRain.text = rain
        }
    }

    override fun setIcon(iconPath: String) {
        this.itemView.apply {
            Glide
                .with(context)
                .load(iconPath)
                .into(icRain);
        }
    }

    override fun setTextDescription(description: String) {
        this.itemView.apply {
            txtDescription.text = description
        }
    }
}