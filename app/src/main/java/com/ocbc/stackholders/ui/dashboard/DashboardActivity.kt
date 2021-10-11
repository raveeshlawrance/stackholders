package com.ocbc.stackholders.ui.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.google.android.material.tabs.TabLayout
import com.ocbc.stackholders.databinding.ActivityDashboardBinding
import android.content.Intent
import androidx.fragment.app.Fragment
import com.ocbc.stackholders.R
import com.ocbc.stackholders.ui.BaseActivity


class DashboardActivity : BaseActivity<ActivityDashboardBinding>() {

    private lateinit var sectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setToolbarTitle("")
        sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        val viewPager: ViewPager = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        tabs.setupWithViewPager(viewPager)

    }

    fun setToolbarTitle(title : String) {
        binding.title.text = title
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun layoutId(): Int {
        return R.layout.activity_dashboard
    }


}