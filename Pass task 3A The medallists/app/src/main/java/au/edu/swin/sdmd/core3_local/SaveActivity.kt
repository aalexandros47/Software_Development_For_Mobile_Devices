package au.edu.swin.sdmd.core3_local

import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class SaveActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_save)

        val saveClick = findViewById<TextView>(R.id.saveClick)
        val sharedPref = getSharedPreferences("saveLastClick", Context.MODE_PRIVATE)
        val lastClick = sharedPref.getString("saveClick", "No country clicked yet")
        saveClick.text = lastClick
    }
}
