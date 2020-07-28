package com.mlx.neteasy

import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter

/**
 * Project:NetEasy
 * Created by MLX on 2020/7/25.
 */
class ViewPagerAdapter :PagerAdapter(){

    var Views= mutableListOf<View>()



    fun setData(datas: MutableList<View>){
        Views.clear()
        Views.addAll(datas)
        notifyDataSetChanged()
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = Views.get(position)
        container.addView(view)
        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(Views[position])
    }


    override fun isViewFromObject(view: View, `object`: Any)=`object`==view

    override fun getCount()=Views.size

}