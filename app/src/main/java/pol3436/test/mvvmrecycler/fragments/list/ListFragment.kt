package pol3436.test.mvvmrecycler.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pol3436.test.mvvmrecycler.R
import pol3436.test.mvvmrecycler.databinding.FragmentListBinding
import pol3436.test.mvvmrecycler.viewmodel.UserViewModel


class ListFragment : Fragment() {
    private var TAG = "test  "
    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding

    private lateinit var recyclerView: RecyclerView
    private lateinit var spinneradapter: SpinnerAdapter
    private var arraylist_spinner  = arrayListOf<String>()
    private var arraylist_spinner1  = mutableListOf<String>()
    val adapter = ListAdapter()

    // ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,arraylist_spinner)

    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListBinding.inflate(inflater, container, false)





        recyclerView = binding!!.recyclerview
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        mUserViewModel.readAllData.observe(
            viewLifecycleOwner,
            Observer { user ->
                adapter.setData(user)
            }
        )


        //adapter1 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,arraylist_spinner)

        mUserViewModel.spinnerData.observe(
            viewLifecycleOwner, Observer {it ->
                //arraylist_spinner = it as ArrayList<String>
                spinneradapter.setData1(it)
            }
        )


        arraylist_spinner1.addAll(arraylist_spinner)
        spinneradapter = SpinnerAdapter(requireContext(),R.layout.item_spinner,arraylist_spinner1)
        binding!!.spinner1.adapter = spinneradapter
/*
        //var adapter1 = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item,arraylist_spinner1)
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter1

        adapter1.notifyDataSetChanged()
        spinner.setSelection(0)
*/

        binding!!.floatingActionButton!!.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        //메뉴 보이도록
        setHasOptionsMenu(true)

        return binding!!.root
    }

    //delete user때와 같은 menu를 추가해주겠습니다.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteAllUsers() //deleteAlluser 함수를 실행시킵니다.
        }
        return super.onOptionsItemSelected(item)
    }
    var checklistbox = pol3436.test.mvvmrecycler.fragments.list.ListAdapter


    private fun deleteAllUsers() { //deleteUser를 만들어줬을때와 같이 dialog를 만들겠습니다.
        val builder = AlertDialog.Builder(requireContext())
        if(checklistbox.checkboxList.size()!=0){

            builder.setPositiveButton("Yes") { _, _ ->
                //while문으로 리스트대로 삭제 진행
                var a = 0
                while (a != checklistbox.checkboxList.size()) {
                     //Log.d(TAG, ""+checklistbox.checkboxList.keyAt(a).toString())
                    mUserViewModel.selectdelete(checklistbox.userList[ checklistbox.checkboxList.keyAt(a)].id)
                   // checklistbox.checkboxList.delete(a)
                    a++
                }
                checklistbox.checkboxList.clear()
                Toast.makeText(
                    requireContext(), "Suscessfully removed select list ",
                    Toast.LENGTH_SHORT
                ).show()

            }
            builder.setNegativeButton("No") { _, _ ->
            }

            builder.setTitle("Delete delete select list?")
            builder.setMessage("Are you sure to delete select list?")
            builder.create().show()
        }
        else {
            builder.setPositiveButton("Yse") { _, _ ->
                mUserViewModel.deleteAllUsers()
                Toast.makeText(
                    requireContext(), "Suscessfully removed everything ",
                    Toast.LENGTH_SHORT
                ).show()
            }
            builder.setNegativeButton("No") { _, _ ->
            }

            builder.setTitle("Delete delete everything?")
            builder.setMessage("Are you sure to delete everything?")
            builder.create().show()
        }
    }

    //추가 끝
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}