package fr.lightiz.sportstats

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.transition.Explode
import android.view.Window
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import fr.lightiz.sportstats.mainactivity.MainActivityHeavyFragment
import fr.lightiz.sportstats.mainactivity.MainActivityLongFragment
import fr.lightiz.sportstats.mainactivity.MainActivityTenFragment
import fr.lightiz.sportstats.models.Value

class MainActivity : AppCompatActivity() {

    private var heavyValuesList = arrayListOf<Value>()
    private var longValuesList = arrayListOf<Value>()
    private var tenValuesList = arrayListOf<Value>()

    private lateinit var currentDb: SharedPreferences

    var currentFragment: Fragment = Fragment()

    private lateinit var valueAddImage: ImageView
    private lateinit var container: FrameLayout
    private lateinit var navBar: BottomNavigationView

    private lateinit var localDBHeavy: SharedPreferences
    private lateinit var localDBLong: SharedPreferences
    private lateinit var localDBTen: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        );

        with(window) {
            requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            exitTransition = Explode()
        }

        setContentView(R.layout.activity_main)

        localDBHeavy = getSharedPreferences("SportsStatsHeavy", Context.MODE_PRIVATE)
        localDBLong = getSharedPreferences("SportsStatsLong", Context.MODE_PRIVATE)
        localDBTen = getSharedPreferences("SportsStatsTen", Context.MODE_PRIVATE)


        loadFragment(MainActivityHeavyFragment(this, heavyValuesList, localDBHeavy))
        currentFragment = MainActivityHeavyFragment(this, heavyValuesList, localDBHeavy)

        currentDb = localDBHeavy

        for (value in localDBHeavy.all) {
            heavyValuesList.add(Value(value.key, value.value as Int))
        }
        for (value in localDBLong.all) {
            longValuesList.add(Value(value.key, value.value as Int))
        }
        for (value in localDBTen.all) {
            tenValuesList.add(Value(value.key, value.value as Int))
        }

        navBar = findViewById(R.id.activity_main_navbar)
        navBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.heavy_page -> {
                    loadFragment(MainActivityHeavyFragment(this, heavyValuesList, localDBHeavy))
                    currentFragment = MainActivityHeavyFragment(this, heavyValuesList, localDBHeavy)
                    currentDb = localDBHeavy
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.long_page -> {
                    loadFragment(MainActivityLongFragment(this, longValuesList, localDBLong))
                    currentFragment = MainActivityLongFragment(this, longValuesList, localDBLong)
                    currentDb = localDBLong
                    return@setOnNavigationItemSelectedListener true
                }
                R.id.ten_page -> {
                    loadFragment(MainActivityTenFragment(this, tenValuesList, localDBTen))
                    currentFragment = MainActivityTenFragment(this, tenValuesList, localDBTen)
                    currentDb = localDBTen
                    return@setOnNavigationItemSelectedListener true
                }
                else -> {
                    return@setOnNavigationItemSelectedListener false
                }
            }
        }

        valueAddImage = findViewById(R.id.activity_main_add_image)
        valueAddImage.setOnClickListener {
            val popup = MainActivityPopupAddValue(this, currentDb, currentFragment)
            popup.create()
            popup.show()
        }
    }

    fun loadFragment(fragment: Fragment) {
        val transactionFragment = supportFragmentManager.beginTransaction()
        container = findViewById(R.id.activity_main_container)
        transactionFragment.replace(R.id.activity_main_container, fragment)
        transactionFragment.addToBackStack(null)
        transactionFragment.commit()
    }
}




//        valuesRecyclerView = findViewById(R.id.activity_main_values_recyclerview)
//        valuesRecyclerView.adapter = ValuesAdapter(valuesList, this)
//        valuesRecyclerView.addItemDecoration(ValueItemDecoration())



    /*
    public fun updateRecyclerView() {
        valuesRecyclerView.adapter = ValuesAdapter(valuesList, this)
    }
    */