package com.loan555.customview_canvas_animation.ui.login

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.loan555.customview_canvas_animation.AppPreferences
import com.loan555.customview_canvas_animation.AppViewModel
import com.loan555.customview_canvas_animation.databinding.FragmentLoginBinding
import java.lang.Exception

class LoginFragment : Fragment() {

    private lateinit var viewModel: AppViewModel
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = activity?.let {
            ViewModelProviders.of(it).get(AppViewModel::class.java)
        } ?: throw Exception("Activity is null")
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        binding.lockCustomView.password.observe(viewLifecycleOwner, {
            if (it != "") {
                if (checkMyPass(it)) {
                    Toast.makeText(this.context, "Đăng nhập thành công", Toast.LENGTH_SHORT).show()
                    viewModel.currentPage(3)
                }else{
                    binding.message.setTextColor(Color.RED)
                    binding.message.text = "Mật khẩu không đúng"
                }
            }
        })

        return binding.root
    }

    private fun checkMyPass(pass: String?): Boolean {
        AppPreferences.init(this.requireContext())
        if (pass == AppPreferences.myPassword) {
            return true
        }
        return false
    }
}