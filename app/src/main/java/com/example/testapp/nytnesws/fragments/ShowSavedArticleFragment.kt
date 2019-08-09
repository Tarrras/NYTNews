package com.example.testapp.nytnesws.fragments

import android.content.ContentValues
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.widget.NestedScrollView
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.text.method.LinkMovementMethod
import android.view.*
import android.widget.HorizontalScrollView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.testapp.nytnesws.R
import com.example.testapp.nytnesws.models.News
import com.example.testapp.nytnesws.models.Result
import com.example.testapp.nytnesws.presenters.NewsPresenter
import com.example.testapp.nytnesws.presenters.OpenSavedArticlePresenter
import com.example.testapp.nytnesws.presenters.SavedArticlesPresenter
import com.example.testapp.nytnesws.views.NewsView
import com.example.testapp.nytnesws.views.OpenSavedArticleView
import com.example.testapp.nytnesws.views.SavedArticlesView
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.squareup.picasso.Picasso

class ShowSavedArticleFragment : MvpAppCompatFragment(), OpenSavedArticleView {

    lateinit var aa_title: TextView
    lateinit var aa_description: TextView
    lateinit var aa_newsurl: TextView
    lateinit var expanded: ImageView
    lateinit var nstdScrollView: NestedScrollView
    private var toolbar: Toolbar? = null
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var appBarLayout: AppBarLayout
    var item_id: Int = -1

    @InjectPresenter
    lateinit var openSavedArticlePresenter: OpenSavedArticlePresenter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.activity_news, container, false)
        toolbar = view.findViewById(R.id.toolbar)
        aa_description = view.findViewById(R.id.aa_description)
        aa_title = view.findViewById(R.id.aa_title)
        aa_newsurl = view.findViewById(R.id.aa_newsurl_id)
        expanded = view.findViewById(R.id.expandedImage)
        collapsingToolbarLayout = view.findViewById(R.id.collapsingtoolbar_id)
        collapsingToolbarLayout.isTitleEnabled = true
        nstdScrollView = view.findViewById(R.id.scroll_view)
        appBarLayout = view.findViewById(R.id.appbarlayout_id)
        return view
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }


    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        (activity as AppCompatActivity).supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        item_id = arguments?.getInt("item_id")!!
        context?.let {  openSavedArticlePresenter.showArticle(it, item_id) }
    }

    override fun showSavedArticle(news: News) {
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + i == 0) {
                    isShow = true
                    collapsingToolbarLayout!!.title = news.category
                } else if (isShow) {
                    isShow = false
                    collapsingToolbarLayout!!.title = ""
                }

            }
        })

        aa_title.text = news.title
        aa_description.text = news.abstracts


        Picasso.with(context)
            .load(news.imageurl)
            .placeholder(R.drawable.loading_shape)
            .error(R.drawable.loading_shape)
            .into(expanded)


        aa_newsurl.text = "Click here to read more"
        aa_newsurl.movementMethod = LinkMovementMethod.getInstance()
        aa_newsurl.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(news.newsurl)
            startActivity(browserIntent)
        }
    }

    override fun showError(text: String) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
    }



}
