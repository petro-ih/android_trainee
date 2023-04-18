package com.petro.scope104.presentation.select

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.petro.scope104.databinding.FragmentSelectBinding

class SelectBottomSheetFragment : BottomSheetDialogFragment() {
    interface OnItemSelected {
        fun onItemSelected(requestCode: Int, items: List<SelectItem>)
    }

    private var binding: FragmentSelectBinding? = null
    private val adapter = SelectRVAdapter()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSelectBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding!!.title.text = requireArguments().getString(KEY_TITLE)
        assert(arguments != null)
        val stringArrayList = requireArguments().getSerializable(KEY_ITEMS) as List<SelectItem>
        binding!!.recyclerView.adapter = adapter
        adapter.submitList(stringArrayList)
        binding!!.cta.setOnClickListener { v: View? ->
            if (activity is OnItemSelected) {
                (activity as OnItemSelected?)!!.onItemSelected(
                    requireArguments().getInt(KEY_REQUEST_CODE),
                    adapter.currentList
                )
                dismiss()
            }
        }
    }

    companion object {
        private const val KEY_TITLE = "KEY_TITLE"
        private const val KEY_ITEMS = "KEY_ITEMS"
        private const val KEY_REQUEST_CODE = "KEY_REQUEST_CODE"
        fun newInstance(
            requestCode: Int,
            title: String?,
            items: List<SelectItem>?
        ): SelectBottomSheetFragment {
            val args = Bundle()
            args.putString(KEY_TITLE, title)
            args.putInt(KEY_REQUEST_CODE, requestCode)
            args.putSerializable(KEY_ITEMS, ArrayList(items))
            val fragment = SelectBottomSheetFragment()
            fragment.arguments = args
            return fragment
        }
    }
}