package pol3436.test.mvvmrecycler.fragments.list

import android.content.ContentValues.TAG
import android.util.Log
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.custom_row.view.*
import pol3436.test.mvvmrecycler.R
import pol3436.test.mvvmrecycler.databinding.FragmentListBinding

import pol3436.test.mvvmrecycler.model.User


class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {


    private val TAG ="TEST"
    companion object {
        public var checkboxList = SparseBooleanArray()
        public var userList = arrayListOf<User>()
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // class MyViewHolder(val binding: FragmentListBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
        //위에서 만든 custom_row 레이아웃을 연결해줍니다.

        //  return MyViewHolder(FragmentListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return if(userList.isNullOrEmpty()){0}else{userList.size} //userList에 사이즈를 리턴합니다.
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //UI와 전달받은 userList에 데이터베이스 데이터를 연결해줍니다.
        val currentItem = userList[position]

        holder.itemView.id_txt.text = (position+1).toString()
        holder.itemView.id_txt.isChecked = checkboxList[position]
        Log.d(TAG, "CLicked : "+ checkboxList[position] )

        holder.itemView.firstName_txt.text = currentItem.firstName
        holder.itemView.lastName_txt.text = currentItem.lastName
        holder.itemView.age_txt.text = currentItem.age.toString()

        holder.itemView.id_txt.setOnClickListener {
            Log.d(TAG, "CLick" )
            if(!holder.itemView.id_txt.isChecked){
                Log.d(TAG, "CLick list " + checkboxList.toString()+ " / " + userList.toString())
                checkboxList.put(position,false)
            }
            else{checkboxList.put(position,true)
                Log.d(TAG, "CLick true "+position  )
            }
            notifyItemChanged(position)
        }
        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(user: List<User>) {
        //유저리스트가 변경 되었을때, 업데이트해줍니다.
        userList = user as ArrayList<User>
        notifyDataSetChanged()
    }
}
/*

    private var userList = emptyList<User>() //
    private val checkboxStatus = SparseBooleanArray()

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // class MyViewHolder(val binding: FragmentListBinding): RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.custom_row, parent, false)
        )
        //위에서 만든 custom_row 레이아웃을 연결해줍니다.

        //  return MyViewHolder(FragmentListBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun getItemCount(): Int {
        return if (userList.isNullOrEmpty()) {
            0
        } else {
            userList.size
        } //userList에 사이즈를 리턴합니다.
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        //UI와 전달받은 userList에 데이터베이스 데이터를 연결해줍니다.
        val currentItem = userList[position]

        holder.itemView.id_txt.text = currentItem.id.toString()
        holder.itemView.firstName_txt.text = currentItem.firstName
        holder.itemView.lastName_txt.text = currentItem.lastName
        holder.itemView.age_txt.text = currentItem.age.toString()

        holder.itemView.rowLayout.setOnClickListener {
            val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
            holder.itemView.findNavController().navigate(action)
        }

    }

    fun setData(user: List<User>) {
        //유저리스트가 변경 되었을때, 업데이트해줍니다.
        this.userList = user
        notifyDataSetChanged()
    }
}*/
