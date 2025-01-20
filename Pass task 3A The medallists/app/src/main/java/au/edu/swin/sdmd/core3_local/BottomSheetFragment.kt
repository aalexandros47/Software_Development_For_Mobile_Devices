package au.edu.swin.sdmd.core3_local

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.bottomsheet_fragment, container, false)

        val name = view.findViewById<TextView>(R.id.detailName)
        val code = view.findViewById<TextView>(R.id.detailCode)
        val gold = view.findViewById<TextView>(R.id.gold)
        val silver = view.findViewById<TextView>(R.id.silver)
        val bronze = view.findViewById<TextView>(R.id.bronze)

        val args = arguments
        name.text = args?.getString("name")
        code.text = args?.getString("code")
        gold.text = "Gold: ${args?.getInt("gold")}"
        silver.text = "Silver: ${args?.getInt("silver")}"
        bronze.text = "Bronze: ${args?.getInt("bronze")}"

        return view
    }
}
