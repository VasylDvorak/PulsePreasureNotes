package com.pulsepreassurenotes.view.list_records

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.pulsepreassurenotes.model.Record

import com.pulsepreasurenotes.R
import com.pulsepreasurenotes.databinding.RecyclerviewItemBinding

class ListAdapter(
    private var correctClickListener: (Int, Record) -> Unit,
    private var callbackRemove: (Int, Record) -> Unit
) : RecyclerView.Adapter<ListAdapter.RecyclerItemViewHolder>(), ItemTouchHelperAdapter {

    private var data: MutableList<Record> = mutableListOf()
    fun setData(data: MutableList<Record>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerItemViewHolder {
        val binding =
            RecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return RecyclerItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RecyclerItemViewHolder, position: Int) {
        holder.bind(data.get(position))
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class RecyclerItemViewHolder(val binding: RecyclerviewItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun onItemSelected() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context, R.color.white
                )
            )
        }

        fun onItemRelease() {
            itemView.setBackgroundColor(
                ContextCompat.getColor(
                    itemView.context, R.color.white
                )
            )
        }

        fun bind(data: Record) {
            if (layoutPosition != RecyclerView.NO_POSITION) {

                itemView.apply {
                    binding.apply {
                        if (layoutPosition % 2 == 0) {
                            layout.setBackgroundColor(
                                ContextCompat.getColor(
                                    itemView.context,
                                    R.color.yellow
                                )
                            )
                        }
                        cardView.setCardBackgroundColor(resources.getColor(R.color.white))
                        hightPressure.setText(data.hightPressue.toString())
                        lowPressure.setText(data.lowPressure.toString())
                        pulse.setText(data.pulse.toString())
                        val hoursPrefix = if (data.hours!! <= 9) "0" else ""
                        val minutesPrefix = if (data.minutes!! <= 9) "0" else ""
                        time.text = hoursPrefix + data.hours.toString() +
                                ":" + minutesPrefix + data.minutes.toString()
                        date.text =
                            data.date.toString() + " " + data.month + " " + data.year.toString()
                        approve.visibility = View.GONE
                        hightPressure.inputType = InputType.TYPE_NULL
                        lowPressure.inputType = InputType.TYPE_NULL
                        pulse.inputType = InputType.TYPE_NULL
                        hightPressure.setOnLongClickListener {
                            editTitleAnnotation(
                                hightPressure,
                                approve,
                                layoutPosition,
                                "hightPressure"
                            )
                            true
                        }
                        lowPressure.setOnLongClickListener {
                            editTitleAnnotation(
                                lowPressure,
                                approve,
                                layoutPosition,
                                "lowPressure"
                            )
                            true
                        }
                        pulse.setOnLongClickListener {
                            editTitleAnnotation(
                                pulse,
                                approve,
                                layoutPosition,
                                "pulse"
                            )
                            true
                        }
                    }
                }
            }
        }
    }

    private fun editTitleAnnotation(
        editTextView: AppCompatEditText,
        apply: ImageView,
        layoutPosition: Int,
        titleCorrect: String
    ) {
        val editData = data[layoutPosition]
        editTextView.inputType = InputType.TYPE_CLASS_PHONE or InputType.TYPE_NUMBER_FLAG_DECIMAL
        apply.visibility = View.VISIBLE
        var newText = editTextView.text.toString()

        editTextView.apply {
            addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(
                    charSequence: CharSequence,
                    i: Int,
                    i1: Int,
                    i2: Int
                ) {
                }

                override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                    newText = charSequence.toString()
                }

                override fun afterTextChanged(editable: Editable) {}
            })
            apply.setOnClickListener {

                newText = newText.replace("[^\\d]".toRegex(), "")
                if (newText == "") {
                    newText = "0"
                }
                newText.toInt().toString()
                editTextView.setText(newText)

                editTextView.hideKeyboard()
                apply.visibility = View.GONE
                editTextView.inputType = InputType.TYPE_NULL
                changeRecordItem(layoutPosition, data[layoutPosition])

                when (titleCorrect) {
                    "hightPressure" -> {
                        editData.hightPressue = newText.toInt()
                        data[layoutPosition] = editData
                    }

                    "lowPressure" -> {
                        editData.lowPressure = newText.toInt()
                        data[layoutPosition] = editData
                    }

                    "pulse" -> {
                        editData.pulse = newText.toInt()
                        data[layoutPosition] = editData
                    }

                }
            }
        }
    }

    // Расширяем функционал вью для скрытия клавиатуры
    fun View.hideKeyboard(): Boolean {
        try {
            val inputMethodManager =
                context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            return inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
        } catch (ignored: RuntimeException) {
        }
        return false
    }

    private fun changeRecordItem(layoutPosition: Int, listItemData: Record) {
        correctClickListener(layoutPosition, listItemData)
    }

    override fun onItemDelete(position: Int) {
        callbackRemove(position, data.get(position))
    }

}
