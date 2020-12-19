package com.ecommerce.lessconsumo.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.lessconsumo.R
import com.ecommerce.lessconsumo.adapters.NewProductsAdapter
import com.ecommerce.lessconsumo.adapters.SaleProductsAdapter
import com.example.lesscon.home.data.ProductModel
import com.example.lesscon.home.viewmodel.HomeViewModel
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var mHomeViewModel: HomeViewModel
    private lateinit var mSaleProductsAdapter: SaleProductsAdapter
    private lateinit var mNewProductsAdapter: NewProductsAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        initButtonListeners()
        initNewProductsAdapter()
        loadNewArrivals()
        initSaleProductsAdapter()
        loadSaleProducts()

        // added
        buttonNotification.setOnClickListener {
            Toast.makeText(this, "This feature is not yet available.", Toast.LENGTH_SHORT).show()
        }
        buttonSearch.setOnClickListener {
            gotoNewActivity(SearchActivity())
        }
        buttonAccount.setOnClickListener {
            gotoNewActivity(ProfilesActivity())
        }
        buttonCart.setOnClickListener {
            gotoNewActivity(CartActivity())
        }
        buttonViewall.setOnClickListener {
            Toast.makeText(this, "This feature is not yet available.", Toast.LENGTH_SHORT).show()
        }
        textviewSale.setOnClickListener {
            gotoNewActivity(SaleActivity())
        }
        textviewMen.setOnClickListener {
            gotoNewActivity(MenActivity())
        }
        textviewWomen.setOnClickListener {
            gotoNewActivity(WomenActivity())
        }
        textviewChild.setOnClickListener {
            gotoNewActivity(ChildActivity())
        }

    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.buttonTops -> gotoNewActivity(TopsActivity())
            R.id.buttonBottoms -> gotoNewActivity(BottomsActivity())
            R.id.buttonDresses -> gotoNewActivity(DressesActivity())
            R.id.buttonBags -> gotoNewActivity(BagsActivity())
            R.id.buttonShoes -> gotoNewActivity(ShoesActivity())
            R.id.buttonMentops -> gotoNewActivity(MenTopsActivity())
            R.id.buttonMenbottoms -> gotoNewActivity(MenBottomsActivity())
            R.id.buttonBoys -> gotoNewActivity(BoysActivity())
            R.id.buttonGirls -> gotoNewActivity(GirlsActivity())
        }
    }

    private fun initButtonListeners() {
        buttonTops.setOnClickListener(this)
        buttonBottoms.setOnClickListener(this)
        buttonDresses.setOnClickListener(this)
        buttonBags.setOnClickListener(this)
        buttonShoes.setOnClickListener(this)
        buttonMentops.setOnClickListener(this)
        buttonMenbottoms.setOnClickListener(this)
        buttonBoys.setOnClickListener(this)
        buttonGirls.setOnClickListener(this)
    }

    private fun initSaleProductsAdapter() {
        mSaleProductsAdapter = SaleProductsAdapter(this)
        recyclerView_saleProducts.layoutManager = GridLayoutManager(this, 2)
        recyclerView_saleProducts.adapter = mSaleProductsAdapter
    }

    private fun initNewProductsAdapter() {
        mNewProductsAdapter = NewProductsAdapter(this)
        recyclerView_newArrivals.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recyclerView_newArrivals.adapter = mNewProductsAdapter
    }

    private fun gotoNewActivity(activity : Activity) {
        val intent = Intent (this, activity::class.java)
        startActivity(intent)
    }

    private fun showToast(s: String) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show()
    }

    private fun loadNewArrivals() {
        //load new arrivals
        mHomeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mHomeViewModel.fetchNewProducts()
        mHomeViewModel.productModelListLiveData?.observe(this, Observer {
            if (it != null)
            {
                recyclerView_newArrivals.visibility =  View.VISIBLE
                mNewProductsAdapter.setData(it as ArrayList<ProductModel>)
            }
            else
            {
                showToast("Something went wrong \nit value: $it")
            }
            progress_newProducts.visibility = View.GONE
        })
    }

    private fun loadSaleProducts() {
        // load sale products
        mHomeViewModel = ViewModelProvider(this)[HomeViewModel::class.java]
        mHomeViewModel.fetchOnSaleProducts()
        mHomeViewModel.productModelListLiveData?.observe(this, Observer {
            if (it != null)
            {
                recyclerView_saleProducts.visibility =  View.VISIBLE
                mSaleProductsAdapter.setData(it as ArrayList<ProductModel>)
            }
            else
            {
                showToast("Something went wrong \nit value: $it")
            }
            progress_saleProducts.visibility = View.GONE
        })
    }
}