package com.mlx.neteasy.cutsomView

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.LayerDrawable
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.core.animation.doOnEnd
import androidx.core.view.get
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.SCROLL_STATE_DRAGGING
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.mlx.neteasy.R
import com.mlx.neteasy.ViewPagerAdapter
import jp.wasabeef.glide.transformations.BlurTransformation

/**
 * Project:NetEasy
 * Created by MLX on 2020/7/25.
 */
class PlayView(context: Context, attrs: AttributeSet) : RelativeLayout(context, attrs) {


    private lateinit var viewpager: ViewPager

    private lateinit var play: ImageView


    var backGroundAnimator: ValueAnimator = ValueAnimator.ofInt(0, 225)

    var bgDrawable: LayerDrawable
    var rotateAnimator: ObjectAnimator? = null

    private var currentImg: ImageView? = null
    private var isPlay = false
    private var centerX = 0f
    private var centerY = 0f
    private var radius = 0f

    init {
        val drawable1 = context.getDrawable(R.drawable.ic_blackground)
        val drawables = arrayOf(drawable1, drawable1)
        bgDrawable = LayerDrawable(drawables)
        background = bgDrawable
        backGroundAnimator.duration = 1000
        backGroundAnimator.interpolator = LinearInterpolator()
        backGroundAnimator.addUpdateListener {
            bgDrawable.getDrawable(1).alpha = it.animatedValue as Int
            background = bgDrawable
        }
        backGroundAnimator.doOnEnd {
            bgDrawable.setDrawable(0, bgDrawable.getDrawable(1))
        }

    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        viewpager = findViewById(R.id.vp)
        play = findViewById(R.id.play)
        play.setOnClickListener {
            it as ImageView
            isPlay = !isPlay
            if (isPlay) {
                it.setImageResource(R.drawable.ic_pause)
                rotateAnimator?.let {
                    if (it.isPaused) {
                        it.resume()
                        return@setOnClickListener
                    }
                }
                rotateAnimator = ObjectAnimator.ofFloat(currentImg, View.ROTATION, 0f, 360f)
                rotateAnimator?.duration = 6000
                rotateAnimator?.repeatCount = -1
                rotateAnimator?.interpolator = LinearInterpolator()
                rotateAnimator?.start()

            } else {
                it.setImageResource(R.drawable.ic_play)
                rotateAnimator?.pause()
            }
        }
        viewpager.pivotX = (viewpager.left + viewpager.width / 2).toFloat()
        viewpager.pivotY = (viewpager.top + viewpager.height / 2).toFloat()
        invalidate()
    }

    fun setAdapter(adapter: ViewPagerAdapter) {
        viewpager.adapter = adapter
        viewpager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
                if (state == SCROLL_STATE_DRAGGING) {
                    rotateAnimator?.let {
                        if (it.isRunning) {
                            it.end()
                        }
                    }
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {

            }

            override fun onPageSelected(position: Int) {
                val view =
                    adapter.Views[position].findViewById<ImageView>(R.id.music_avatar)
                currentImg = view
                isPlay = false
                play.setImageResource(R.drawable.ic_play)
                replaceBg(view.tag as Int)

            }

        })
        val view = viewpager[viewpager.currentItem]
        val img = view.findViewById<ImageView>(R.id.music_avatar)
        currentImg = img
        centerX = (viewpager.left + img.left + img.width / 2).toFloat()
        centerY = (viewpager.top + img.top + img.height / 2).toFloat()
        radius = (img.width / 2).toFloat()
        replaceBg(img.tag as Int)
    }


    fun replaceBg(res: Int) {
        Glide.with(context)
            .load(res)
            .transform(BlurTransformation(10, 25))
            .into(object : SimpleTarget<Drawable>() {
                override fun onResourceReady(
                    resource: Drawable,
                    transition: Transition<in Drawable>?
                ) {
                    bgDrawable.setDrawable(1, resource)
                    backGroundAnimator.start()
                }

            })
    }


}