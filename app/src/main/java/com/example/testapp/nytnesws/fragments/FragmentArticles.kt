package com.example.testapp.nytnesws.fragments

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.testapp.nytnesws.R
import com.example.testapp.nytnesws.adapters.RecyclerViewAdapter
import com.example.testapp.nytnesws.models.Result
import com.example.testapp.nytnesws.presenters.TidingsPresenter
import com.example.testapp.nytnesws.views.TidingsView
import com.github.rahatarmanahmed.cpv.CircularProgressView

class FragmentArticles : MvpAppCompatFragment(), TidingsView {


    private lateinit var recyclerView: RecyclerView
    private lateinit var recyclerViewAdapter: RecyclerViewAdapter
    private lateinit var mCpvWait: CircularProgressView


    @InjectPresenter
    lateinit var tidingsPresenter: TidingsPresenter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.most_fragment, container, false)
        recyclerView = view.findViewById(R.id.emailed_recyclerview)
        mCpvWait = view.findViewById(R.id.cpv_tidings)
        return view
    }

    companion object{
        private const val ARGUMENT_PAGE_NUMBER =  "arg_page_number"

        fun newInstance(page: Int): FragmentArticles{
            val fragment = FragmentArticles()
            val bundle = Bundle()
            bundle.putInt(ARGUMENT_PAGE_NUMBER,page)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onResume() {
        super.onResume()
        val res = arguments?.getInt(ARGUMENT_PAGE_NUMBER)
        res?.let { tidingsPresenter.loadTidings(it) }
        recyclerViewAdapter = RecyclerViewAdapter{
            tidingsPresenter.openNews(it)
        }
        recyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = recyclerViewAdapter

    }



    override fun setupNewsList(newsList: List<Result>) {
        recyclerViewAdapter.setupList(newsList)
    }

    override fun openNews(bundle: Bundle) {
        val fragment = ItemDetailFragment()
        fragment.arguments = bundle
        val fmTransaction = activity?.supportFragmentManager?.beginTransaction()
        fmTransaction?.setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
        fmTransaction?.replace(R.id.container_inside,fragment)?.addToBackStack("TAG_FRAGMENT")?.commit()
    }

    override fun startLoading() {
        recyclerView.visibility = View.GONE
        mCpvWait.visibility = View.VISIBLE
    }

    override fun endLoading() {
        recyclerView.visibility = View.VISIBLE
        mCpvWait.visibility = View.GONE
    }

    override fun showError(message: String) {
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show()
    }
}
