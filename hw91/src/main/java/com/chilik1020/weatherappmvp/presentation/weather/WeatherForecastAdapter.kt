package com.chilik1020.weatherappmvp.presentation.weather

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chilik1020.weatherappmvp.data.entities.WeatherAtTimeStamp
import com.chilik1020.weatherappmvp.databinding.ItemWeatherForecastBinding
import com.chilik1020.weatherappmvp.utils.ICON_BASE_URL
import com.chilik1020.weatherappmvp.utils.timeFromStamp

class WeatherForecastAdapter :
    RecyclerView.Adapter<WeatherForecastAdapter.WeatherAtTimeStampViewHolder>() {

    private val list = mutableListOf<WeatherAtTimeStamp>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WeatherAtTimeStampViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val itemBinding = ItemWeatherForecastBinding.inflate(inflater, parent, false)
        return WeatherAtTimeStampViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: WeatherAtTimeStampViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun setData(data: List<WeatherAtTimeStamp>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    class WeatherAtTimeStampViewHolder(private val binding: ItemWeatherForecastBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: WeatherAtTimeStamp) {
            with(binding) {
                Glide.with(root)
                    .load(ICON_BASE_URL.format(data.weatherList[0].icon))
                    .into(ivWeatherIcon)

                tvTimeStamp.text = data.dt.timeFromStamp()
                tvTemperature.text = data.temp
                tvWeatherDescription.text = data.weatherList[0].description
            }
        }
    }
}