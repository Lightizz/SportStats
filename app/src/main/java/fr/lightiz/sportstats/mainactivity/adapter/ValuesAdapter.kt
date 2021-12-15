package fr.lightiz.sportstats.mainactivity.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import fr.lightiz.sportstats.MainActivity
import fr.lightiz.sportstats.R
import fr.lightiz.sportstats.models.Value

class ValuesAdapter(private val valuesList: List<Value>,
                    private val context: MainActivity,
                    private val currentDB: SharedPreferences) : RecyclerView.Adapter<ValuesAdapter.ViewHolder>(){
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        var valueTitle: TextView? = view.findViewById(R.id.item_values_title)
        var valueValue: TextView? = view.findViewById(R.id.item_values_value)

        val valueAdd: ImageView? = view.findViewById(R.id.item_values_more)
        val valueLess: ImageView? = view.findViewById(R.id.item_values_less)

        val valueDelete: ImageView? = view.findViewById(R.id.item_values_delete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_values, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentValue = valuesList[position]

        holder.valueTitle?.text = currentValue.title
        holder.valueValue?.text = currentValue.value.toString()

        holder.valueAdd?.setOnClickListener {
            currentValue.value += 1
            holder.valueValue?.text = (currentValue.value).toString()
            currentDB.edit().putInt(currentValue.title, currentValue.value).apply()
        }

        holder.valueLess?.setOnClickListener {
            currentValue.value -= 1
            holder.valueValue?.text = (currentValue.value).toString()
            currentDB.edit().putInt(currentValue.title, currentValue.value).apply()
        }

        holder.valueDelete?.setOnClickListener{
            currentDB.edit().remove(currentValue.title).apply()
            valuesList.drop(position)

            context.loadFragment(MainActivity().currentFragment)
            context.startActivity(Intent(context, MainActivity::class.java))
        }
    }

    override fun getItemCount(): Int {
        return valuesList.size
    }
}