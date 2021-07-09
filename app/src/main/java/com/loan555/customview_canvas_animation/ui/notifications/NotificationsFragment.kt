package com.loan555.customview_canvas_animation.ui.notifications

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.loan555.customview_canvas_animation.AppPreferences
import com.loan555.customview_canvas_animation.AppViewModel
import com.loan555.customview_canvas_animation.databinding.FragmentNotificationsBinding
import java.lang.Exception

class NotificationsFragment : Fragment() {

    private lateinit var viewModel: AppViewModel
    private var _binding: FragmentNotificationsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.let {
            ViewModelProviders.of(it).get(AppViewModel::class.java)
        } ?: throw Exception("Activity is null")

        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        binding.lockCustomView.password.observe(viewLifecycleOwner, {
            if (it != "") {
                if (viewModel.checkPass(it)) {
                    Toast.makeText(this.context, "Đặt mật khẩu thành công", Toast.LENGTH_SHORT)
                        .show()
                    AppPreferences.init(this.requireContext())
                    AppPreferences.myPassword = it
                    viewModel.currentPage(2)
                    viewModel.setPass("")
                } else {
                    binding.message.setTextColor(Color.RED)
                    binding.message.text = "Vui lòng nhập đúng mật khẩu mới"
                }
            }
        })

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}