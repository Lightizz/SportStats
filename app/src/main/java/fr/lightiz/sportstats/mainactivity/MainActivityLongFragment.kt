package fr.lightiz.sportstats.mainactivity

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.lightiz.sportstats.MainActivity
import fr.lightiz.sportstats.R
import fr.lightiz.sportstats.mainactivity.adapter.ValueItemDecoration
import fr.lightiz.sportstats.mainactivity.adapter.ValuesAdapter
import fr.lightiz.sportstats.models.Value

class MainActivityLongFragment(private val mainActivity: MainActivity, private val valuesList: ArrayList<Value>, private val db: SharedPreferences): Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater?.inflate(R.layout.main_activity_long_fragment, container, false)

        val longRecyclerView = view.findViewById<RecyclerView>(R.id.long_fragment_rv)
        longRecyclerView?.adapter = ValuesAdapter(valuesList, mainActivity, db)
        longRecyclerView?.addItemDecoration(ValueItemDecoration())

        val emptyText = view.findViewById<TextView>(R.id.main_page_fragment_empty_text_long)
        if (valuesList.isEmpty()) {
            emptyText.visibility = View.VISIBLE
        }

        return view
    }
}