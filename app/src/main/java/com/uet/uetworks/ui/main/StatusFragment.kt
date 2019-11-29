package com.uet.uetworks.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.uet.uetworks.R
import com.uet.uetworks.adapter.StatusAdapter
import com.uet.uetworks.model.Company
import kotlinx.android.synthetic.main.fragment_status.*

class StatusFragment : Fragment() {
    private lateinit var adapter: StatusAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_status, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val comp = ArrayList<Company>()
        comp.add(Company("Công ty TNHH UUU", "WAIT"))
        comp.add(Company("Công ty TNHH EEE", "SELECTED"))
        comp.add(Company("Công ty TNHH TTT", "WAIT"))
        adapter = StatusAdapter(comp)
        recyclerStatusFragment.adapter = adapter
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            StatusFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}
