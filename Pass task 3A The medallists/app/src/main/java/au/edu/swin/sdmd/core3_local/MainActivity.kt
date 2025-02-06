package au.edu.swin.sdmd.core3_local

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var list: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var adapter: MedalListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        list = findViewById(R.id.medallist)
        layoutManager = LinearLayoutManager(this)
        list.layoutManager = layoutManager

        loadMedals()
    }

    private fun loadMedals() {
        lifecycleScope.launch(Dispatchers.IO) {
            val medals = mutableListOf<Medal>()
            try {
                resources.openRawResource(R.raw.medallists).bufferedReader().forEachLine {
                    val temp = it.split(",")
                    medals.add(
                        Medal(
                            temp[0],
                            temp[1],
                            temp[2].toIntOrNull() ?: 0,
                            temp[3].toIntOrNull() ?: 0,
                            temp[4].toIntOrNull() ?: 0,
                            temp[5].toIntOrNull() ?: 0,
                            (temp[3].toIntOrNull() ?: 0) +
                                    (temp[4].toIntOrNull() ?: 0) +
                                    (temp[5].toIntOrNull() ?: 0)
                        )
                    )
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Snackbar.make(list, "Error loading data: ${e.message}", Snackbar.LENGTH_LONG).show()
                }
            }

            val top10Medals = medals.sortedByDescending { it.totalMedals }.take(10).map { it.totalMedals }

            withContext(Dispatchers.Main) {
                adapter = MedalListAdapter(medals, top10Medals) { medal ->
                    showDetail(medal)
                    saveClick(medal)
                }
                list.adapter = adapter
            }
        }
    }

    private fun showDetail(medal: Medal) {
        val bottomSheetFragment = BottomSheetFragment()
        val bundle = Bundle()
        bundle.putString("name", medal.country)
        bundle.putString("code", medal.countryCode)
        bundle.putInt("gold", medal.gold)
        bundle.putInt("silver", medal.silver)
        bundle.putInt("bronze", medal.bronze)
        bottomSheetFragment.arguments = bundle
        bottomSheetFragment.show(supportFragmentManager, "BottomSheetDialog")
    }

    private fun saveClick(medal: Medal) {
        val sharedPref = getSharedPreferences("saveLastClick", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putString("saveClick", "Last clicked: ${medal.country} (${medal.countryCode})")
            apply()
        }
        Snackbar.make(list, "Saved: ${medal.country}", Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        return when (item.itemId) {
            R.id.save -> {
                startActivity(Intent(this, SaveActivity::class.java))
                true
            }
            R.id.reload -> {
                recreate() // Reload data
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
