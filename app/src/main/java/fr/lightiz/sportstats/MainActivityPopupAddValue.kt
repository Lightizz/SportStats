package fr.lightiz.sportstats

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Window
import android.widget.*
import androidx.fragment.app.Fragment
import fr.lightiz.sportstats.models.Value

class MainActivityPopupAddValue(private var mainActivity: MainActivity, private var currentDb: SharedPreferences, private var currentFragment: Fragment): Dialog(mainActivity) {
    private lateinit var close: ImageView
    private lateinit var textInput: EditText
    private lateinit var confirmButton: Button

    @SuppressLint("CommitPrefEdits", "ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)

        setContentView(R.layout.popup_add_value)

        close = findViewById(R.id.popup_add_value_close)
        close.setOnClickListener {
            hide()
            dismiss()
        }

        textInput = findViewById(R.id.popup_add_value_textinput)

        confirmButton = findViewById(R.id.popup_add_value_confirm_button)
        confirmButton.setOnClickListener {
            val valueToAdd = Value(textInput.text.toString(), 18)

            currentDb.edit().putInt(valueToAdd.title, valueToAdd.value).apply()

            hide()
            dismiss()

            mainActivity.loadFragment(currentFragment)

            mainActivity.startActivity(Intent(mainActivity, MainActivity::class.java))
        }
    }
}
