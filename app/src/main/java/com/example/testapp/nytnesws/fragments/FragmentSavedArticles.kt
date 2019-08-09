package com.example.testapp.nytnesws.fragments

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.testapp.nytnesws.R
import com.example.testapp.nytnesws.adapters.RecyclerViewAdapter
import com.example.testapp.nytnesws.adapters.SavedNewsAdapter
import com.example.testapp.nytnesws.builders.NetworkService
import com.example.testapp.nytnesws.models.Example
import com.example.testapp.nytnesws.models.News
import com.example.testapp.nytnesws.models.Result
import com.example.testapp.nytnesws.presenters.SavedArticlesPresenter
import com.example.testapp.nytnesws.presenters.TidingsPresenter
import com.example.testapp.nytnesws.views.SavedArticlesView
import com.example.testapp.nytnesws.views.TidingsView
import com.github.rahatarmanahmed.cpv.CircularProgressView
import kotlinx.android.synthetic.main.most_fragment.*
import kotlinx.android.synthetic.main.list_fragment.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import java.util.ArrayList

class FragmentSavedArticles : MvpAppCompatFragment(), SavedArticlesView {



    private lateinit var recyclerView: RecyclerView
    private lateinit var savedNewsAdapter: SavedNewsAdapter
    private lateinit var textEmpty: TextView
    private lateinit var mCpvWait: CircularProgressView
    val mList: ArrayList<News> = ArrayList()


    @InjectPresenter
    lateinit var savedArticlesPresenter: SavedArticlesPresenter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.list_fragment, container, false)
        recyclerView = view.findViewById(R.id.recyclerview_id)
        mCpvWait = view.findViewById(R.id.cpv_t)
        textEmpty = view.findViewById(R.id.txt_empty)
        return view
    }

    companion object{

        fun newInstance(): FragmentSavedArticles{
            val fragment = FragmentSavedArticles()
            return fragment
        }
    }


    override fun onResume() {
        super.onResume()

        context?.let { savedArticlesPresenter.showSavedArticles(it) }
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        recyclerView.layoutManager = layoutManager

        val dividerItemDecoration = DividerItemDecoration(context,layoutManager.orientation)
        recyclerView.addItemDecoration(dividerItemDecoration)

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        recyclerView.addItemDecoration(itemTouchHelper)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        savedNewsAdapter = SavedNewsAdapter{
            savedArticlesPresenter.openSavedArticles(it.id)
        }
        recyclerView.adapter = savedNewsAdapter
        savedNewsAdapter.notifyDataSetChanged()
    }

    private val itemTouchHelperCallback = object:ItemTouchHelper.Callback() {
        override fun onSwiped(p0: RecyclerView.ViewHolder, p1: Int) {
            context?.let { savedArticlesPresenter.deleteArticle(it,mList[p0.adapterPosition]) }
            savedNewsAdapter.onItemDismiss(p0.adapterPosition)
        }

        override fun getMovementFlags(recyclerView:RecyclerView,viewHolder:RecyclerView.ViewHolder):Int {
            return makeMovementFlags(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.END)
        }
        override fun onMove(recyclerView:RecyclerView,viewHolder:RecyclerView.ViewHolder, viewHolder1:RecyclerView.ViewHolder):Boolean {
            return false
        }
    }

    override fun showSavedArtciles(newsList: List<News>) {
        recyclerView.visibility = View.VISIBLE
        mList.addAll(newsList)
        savedNewsAdapter.setupList(newsList)
        savedNewsAdapter.notifyDataSetChanged()
    }

    override fun showEmptyList() {
        recyclerView.visibility = View.GONE
        txt_empty.visibility = View.VISIBLE
        txt_empty.text = "Список пуст"
    }

    override fun openItem(id: Int) {
        val fragment = ShowSavedArticleFragment()
        val bundle = Bundle()
        bundle.putInt("item_id",id)
        fragment.arguments = bundle
        val fmTransaction = activity?.supportFragmentManager?.beginTransaction()
        fmTransaction?.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
        fmTransaction?.replace(R.id.container_inside,fragment)?.addToBackStack("TAG_FRAGMENT")?.commit()
    }


    override fun showError(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }


}
