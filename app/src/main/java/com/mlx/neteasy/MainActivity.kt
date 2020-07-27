package com.mlx.neteasy

import android.graphics.drawable.Drawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomViewTarget
import com.bumptech.glide.request.target.ImageViewTarget
import com.mlx.neteasy.databinding.ActivityMain2Binding
import com.mlx.neteasy.databinding.ActivityMainBinding
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    var drawables = arrayOf(R.drawable.ic_music1, R.drawable.ic_music2, R.drawable.ic_music3)
    var adapter: ViewPagerAdapter? = null
    var views = mutableListOf<View>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        lifecycleScope.launch(Dispatchers.Main) {
            createDatas()
            adapter= ViewPagerAdapter()
            adapter?.setData(views)
            mainBinding.rootLayout.setAdapter(adapter!!)
        }
    }

    private suspend fun createDatas() {
        withContext(Dispatchers.IO) {
            drawables.forEach {
                var view = LayoutInflater.from(this@MainActivity)
                    .inflate(R.layout.layout_disc, null, false)
                val img = view.findViewById<ImageView>(R.id.music_avatar)
                Glide.with(this@MainActivity)
                    .load(it)
                    .circleCrop()
                    .into(object : ImageViewTarget<Drawable>(img) {
                        override fun setResource(resource: Drawable?) {
                            img.tag = it
                            img.setImageDrawable(resource)
                            views.add(view)
                        }
                    })
            }
        }
    }


}