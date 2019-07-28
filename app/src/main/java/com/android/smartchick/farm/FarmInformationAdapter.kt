package com.android.smartchick.farm

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.android.smartchick.R
import kotlinx.android.synthetic.main.item_listview_farm.view.*

class FarmInformationAdapter (val farmNameList: MutableList<String?>) :
        RecyclerView.Adapter<FarmInformationAdapter.ItemViewHolder>() {

    var farms: MutableList<String?> = farmNameList.toMutableList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_listview_farm, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
       return farms.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.tvNo?.text = "โรงเรือนที่ ${position+1}"
        holder.tvName?.text = farms[position]
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), ItemTouchHelperViewHolder {

        val tvName: TextView? = itemView.tvFarmName
        val tvNo: TextView? = itemView.tvFarmNo

        override fun onItemSelected() {
            itemView.setBackgroundColor(Color.LTGRAY)
        }

        override fun onItemClear() {
            itemView.setBackgroundColor(0)
        }

    }

    interface ItemTouchHelperViewHolder {

        fun onItemSelected()

        fun onItemClear()
    }
}