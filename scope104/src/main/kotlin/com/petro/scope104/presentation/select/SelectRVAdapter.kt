package com.petro.scope104.presentation.select

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.petro.scope104.databinding.ItemSelectBinding
import com.petro.scope104.presentation.select.SelectRVAdapter.SelectViewHolder

class SelectRVAdapter :
    ListAdapter<SelectItem, SelectViewHolder>(object : DiffUtil.ItemCallback<SelectItem>() {
        override fun areItemsTheSame(oldItem: SelectItem, newItem: SelectItem): Boolean {
            return oldItem.title == newItem.title
        }

        override fun areContentsTheSame(oldItem: SelectItem, newItem: SelectItem): Boolean {
            return oldItem.title == newItem.title && oldItem.value == newItem.value
        }
    }) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SelectViewHolder {
        return SelectViewHolder(
            ItemSelectBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SelectViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class SelectViewHolder(private val binding: ItemSelectBinding) : RecyclerView.ViewHolder(
        binding.root
    ) {
        private var isChangingCheckedState = false
        fun bind(selectItem: SelectItem) {
            binding.root.text = selectItem.title
            isChangingCheckedState = true
            // Do not notify the CheckedChangeListener here
            binding.root.isChecked = selectItem.value
            isChangingCheckedState = false
            binding.root.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
                if (isChangingCheckedState) return@setOnCheckedChangeListener
                selectItem.value = isChecked
                itemView.post { notifyItemChanged(adapterPosition) }
            }
        }
    }
}