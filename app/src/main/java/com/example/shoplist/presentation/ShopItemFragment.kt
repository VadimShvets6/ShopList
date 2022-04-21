package com.example.shoplist.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import java.lang.RuntimeException

class ShopItemFragment : Fragment() {

    private lateinit var textInputLayoutName: TextInputLayout
    private lateinit var textInputLayoutCount: TextInputLayout
    private lateinit var editTextName: TextInputEditText
    private lateinit var editTextCount: TextInputEditText
    private lateinit var materialButtonSave: MaterialButton

    private lateinit var viewModel: ShopItemViewModel

    private var screenMode : String = MODE_UNKNOWN
    private var shopItemId : Int = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_shop_item, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
        initViews(view)
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.errorInputCount.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_count)
            } else {
                null
            }
            textInputLayoutCount.error = message
        }
        viewModel.errorInputName.observe(viewLifecycleOwner) {
            val message = if (it) {
                getString(R.string.error_input_name)
            } else {
                null
            }
            textInputLayoutName.error = message
        }
        viewModel.finishActivity.observe(viewLifecycleOwner) {
            activity?.onBackPressed()
        }
    }

    private fun launchRightMode() {
        when (screenMode) {
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun launchEditMode() {
        viewModel.getShopItemId(shopItemId)
        viewModel.shopItem.observe(viewLifecycleOwner) {
            editTextName.setText(it.name)
            editTextCount.setText(it.count.toString())
        }
        materialButtonSave.setOnClickListener {
            viewModel.editShopItem(editTextName.text?.toString(), editTextCount.text?.toString())
        }
    }

    private fun launchAddMode() {
        materialButtonSave.setOnClickListener {
            viewModel.insertShopItem(editTextName.text?.toString(), editTextCount.text?.toString())
        }
    }

    private fun parseParams() {
        val args = requireArguments()
        if (!args.containsKey(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param SCREEN MODE is absent")
        }
        val mode = args.getString(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode.toString()
        if (screenMode == MODE_EDIT) {
            if (!args.containsKey(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param SHOP ITEM ID is absent")
            }
            shopItemId = args.getInt(EXTRA_SHOP_ITEM_ID, -1)
        }
    }

    private fun initViews(view : View) {
        textInputLayoutName = view.findViewById(R.id.textInputLayoutName)
        textInputLayoutCount = view.findViewById(R.id.textInputLayoutCount)
        editTextName = view.findViewById(R.id.editTextName)
        editTextCount = view.findViewById(R.id.editTextCount)
        materialButtonSave = view.findViewById(R.id.materialButtonSave)
    }

    private fun addTextChangeListeners() {
        editTextName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputName()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        editTextCount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.resetErrorInputCount()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""

        fun newInstanceAddItem() : ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem(shopItemId : Int) : ShopItemFragment {
            return ShopItemFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_SCREEN_MODE, MODE_EDIT)
                    putInt(EXTRA_SHOP_ITEM_ID, shopItemId)
                }
            }
        }

    }
}