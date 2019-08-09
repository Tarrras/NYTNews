package com.example.testapp.nytnesws.fragments


import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.example.testapp.nytnesws.R
import com.example.testapp.nytnesws.adapters.ViewPagerAdapter


class TabFragment : Fragment() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager: ViewPager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_tab, container, false)
        tabLayout = view.findViewById(R.id.tablayout_id)
        viewPager = view.findViewById(R.id.viewpager_id)

        return view
    }

    override fun onResume() {
        super.onResume()

        val adapter = ViewPagerAdapter(childFragmentManager,context)
        adapter.AddFragment(FragmentArticles())
        viewPager.adapter = adapter
        tabLayout.setupWithViewPager(viewPager)
    }

}
