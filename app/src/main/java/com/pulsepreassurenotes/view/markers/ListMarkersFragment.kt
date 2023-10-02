package com.pulsepreassurenotes.view.markers

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.model.MarkerOptions
import com.pulsepreassurenotes.domain.presenters.ListMarkersPresenter
import com.pulsepreassurenotes.model.Record
import com.pulsepreassurenotes.view.BaseFragment
import com.pulsepreassurenotes.view.viewById
import com.pulsepreasurenotes.R
import com.pulsepreasurenotes.databinding.FragmentListMarkersBinding
import moxy.ktx.moxyPresenter

class ListMarkersFragment : BaseFragment<FragmentListMarkersBinding>(
    FragmentListMarkersBinding::inflate
) {

    val presenter: ListMarkersPresenter by moxyPresenter { ListMarkersPresenter() }
    private val listMarkersRecyclerview by viewById<RecyclerView>(R.id.list_markers_recyclerview)

    private val adapter: ListMarkersAdapter by lazy {
        ListMarkersAdapter(
            ::onCorrectionClick,
            ::onRemove
        )
    }

    private fun onCorrectionClick(i: Int, marker: Record) {
        presenter.onCorrectionClick(i, marker)
    }

    private fun onRemove(i: Int, marker: Record) {
        setDataToAdapter(presenter.onRemove(i))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addRecord.setOnClickListener {
            setDataToAdapter(presenter.addNote())
        }
    }

    override fun init() {
        listMarkersRecyclerview.adapter = adapter
        ItemTouchHelper(ItemTouchHelperCallback(adapter)).attachToRecyclerView(
            listMarkersRecyclerview
        )
        presenter.loadMarkers()
    }

    override fun loadMarkers(markers: MutableList<Record>) {
        setDataToAdapter(markers)
    }


    private fun setDataToAdapter(data: MutableList<Record>) {
        adapter.setData(data)
    }

    override fun onPause() {
        presenter.saveListMarkers()
        super.onPause()
    }

    companion object {
        fun newInstance() = ListMarkersFragment()
    }
}
