package com.uet.uetworks.mainFragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uet.uetworks.R

class MessageFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MessageFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
