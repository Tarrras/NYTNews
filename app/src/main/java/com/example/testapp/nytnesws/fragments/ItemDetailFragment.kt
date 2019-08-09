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
import com.example.testapp.nytnesws.views.NewsView
import com.github.rahatarmanahmed.cpv.CircularProgressView
import com.squareup.picasso.Picasso

class ItemDetailFragment : MvpAppCompatFragment(), NewsView {


    lateinit var aa_title: TextView
    lateinit var aa_description: TextView
    lateinit var aa_newsurl: TextView
    lateinit var expanded: ImageView
    lateinit var nstdScrollView: NestedScrollView
    private var category: String? = null
    private var title: String? = null
    private var news_url: String? = null
    private var news_abstract: String? = null
    private var image_url: String? = null
    private var author: String? = null
    private var toolbar: Toolbar? = null
    private lateinit var collapsingToolbarLayout: CollapsingToolbarLayout
    private lateinit var appBarLayout: AppBarLayout

    @InjectPresenter
    lateinit var newsPresenter: NewsPresenter

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
        category = arguments!!.getString(EXTRA_NEWS_CATEGORY)
        author = arguments!!.getString(EXTRA_NEWS_AUTHOR)
        title = arguments!!.getString(EXTRA_NEWS_TITLE)
        news_url = arguments!!.getString(EXTRA_NEWS_URL)
        news_abstract = arguments!!.getString(EXTRA_NEWS_ABSTRACT)
        image_url = arguments!!.getString(EXTRA_NEWS_IMAGE)
        newsPresenter.showNews()
        appBarLayout.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            var isShow = false
            var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, i: Int) {

                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                }
                if (scrollRange + i == 0) {
                    isShow = true
                    collapsingToolbarLayout!!.title = category
                } else if (isShow) {
                    isShow = false
                    collapsingToolbarLayout!!.title = ""
                }

            }
        })

    }


    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_news,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item!!.itemId) {
            R.id.action_save ->{

                val news = News(title!!,category!!,news_url!!,image_url!!,news_abstract!!,author!!)
                context?.let { newsPresenter.saveNewsToDatabase(news, it) }
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        val EXTRA_NEWS_CATEGORY = "category"
        val EXTRA_NEWS_AUTHOR = "author"
        val EXTRA_NEWS_TITLE = "title"
        val EXTRA_NEWS_URL = "news_url"
        val EXTRA_NEWS_ABSTRACT = "news_abstract"
        val EXTRA_NEWS_IMAGE = "image_url"
    }

    override fun showNews() {

        aa_title.text = title
        aa_description.text = news_abstract


        Picasso.with(context)
            .load(image_url)
            .placeholder(R.drawable.loading_shape)
            .error(R.drawable.loading_shape)
            .into(expanded)


        aa_newsurl.text = "Click here to read more"
        aa_newsurl.movementMethod = LinkMovementMethod.getInstance()
        aa_newsurl.setOnClickListener {
            val browserIntent = Intent(Intent.ACTION_VIEW)
            browserIntent.data = Uri.parse(news_url)
            startActivity(browserIntent)
        }
    }


    override fun showError(text: String) {
        Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
    }


}
