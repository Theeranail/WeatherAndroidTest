package com.example.weatherandroidtest.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.weatherandroidtest.R
import com.example.weatherandroidtest.adapter.viewholder.HourlyViewHolder
import com.example.weatherandroidtest.models.Daily
import com.example.weatherandroidtest.models.Hourly

class HourlyAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private var dataList: ArrayList<Hourly>;
    private var context: Context;

    constructor(dataList: ArrayList<Hourly> = ArrayList(), context: Context) {
        this.dataList = dataList;
        this.context = context;
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return HourlyViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.content_item_list_hourly,
                parent,
                false
            )
            , context
        );
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is HourlyViewHolder) {
            var itemViewHolder: HourlyViewHolder = holder;
            var daily: Hourly = dataList[position];
            itemViewHolder.setView(daily);
        }
    }
}