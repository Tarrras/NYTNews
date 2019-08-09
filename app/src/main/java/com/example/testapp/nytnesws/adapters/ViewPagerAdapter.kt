package com.example.testapp.nytnesws.adapters

import android.content.Context
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.testapp.nytnesws.R
import com.example.testapp.nytnesws.fragments.FragmentArticles
import com.example.testapp.nytnesws.fragments.FragmentSavedArticles

import java.util.ArrayList

class ViewPagerAdapter(fm: FragmentManager?, context: Context?) : FragmentPagerAdapter(fm) {

    private val lstFragment = ArrayList<Fragment>()
    private val lstTitles = context?.resources?.getStringArray(R.array.fragments)

    override fun getItem(i: Int): Fragment {
        return if(i==3){
            FragmentSavedArticles.newInstance()
        } else FragmentArticles.newInstance(i)
    }

    override fun getCount(): Int {
        return lstTitles?.size!!
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return this.lstTitles!![position]
    }

    fun AddFragment(fragment: Fragment) {
        lstFragment.add(fragment)
    }
}
