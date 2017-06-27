package com.example.recyclerview_expandable

import android.content.Context
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.hendraanggrian.widget.ExpandableItem
import com.hendraanggrian.widget.ExpandableRecyclerView

/**
 * @author Hendra Anggrian (hendraanggrian@gmail.com)
 */
class ItemAdapter(layout: LinearLayoutManager) : ExpandableRecyclerView.Adapter<ItemAdapter.ViewHolder>(layout) {

    private var context: Context? = null
    private val items = arrayOf(
            Item(R.drawable.ic_test1, "14 Easy Weekend Getaways"),
            Item(R.drawable.ic_test2, "Why We Travel"),
            Item(R.drawable.ic_test3, "A Paris Farewell"),
            Item(R.drawable.ic_test1, "14 Easy Weekend Getaways"),
            Item(R.drawable.ic_test2, "Why We Travel"),
            Item(R.drawable.ic_test3, "A Paris Farewell"),
            Item(R.drawable.ic_test1, "14 Easy Weekend Getaways"),
            Item(R.drawable.ic_test2, "Why We Travel"),
            Item(R.drawable.ic_test3, "A Paris Farewell"),
            Item(R.drawable.ic_test1, "14 Easy Weekend Getaways"),
            Item(R.drawable.ic_test2, "Why We Travel"),
            Item(R.drawable.ic_test3, "A Paris Farewell")
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemAdapter.ViewHolder {
        context = parent.context
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.view_row, parent, false))
    }

    override fun onBindViewHolder(holder: ItemAdapter.ViewHolder, position: Int) {
        super.onBindViewHolder(holder, position)
        val (drawable, title) = items[position]
        holder.imageView.setImageDrawable(ContextCompat.getDrawable(context, drawable))
        holder.textView.text = title
        holder.button.setOnClickListener { Toast.makeText(context, "Clicked!", Toast.LENGTH_SHORT).show() }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val item: ExpandableItem = itemView.findViewById(R.id.row) as ExpandableItem
        val imageView: ImageView = item.headerLayout.findViewById(R.id.imageView) as ImageView
        val textView: TextView = item.headerLayout.findViewById(R.id.textView) as TextView
        val button: Button = item.contentLayout.findViewById(R.id.button) as Button
    }
}