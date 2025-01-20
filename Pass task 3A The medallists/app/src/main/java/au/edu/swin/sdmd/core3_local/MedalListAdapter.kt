package au.edu.swin.sdmd.core3_local

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MedalListAdapter(
    private val data: List<Medal>,
    private val top10Medals: List<Int>,
    private val listener: (Medal) -> Unit
) : RecyclerView.Adapter<MedalListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.medal_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        val isTop10 = top10Medals.contains(item.totalMedals)
        holder.bind(item, isTop10)
    }

    inner class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        private val countryName: TextView = view.findViewById(R.id.country_name)
        private val countryCode: TextView = view.findViewById(R.id.country_code)
        private val totalMedals: TextView = view.findViewById(R.id.totalMedals)
        private val medalIcon: ImageView = view.findViewById(R.id.medal_icon)

        fun bind(item: Medal, isTop10: Boolean) {
            countryName.text = item.country
            countryCode.text = item.countryCode
            totalMedals.text = item.totalMedals.toString()

            if (isTop10) view.setBackgroundColor(Color.LTGRAY)
            else view.setBackgroundColor(Color.WHITE)

            medalIcon.setImageResource(
                when {
                    item.gold > 0 -> R.drawable.gold_icon
                    item.silver > 0 -> R.drawable.silver_icon
                    item.bronze > 0 -> R.drawable.bronze_icon
                    else -> R.drawable.no_medal_icon
                }
            )

            view.setOnClickListener { listener(item) }
        }
    }
}
