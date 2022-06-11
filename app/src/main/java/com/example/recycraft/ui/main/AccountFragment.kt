package com.example.recycraft.ui.main

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.recycraft.databinding.FragmentAccountBinding
import com.example.recycraft.databinding.FragmentHomeBinding
import com.example.recycraft.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_third_screen.*

class AccountFragment : Fragment() {

    private var _binding: FragmentAccountBinding? = null
    private val binding get() = _binding!!
    private lateinit var name: String
    private lateinit var username: String
    private lateinit var email: String
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAccountBinding.bind(view)

        preferences = requireActivity().getSharedPreferences(
            LoginActivity.SHARED_PREFERENCES,
            Context.MODE_PRIVATE
        )
        name = preferences.getString(LoginActivity.NAME, "").toString()
        username = preferences.getString(LoginActivity.USERNAME, "").toString()
        email = preferences.getString(LoginActivity.EMAIL, "").toString()

        binding.tvUsername.text = "$username"
        binding.tvFullname.text = "$name"
        binding.tvEmail.text = "$email"

        /*
        binding.btnLogout.setOnClickListener {
            preferences.edit().apply{
                clear()
                apply()
            }
            val backIntent = Intent(this@AccountFragment, MainActivity::class.java)
            startActivity(backIntent)

        }

         */

    }

}


