package com.example.skinscanner.ui.history

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinscanner.utils.Permissions
import com.example.skinscanner.R
import com.example.skinscanner.model.skin.SkinRecordListAdapter

class HistoryFragment : Fragment() {

    private lateinit var historyViewModel: HistoryViewModel
    private lateinit var root: View
    private lateinit var adapter: SkinRecordListAdapter

    private val requiredPermissions = arrayOf(
        "android.permission.CAMERA",
        "android.permission.WRITE_EXTERNAL_STORAGE",
        "android.permission.READ_EXTERNAL_STORAGE"
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_history, container, false)

        Permissions.requestPermissions(requiredPermissions, activity!!, root.context)
        configView()

        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)
        historyViewModel.skinRecordsLiveData.observe(viewLifecycleOwner, Observer {
                skinRecords -> skinRecords?.let { adapter.setSkinRecords(it) }
        })

        return root
    }

    private fun configView() {
        val recyclerView = root.findViewById<RecyclerView>(R.id.history_rv)
        adapter = SkinRecordListAdapter(root.context)
        recyclerView.adapter = adapter

        if(activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.layoutManager = LinearLayoutManager(root.context)
        else
            recyclerView.layoutManager = GridLayoutManager(root.context, 2)
    }
}
