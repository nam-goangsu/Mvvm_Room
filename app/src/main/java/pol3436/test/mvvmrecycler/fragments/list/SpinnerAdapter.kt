package pol3436.test.mvvmrecycler.fragments.list

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.annotation.LayoutRes
import androidx.core.content.ContextCompat
import pol3436.test.mvvmrecycler.databinding.ItemSpinnerBinding
import pol3436.test.mvvmrecycler.model.User


class SpinnerAdapter (
    context: Context,
    @LayoutRes private val resId: Int,
    private var values: List<String>
) : ArrayAdapter<String>(context, resId, values) {


    companion object {
        public var values = arrayListOf<String>()
    }

    open fun setData1(values: List<String>) {
        //유저리스트가 변경 되었을때, 업데이트해줍니다.
        this.values = values as ArrayList<String>
        notifyDataSetChanged()
    }
    override fun getCount() = values.size


    override fun getItem(position: Int) = values[position]

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        val model = values[position]
        try {

            binding.text.text = model

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return binding.root
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val binding = ItemSpinnerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val model = values[position]
        try {

            binding.text.text = model

        } catch (e: Exception) {
            e.printStackTrace()
        }
        return binding.root
    }



}