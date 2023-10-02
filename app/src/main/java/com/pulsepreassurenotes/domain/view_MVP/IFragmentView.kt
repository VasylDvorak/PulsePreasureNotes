package com.pulsepreassurenotes.domain.view_MVP

import com.pulsepreassurenotes.model.Record
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface IFragmentView : MvpView {
    fun init()
    fun loadRecords(records: MutableList<Record>)
}
