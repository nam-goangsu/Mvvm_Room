package pol3436.test.mvvmrecycler.fragments.add

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add.*
import pol3436.test.mvvmrecycler.R
import pol3436.test.mvvmrecycler.databinding.FragmentAddBinding
import pol3436.test.mvvmrecycler.model.User
import pol3436.test.mvvmrecycler.viewmodel.UserViewModel

class AddFragment : Fragment() {
    private var _binding : FragmentAddBinding?=null
    private val binding get() = _binding
    private lateinit var mUserViewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)
        binding!!.addButton.setOnClickListener {
            insertDataToDatabase()
        }

        return binding?.root
    }
    private fun insertDataToDatabase(){
        val firstName = binding!!.editTextTextPersonName.text.toString()
        val lastName = binding!!.editTextTextPersonName2.text.toString()
        val age = binding!!.editTextNumber.text

        if(inputCheck(firstName,lastName,age)){
            val user = User(0,firstName,lastName,Integer.parseInt(age.toString()))

            mUserViewModel.addUser(user)
            //토스트 메세지입니다.
            Toast.makeText(requireContext(),"Successfully added!", Toast.LENGTH_LONG).show()
            //다시 listfragment로 돌려보냅니다.
            findNavController().navigate(R.id.action_addFragment_to_listFragment)


        }else{
            //만약 유저가 editText모두 넣지 않았다면 토스트 메세지를 띄웁니다.
            Toast.makeText(requireContext(), "Please fill out all fields.", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return  !(TextUtils.isEmpty(firstName)&&TextUtils.isEmpty(lastName)&&age.isEmpty())

    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}