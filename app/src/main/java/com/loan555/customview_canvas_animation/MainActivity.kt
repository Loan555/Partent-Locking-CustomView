package com.loan555.customview_canvas_animation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.lifecycle.ViewModelProvider
import com.loan555.customview_canvas_animation.ui.dashboard.DashboardFragment
import com.loan555.customview_canvas_animation.ui.home.HomeFragment
import com.loan555.customview_canvas_animation.ui.login.LoginFragment
import com.loan555.customview_canvas_animation.ui.notifications.NotificationsFragment

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: AppViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)
        AppPreferences.init(this)
        val myPassword = AppPreferences.myPassword
        if (myPassword != null) {
            viewModel.currentPage(2)
        }
        viewModel.page.observe(this, {
            when (it) {
                0 -> {
                    supportFragmentManager.commit {
                        setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.slide_out,
                            R.anim.fade_in
                        )
                        setReorderingAllowed(true)
                        replace<DashboardFragment>(R.id.viewFragment)
                        addToBackStack("setPass")
                    }
                }
                1 -> {
                    supportFragmentManager.commit {
                        setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.fade_out,
                            R.anim.slide_out,
                            R.anim.fade_in
                        )
                        setReorderingAllowed(true)
                        replace<NotificationsFragment>(R.id.viewFragment)
                        addToBackStack("commitPass")
                    }
                }
                2 -> {
                    supportFragmentManager.commit {
                        setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.slide_out
                        )
                        setReorderingAllowed(true)
                        replace<LoginFragment>(R.id.viewFragment)
                        addToBackStack("animationDemo")
                    }
                }
                3 -> {
                    supportFragmentManager.commit {
                        setCustomAnimations(
                            R.anim.slide_in,
                            R.anim.slide_out
                        )
                        setReorderingAllowed(true)
                        replace<HomeFragment>(R.id.viewFragment)
                        addToBackStack("animationDemo")
                    }
                }
            }
        })

        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        when (viewModel.page.value) {
            1 -> {
                supportFragmentManager.popBackStack()
            }
            2 -> {
                this.finish()
            }
            3 -> {
                this.finish()
            }
        }
    }
}