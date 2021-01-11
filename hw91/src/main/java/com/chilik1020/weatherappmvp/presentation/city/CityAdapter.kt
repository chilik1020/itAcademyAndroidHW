package com.chilik1020.weatherappmvp.presentation.city

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chilik1020.weatherappmvp.databinding.ItemCityBinding
import com.chilik1020.weatherappmvp.presentation.models.CityUiModel

class CityAdapter(private val listener: OnCityItemClickListener) :
    RecyclerView.Adapter<CityAdapter.CityViewHolder>() {

    private val list = mutableListOf<CityUiModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater, parent, false)
        return CityViewHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        holder.bind(list[position])
    }

    override fun getItemCount() = list.size

    fun setData(data: List<CityUiModel>) {
        list.clear()
        list.addAll(data)
        notifyDataSetChanged()
    }

    class CityViewHolder(
        private val binding: ItemCityBinding,
        private val listener: OnCityItemClickListener
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(city: CityUiModel) {
            binding.tvCityName.text = city.name
            binding.ivIsActiveCity.visibility = if (city.isCurrentCity) {
                View.VISIBLE
            } else {
                View.GONE
            }

            binding.root.setOnClickListener { listener.onClick(city) }
        }
    }
}