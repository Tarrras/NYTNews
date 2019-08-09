package com.example.testapp.nytnesws.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.example.testapp.nytnesws.R
import com.example.testapp.nytnesws.models.Example
import com.example.testapp.nytnesws.models.News
import com.example.testapp.nytnesws.models.Result
import com.squareup.picasso.Picasso
import retrofit2.Response

class SavedNewsAdapter(val itemClick: (News) -> Unit) :
    RecyclerView.Adapter<SavedNewsAdapter.MyViewHolderItem>(), IItemHelper {
    override fun onItemDismiss(position: Int) {
        mList.removeAt(position)
        notifyItemRemoved(position)
    }

    var mList: ArrayList<News> = ArrayList()

    fun setupList(list: List<News>) {
        mList.addAll(list)
        Log.d("LOL", mList.size.toString() + "setup")
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): MyViewHolderItem {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.news_row_item, viewGroup, false)
        Log.d("LOL","view created")
        return MyViewHolderItem(view,itemClick)
    }


    override fun onBindViewHolder(myViewHolder: MyViewHolderItem, i: Int) {
        Log.d("LOL","binding")
        myViewHolder.bindItem(mList[i])
    }

    override fun getItemCount(): Int {
        Log.d("LOL",mList.size.toString() + "size")
        return mList.size
    }


    class MyViewHolderItem(itemView: View, val itemClick: (News) -> Unit) : RecyclerView.ViewHolder(itemView) {

        private val tv_name = itemView.findViewById<TextView>(R.id.title_id)
        private val tv_author = itemView.findViewById<TextView>(R.id.author_id)
        private val tv_category = itemView.findViewById<TextView>(R.id.category_id)
        private val img = itemView.findViewById<ImageView>(R.id.thumbnail)

        fun bindItem(resultItem: News) {
            with(resultItem) {
                Picasso.with(itemView.context)
                    .load(resultItem.imageurl)
                    // .resize(100,150)
                    .placeholder(R.drawable.loading_shape)
                    .error(R.drawable.loading_shape)
                    .into(img)
                tv_category.text = resultItem.category
                tv_author.text = resultItem.author
                tv_name.text = resultItem.title
                itemView.setOnClickListener { itemClick(this) }
            }
        }


    }

}
