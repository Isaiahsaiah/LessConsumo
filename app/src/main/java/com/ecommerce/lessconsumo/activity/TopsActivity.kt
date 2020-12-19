package com.ecommerce.lessconsumo.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.ecommerce.lessconsumo.R
import com.ecommerce.lessconsumo.adapters.TopsAdapter
import com.example.lesscon.home.data.ProductModel
import com.example.lesscon.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_tops.*

class TopsActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mTopsAdapter: TopsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tops)

        initButtonListeners()
        initAdapter()
        loadTops()
    }

    override fun onClick(p0: View?) {
        when(p0?.id)
        {
            R.id.buttonBackTops -> finishMe()
        }
    }

    private fun initButtonListeners() {
        buttonBackTops.setOnClickListener(this)
    }

    private fun finishMe()
    {
        this.finish()
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun loadTops()
    {
        mHomeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mHomeViewModel.fetchTops()
        mHomeViewModel.productModelListLiveData?.observe(this, Observer {
            if (it != null)
            {
                recyclerView_tops.visibility =  View.VISIBLE
                mTopsAdapter.setData(it as ArrayList<ProductModel>)
            }
            else
            {
                showToast("Something went wrong \nit value: $it")
            }
            progressbar.visibility = View.GONE
        })
    }

    private fun initAdapter()
    {
        mTopsAdapter = TopsAdapter(this)
        recyclerView_tops.layoutManager = GridLayoutManager(this, 2)
        recyclerView_tops.adapter = mTopsAdapter
    }
}