package com.vishnu.routesapp.ui.routes

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.viewbinding.ViewBinding
import com.github.nitrico.lastadapter.Holder
import com.github.nitrico.lastadapter.ItemType
import com.github.nitrico.lastadapter.LastAdapter
import com.vishnu.database.data.db.entities.TripData
import com.vishnu.routesapp.BR
import com.vishnu.routesapp.R
import com.vishnu.routesapp.databinding.ActivityRoutesBinding
import com.vishnu.routesapp.databinding.ItemRouteBinding
import com.vishnu.routesapp.ui.base.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoutesActivity:BaseActivity() {
    private val routesViewModel: RoutesViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


       val binding =  DataBindingUtil.setContentView<ActivityRoutesBinding>(this,R.layout.activity_routes)
        // Start polling routes API every 1 minute it will stop when activity is destroyed
        routesViewModel.fetchRoutesData()
        val type = object: ItemType<ItemRouteBinding>(R.layout.item_route){
            override fun onCreate(holder: Holder<ItemRouteBinding>) {
                super.onCreate(holder)
                holder.itemView.setOnClickListener { println(holder.binding.destinnation.text) }
            }
        }
        //Observe DB to get the neccessary UI data
        routesViewModel.observeRoutesDB().observe(this){
            routes ->
            LastAdapter(routes,BR.item).map(TripData::class.java,type).into(binding.rvRoutes)
        }


    }
}