package com.example.to_do.fragmets.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.to_do.R
import com.example.to_do.databinding.FragmentTaskBinding
import com.example.to_do.databinding.SettingsBinding

class Settings_Fragment : Fragment() {
    lateinit var  binding : SettingsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = SettingsBinding.inflate(layoutInflater,container,false)
        return  binding.root
    }
}