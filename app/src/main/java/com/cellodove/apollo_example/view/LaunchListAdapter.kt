package com.cellodove.apollo_example.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.cellodove.apollo_example.LaunchListQuery
import com.cellodove.apollo_example.databinding.LaunchItemBinding

class LaunchListAdapter(
    private val launches: List<LaunchListQuery.Launch>
) : RecyclerView.Adapter<LaunchListAdapter.ViewHolder>() {

    class ViewHolder(val binding: LaunchItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = LaunchItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return launches.size
    }

    var onEndOfListReached: (() -> Unit)? = null
    var onItemClicked: ((LaunchListQuery.Launch) -> Unit)? = null

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val launch = launches[position]
        holder.binding.site.text = launch.site ?: ""
        holder.binding.missionName.text = launch.mission?.name
        Glide.with(holder.itemView.context).load(launch.mission?.missionPatch).into(holder.binding.missionPatch)

        if (position == launches.size - 1){
            onEndOfListReached?.invoke()
        }

        holder.binding.root.setOnClickListener {
            onItemClicked?.invoke(launch)
        }
    }
}