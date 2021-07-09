package com.loan555.customview_canvas_animation.ui.home

import android.animation.*
import android.graphics.drawable.AnimatedImageDrawable
import android.graphics.drawable.AnimatedVectorDrawable
import android.graphics.drawable.AnimationDrawable
import android.os.Build
import android.os.Bundle
import android.transition.Transition
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.AnimationUtils
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.loan555.customview_canvas_animation.AppViewModel
import com.loan555.customview_canvas_animation.R
import com.loan555.customview_canvas_animation.databinding.FragmentHomeBinding
import java.lang.Exception

class HomeFragment : Fragment() {

    private lateinit var viewModel: AppViewModel
    private var _binding: FragmentHomeBinding? = null
    private lateinit var animationDrawable: AnimationDrawable

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var animation: Animation

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.let {
            ViewModelProviders.of(it).get(AppViewModel::class.java)
        } ?: throw Exception("Activity is null")

        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        binding.fadeIn.setOnClickListener {
            animation = AnimationUtils.loadAnimation(this.context, R.anim.fade_in)
            binding.img.startAnimation(animation)
        }
        binding.fadeOut.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.fade_out))
        }
        binding.blink.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.blink))
        }
        binding.zoomIn.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.zoom_in))
        }
        binding.zoomOut.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.zoom_out))
        }
        binding.rotate.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.rotate))
        }
        binding.move.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.move))
        }
        binding.slideUp.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.slide_up))
        }
        binding.slideIn.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.slide_in))
        }
        binding.slideDown.setOnClickListener {
            binding.img.startAnimation(
                AnimationUtils.loadAnimation(
                    this.context,
                    R.anim.slide_down
                )
            )
        }
        binding.slideOut.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.slide_out))
        }
        binding.sequential.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.squential))
        }
        binding.together.setOnClickListener {
            binding.img.startAnimation(AnimationUtils.loadAnimation(this.context, R.anim.together))
        }
        binding.valueAnimator.setOnClickListener {
            ValueAnimator.ofFloat(0f, 100f).apply {
                duration = 1000
                repeatCount = Animation.INFINITE
                start()
                addUpdateListener {
                    binding.img.translationX = it.animatedValue as Float
                }
            }
        }
        binding.objectAnimation.setOnClickListener {
            ObjectAnimator.ofFloat(binding.img, "translationX", -100f).apply {
                duration = 1000
                start()
            }
        }
        binding.animatorSet.setOnClickListener {
            val bouncer = AnimatorSet().apply {
                play(ObjectAnimator.ofFloat(binding.img, "translationX", 100f).apply {
                    duration = 1000
                }).before(ObjectAnimator.ofFloat(binding.img, "alpha", 1f, 0f).apply {
                    duration = 1000
                })
            }
            AnimatorSet().apply {
                play(bouncer).before(ObjectAnimator.ofFloat(binding.img, "alpha", 0f, 1f).apply {
                    duration = 1000
                })
                start()
            }
        }
        binding.animationLayoutChange.setOnClickListener {

            val animFadeOut = ObjectAnimator.ofFloat(binding.linearLayout, "scaleY", 1f, 0f).apply {
                duration = 1000
            }
            val animSlideUp =
                ObjectAnimator.ofFloat(binding.linearLayout, "scaleY", 0.0f, 1.0f).apply {
                    duration = 1000
                }
            AnimatorSet().apply {
                play(animFadeOut).before(animSlideUp)
                start()
                addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        binding.linearLayout.visibility = View.GONE
                    }
                })
            }
        }
        binding.img.apply {
            setBackgroundResource(R.drawable.rocket_image)
            animationDrawable = background as AnimationDrawable
        }
        binding.img.setOnClickListener { animationDrawable.start() }
        binding.vectorAnimation.apply {
            setBackgroundResource(R.drawable.animatorvectordrawable)
            val anim = background as AnimatedVectorDrawable
            setOnClickListener { anim.start() }
        }
        binding.resetPass.setOnClickListener {
            viewModel.currentPage(0)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}