package com.rocketbank.rockettest

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_size_choose.*

class SizeChooseFragment : DialogFragment() {

    companion object {

        const val TAG = "SizeChooseFragment"

    }

    private var mainViewModel: MainViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activity?.let { fragmentActivity ->
            mainViewModel = ViewModelProviders.of(fragmentActivity).get(MainViewModel::class.java)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_size_choose, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel?.size?.apply {
            editRows.setText(rows.toString())
            editColumns.setText(columns.toString())
        }

        buttonCancel.setOnClickListener {
            activity?.onBackPressed()
        }

        buttonOk.setOnClickListener {
            mainViewModel?.setSize(editRows.text.toString(), editColumns.text.toString())
            activity?.onBackPressed()
        }
    }

}