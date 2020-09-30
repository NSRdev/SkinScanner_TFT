package com.example.skinscanner.ui.dermatologists

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.skinscanner.R
import com.example.skinscanner.model.dermatologist.DermatologistRecordListAdapter
import com.example.skinscanner.utils.*
import kotlinx.android.synthetic.main.fragment_dermatologists.*
import java.text.SimpleDateFormat
import java.util.*


class DermatologistsFragment : Fragment() {

    private lateinit var root: View
    private lateinit var dermatologistsViewModel: DermatologistsViewModel
    private lateinit var adapter: DermatologistRecordListAdapter
    private lateinit var latestUpdate: AppCompatTextView
    private lateinit var progressBar: ContentLoadingProgressBar
    private lateinit var sharedPreferences: MySharedPreferences
    private lateinit var alertMessages: AlertMessages

    private val requiredPermissions = arrayOf(
        "android.permission.ACCESS_FINE_LOCATION",
        "android.permission.ACCESS_COARSE_LOCATION"
    )

    override fun onCreateView(inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        root = inflater.inflate(R.layout.fragment_dermatologists, container, false)
        alertMessages = AlertMessages(root.context)

        Permissions.requestPermissions(requiredPermissions, activity!!, root.context)
        sharedPreferences = MySharedPreferences(root.context)

        dermatologistsViewModel =
            ViewModelProvider(this).get(DermatologistsViewModel::class.java)
        dermatologistsViewModel.dermatologistRecordsLiveData.observe(viewLifecycleOwner, Observer {
                dermatologistRecords -> dermatologistRecords?.let { adapter.setDermatologistRecords(it) }
        })

        configView()
        updateView()
        return root
    }

    private fun configView() {
        latestUpdate = root.findViewById(R.id.latest_update)
        progressBar = root.findViewById(R.id.progress_bar)

        val recyclerView = root.findViewById<RecyclerView>(R.id.dermatologists_rv)
        adapter = DermatologistRecordListAdapter(root.context)
        recyclerView.adapter = adapter

        if(activity!!.resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT)
            recyclerView.layoutManager = LinearLayoutManager(root.context)
        else
            recyclerView.layoutManager = GridLayoutManager(root.context, 3)

        val reloadButton = root.findViewById<AppCompatImageButton>(R.id.reload_button)
        reloadButton.setOnClickListener {
            if (ConnectionDetector(root.context).verifyNetwork()) {
                val result = FindDermatologists(
                    root.context,
                    progressBar
                ).execute().get()
                if (result.isEmpty()) {
                    alertMessages.serverError()
                } else {
                    dermatologistsViewModel.removeAll()
                    dermatologistsViewModel.insertAll(result)

                    sharedPreferences.put("update_time", SimpleDateFormat("HH:mm - dd/MM/yy")
                            .format(Date()))

                    updateView()
                }
            } else {
                alertMessages.internetError()
            }
        }
    }

    private fun updateView() {
        latestUpdate.text = resources.getString(R.string.latest_update) + " " +
                sharedPreferences.get("update_time")
    }
}
