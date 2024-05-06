package com.arpi.rplauncher

import android.content.ComponentName
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.leanback.app.VerticalGridFragment
import androidx.leanback.widget.ArrayObjectAdapter
import androidx.leanback.widget.OnItemViewClickedListener
import androidx.leanback.widget.VerticalGridPresenter
import java.util.ArrayList


class GridFragment : VerticalGridFragment() {
    private val mAdapter = ArrayObjectAdapter(
            IconPresenter())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        adapter = mAdapter
        val gridPresenter = VerticalGridPresenter()
        gridPresenter.numberOfColumns = NUM_COLUMNS
        setGridPresenter(gridPresenter)
        onItemViewClickedListener = OnItemViewClickedListener { viewHolder, item, _, _ ->
            val info = item as AppInfo
            val intent = Intent()
            intent.component = info.componentName
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED)
            viewHolder.view.context.startActivity(intent)
        }
        setOnItemViewSelectedListener{ _, item, _, _ ->
            val info = item as AppInfo
            selectedComponent = info.componentName
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        view!!.setBackgroundColor(Color.BLACK)
    }

    fun updateApps(apps: ArrayList<AppInfo>) {
        mAdapter.clear()
        for (i in apps.indices) {
            mAdapter.add(apps[i])
        }
        mAdapter.notifyItemRangeChanged(0, apps.size)
    }

    companion object {
        private val NUM_COLUMNS = 5
        public var selectedComponent : ComponentName = ComponentName("","")
    }
}
