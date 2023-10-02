package com.pulsepreassurenotes.view.list_records

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.pulsepreassurenotes.domain.presenters.ListRecordsPresenter
import com.pulsepreassurenotes.model.Record
import com.pulsepreassurenotes.view.BaseFragment
import com.pulsepreassurenotes.view.viewById
import com.pulsepreasurenotes.R
import com.pulsepreasurenotes.databinding.FragmentListBinding
import moxy.ktx.moxyPresenter

class ListFragment : BaseFragment<FragmentListBinding>(
    FragmentListBinding::inflate
) {

    val presenter: ListRecordsPresenter by moxyPresenter { ListRecordsPresenter() }
    private val listMarkersRecyclerview by viewById<RecyclerView>(R.id.list_markers_recyclerview)

    private val adapter: ListAdapter by lazy {
        ListAdapter(
            ::onCorrectionClick,
            ::onRemove
        )
    }

    private fun onCorrectionClick(i: Int, marker: Record) {
        presenter.onCorrectionClick(i, marker)
    }

    private fun onRemove(i: Int, record: Record) {
        setDataToAdapter(presenter.onRemove(i))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.loadMarkers()
        binding.addRecord.setOnClickListener {
            setDataToAdapter(presenter.addRecord())
        }
    }

    override fun init() {
        listMarkersRecyclerview.adapter = adapter
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(
            listMarkersRecyclerview
        )
    }

    override fun loadRecords(records: MutableList<Record>) {
        setDataToAdapter(records)
    }


    private fun setDataToAdapter(data: MutableList<Record>) {
        adapter.setData(data)
    }

    override fun onDestroy() {
        presenter.saveListRecords()
        super.onDestroy()
    }
    companion object {
        fun newInstance() = ListFragment()
    }
}
