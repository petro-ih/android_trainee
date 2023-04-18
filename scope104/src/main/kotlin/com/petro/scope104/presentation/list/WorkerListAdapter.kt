package com.petro.scope104.presentation.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.petro.scope104.R
import com.petro.scope104.databinding.ItemWorkerBinding
import com.petro.scope104.databinding.ItemWorkerSquareBinding
import com.petro.scope104.domain.entity.WorkerEntity
import com.petro.scope104.presentation.list.WorkerListAdapter.AbstractViewHolder
import com.petro.scope104.util.DateFormatHelper
import java.util.*

class WorkerListAdapter(private val currentViewType: Int) :
    ListAdapter<WorkerEntity, AbstractViewHolder>(UserDiffCallBack()) {
    private var callback: OnClickListener? = null
    override fun getItemViewType(position: Int): Int {
        return currentViewType
    }

    fun setOnClickListener(callback: OnClickListener) {
        this.callback = callback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AbstractViewHolder {
        when (viewType) {
            VIEW_TYPE_NORMAL -> return WorkerViewHolder(parent, callback)
            VIEW_TYPE_SQUARE -> return WorkerGridViewHolder(parent, callback)
        }
        throw IllegalStateException()
    }

    override fun onBindViewHolder(holder: AbstractViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    interface OnClickListener {
        fun onClick(clickedItem: WorkerEntity, transitionView: List<View>)
    }

    private class UserDiffCallBack : DiffUtil.ItemCallback<WorkerEntity>() {
        override fun areItemsTheSame(oldItem: WorkerEntity, newItem: WorkerEntity): Boolean {
            return oldItem.username == newItem.username
        }

        override fun areContentsTheSame(oldItem: WorkerEntity, newItem: WorkerEntity): Boolean {
            return oldItem == newItem
        }
    }

    abstract class AbstractViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        abstract fun bind(item: WorkerEntity)
    }

    internal class WorkerViewHolder(
        private val binding: ItemWorkerBinding,
        private val onClickListener: OnClickListener?
    ) : AbstractViewHolder(
        binding.getRoot()
    ) {
        constructor(parent: ViewGroup, onClickListener: OnClickListener?) : this(
            ItemWorkerBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ), onClickListener
        )

        override fun bind(item: WorkerEntity) {
            Glide.with(binding.ivAVATAR).load(item.avatarUrl).centerCrop()
                .into(binding.ivAVATAR)
            binding.name.text = item.name
            binding.date.text = DateFormatHelper.formatDob(item.dob)
            binding.city.text = item.city
            binding.ivAVATAR.transitionName =
                binding.ivAVATAR.context.getString(R.string.avatarTransition, item.username)
            binding.name.transitionName =
                binding.name.context.getString(R.string.nameTransition, item.username)
            binding.root.setOnClickListener {
                onClickListener?.onClick(
                    item, listOf(
                        binding.ivAVATAR, binding.name
                    )
                )
            }
        }
    }

    internal class WorkerGridViewHolder(
        private val binding: ItemWorkerSquareBinding,
        private val onClickListener: OnClickListener?
    ) : AbstractViewHolder(
        binding.getRoot()
    ) {
        constructor(parent: ViewGroup, onClickListener: OnClickListener?) : this(
            ItemWorkerSquareBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            onClickListener
        )

        override fun bind(item: WorkerEntity) {
            Glide.with(binding.ivAVATAR).load(item.avatarUrlXXL).centerCrop()
                .into(binding.ivAVATAR)
            binding.name.text = item.name
            binding.ivAVATAR.transitionName =
                binding.ivAVATAR.context.getString(R.string.avatarTransition, item.username)
            binding.name.transitionName =
                binding.name.context.getString(R.string.nameTransition, item.username)
            binding.root.setOnClickListener { v: View? ->
                onClickListener?.onClick(
                    item, Arrays.asList(
                        binding.ivAVATAR, binding.name
                    )
                )
            }
        }
    }

    companion object {
        const val VIEW_TYPE_NORMAL = 1
        const val VIEW_TYPE_SQUARE = 2
    }
}