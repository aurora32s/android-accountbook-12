package com.seom.accountbook.ui.adapter

import android.animation.AnimatorInflater
import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.seom.accountbook.R
import com.seom.accountbook.databinding.ItemCategoryOptionBinding
import com.seom.accountbook.model.category.Category

class CategorySpinnerAdapter(
    context: Context,
    private val categories: List<Category>
) : ArrayAdapter<Category>(context, R.layout.item_category_option) {

    private val layoutInflater = LayoutInflater.from(context)


    override fun getCount() = categories.size
    override fun getItem(position: Int) = categories[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view =
            convertView ?: layoutInflater.inflate(R.layout.header_category_option, parent, false)

        val title = view.findViewById<TextView>(R.id.tv_option_title)
        title.text = getItem(position).categoryName

        val icon = view.findViewById<ImageView>(R.id.img_option)
        icon.stateListAnimator =
            AnimatorInflater.loadStateListAnimator(context, R.xml.anim_spinner_click)
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View =
        convertView?.let {
            convertView
        } ?: kotlin.run {
            ItemCategoryOptionBinding.inflate(layoutInflater, parent, false).apply {
                val issueType = getItem(position)
                tvIssueType.text = issueType.categoryName
            }.root
        }
}