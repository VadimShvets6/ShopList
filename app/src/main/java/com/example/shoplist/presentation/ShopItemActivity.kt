package com.example.shoplist.presentation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.shoplist.R
import com.example.shoplist.domain.ShopItem
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class ShopItemActivity : AppCompatActivity() {

//    private lateinit var textInputLayoutName: TextInputLayout
//    private lateinit var textInputLayoutCount: TextInputLayout
//    private lateinit var editTextName: TextInputEditText
//    private lateinit var editTextCount: TextInputEditText
//    private lateinit var materialButtonSave: MaterialButton
//
//    private lateinit var viewModel: ShopItemViewModel
//
    private var screenMode = MODE_UNKNOWN
    private var shopItemId = ShopItem.UNDEFINED_ID

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shop_item)
        parseIntent()
//        viewModel = ViewModelProvider(this)[ShopItemViewModel::class.java]
//        initViews()
//        addTextChangeListeners()
        launchRightMode()
//        observeViewModel()
    }
//
//    private fun observeViewModel() {
//        viewModel.errorInputCount.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_count)
//            } else {
//                null
//            }
//            textInputLayoutCount.error = message
//        }
//        viewModel.errorInputName.observe(this) {
//            val message = if (it) {
//                getString(R.string.error_input_name)
//            } else {
//                null
//            }
//            textInputLayoutName.error = message
//        }
//        viewModel.finishActivity.observe(this) {
//            finish()
//        }
//    }
//
    private fun launchRightMode() {
        val fragment = when (screenMode) {
            MODE_EDIT -> ShopItemFragment.newInstanceEditItem(shopItemId)
            MODE_ADD -> ShopItemFragment.newInstanceAddItem()
            else -> throw RuntimeException("Unknown screen mode $screenMode")
        }
    supportFragmentManager.beginTransaction()
        .add(R.id.fragmentShopItemContainer, fragment)
        .commit()
    }
//
//    private fun launchEditMode() {
//        viewModel.getShopItemId(shopItemId)
//        viewModel.shopItem.observe(this) {
//            editTextName.setText(it.name)
//            editTextCount.setText(it.count.toString())
//        }
//        materialButtonSave.setOnClickListener {
//            viewModel.editShopItem(editTextName.text?.toString(), editTextCount.text?.toString())
//        }
//    }
//
//    private fun launchAddMode() {
//        materialButtonSave.setOnClickListener {
//            viewModel.insertShopItem(editTextName.text?.toString(), editTextCount.text?.toString())
//        }
//    }
//
    private fun parseIntent() {
        if (!intent.hasExtra(EXTRA_SCREEN_MODE)) {
            throw RuntimeException("Param SCREEN MODE is absent")
        }
        val mode = intent.getStringExtra(EXTRA_SCREEN_MODE)
        if (mode != MODE_EDIT && mode != MODE_ADD) {
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode.toString()
        if (screenMode == MODE_EDIT) {
            if (!intent.hasExtra(EXTRA_SHOP_ITEM_ID)) {
                throw RuntimeException("Param SHOP ITEM ID is absent")
            }
            shopItemId = intent.getIntExtra(EXTRA_SHOP_ITEM_ID, -1)
        }
    }

//    private fun initViews() {
//        textInputLayoutName = findViewById(R.id.textInputLayoutName)
//        textInputLayoutCount = findViewById(R.id.textInputLayoutCount)
//        editTextName = findViewById(R.id.editTextName)
//        editTextCount = findViewById(R.id.editTextCount)
//        materialButtonSave = findViewById(R.id.materialButtonSave)
//    }
//
//    private fun addTextChangeListeners() {
//        editTextName.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputName()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//
//        editTextCount.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//                viewModel.resetErrorInputCount()
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//            }
//        })
//    }

    companion object {
        private const val EXTRA_SCREEN_MODE = "extra_mode"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val EXTRA_SHOP_ITEM_ID = "extra_shop_item_id"
        private const val MODE_UNKNOWN = ""

        fun newIntentAddItem(context: Context): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_ADD)
            return intent
        }

        fun newIntentEditItem(context: Context, shopItemId: Int): Intent {
            val intent = Intent(context, ShopItemActivity::class.java)
            intent.putExtra(EXTRA_SCREEN_MODE, MODE_EDIT)
            intent.putExtra(EXTRA_SHOP_ITEM_ID, shopItemId)
            return intent
        }
    }
}