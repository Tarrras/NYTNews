package com.example.testapp.nytnesws.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.testapp.nytnesws.R
import com.example.testapp.nytnesws.models.Example
import com.example.testapp.nytnesws.models.Result
import com.squareup.picasso.Picasso
import retrofit2.Response

class RecyclerViewAdapter(val itemClick: (Result) -> Unit) :
    RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder>() {
    var mList: ArrayList<Result> = ArrayList()

    fun setupList(list: List<Result>) {
        mList.addAll(list)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.news_row_item, viewGroup, false)
        return MyViewHolder(view,itemClick)
    }


    override fun onBindViewHolder(myViewHolder: MyViewHolder, i: Int) {
        myViewHolder.bindItem(mList[i])
    }

    override fun getItemCount(): Int {
        return mList.size
    }


    class MyViewHolder(itemView: View, val itemClick: (Result) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val tv_name = itemView.findViewById<TextView>(R.id.title_id)
        private val tv_author = itemView.findViewById<TextView>(R.id.author_id)
        private val tv_category = itemView.findViewById<TextView>(R.id.category_id)
        private val img = itemView.findViewById<ImageView>(R.id.thumbnail)

        fun bindItem(resultItem: Result) {
            with(resultItem) {
                Picasso.with(itemView.context)
                    .load(resultItem.media[0].mediaMetadata[1].url)
                    // .resize(100,150)
                    .placeholder(R.drawable.loading_shape)
                    .error(R.drawable.loading_shape)
                    .into(img)
                tv_category.text = resultItem.section
                tv_author.text = resultItem.byline
                tv_name.text = resultItem.title
                itemView.setOnClickListener { itemClick(this) }
            }
        }


    }

}
