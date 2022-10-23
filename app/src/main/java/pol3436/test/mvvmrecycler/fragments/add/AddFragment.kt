package pol3436.test.mvvmrecycler.fragments.add

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
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
    lateinit var imm : InputMethodManager
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
        binding!!.editTextTextPersonName.requestFocus()

        imm = getActivity()?.getApplicationContext()?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
//https://all-dev-kang.tistory.com/entry/%EC%95%88%EB%93%9C%EB%A1%9C%EC%9D%B4%EB%93%9Cfragment-%EB%82%B4%EC%97%90%EC%84%9C-%ED%82%A4%EB%B3%B4%EB%93%9C-%EC%A1%B0%EC%A0%95%ED%95%98%EA%B8%B0%EB%82%B4%EB%A6%AC%EA%B8%B0
        //https://win-record.tistory.com/52
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