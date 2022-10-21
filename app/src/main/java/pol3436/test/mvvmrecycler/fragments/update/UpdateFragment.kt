package pol3436.test.mvvmrecycler.fragments.update

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.ListFragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import kotlinx.android.synthetic.main.fragment_update.*
import pol3436.test.mvvmrecycler.R
import pol3436.test.mvvmrecycler.databinding.FragmentUpdateBinding
import pol3436.test.mvvmrecycler.model.User
import pol3436.test.mvvmrecycler.viewmodel.UserViewModel


class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding

    private val args by navArgs<UpdateFragmentArgs>()


    private lateinit var mUserViewModel: UserViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)

        mUserViewModel = ViewModelProvider(this).get(UserViewModel::class.java)

        binding!!.updateFirstNameEt.setText(args.currentUser.firstName)
        binding!!.updateLastNameEt.setText(args.currentUser.lastName)
        binding!!.updateAgeEt.setText(args.currentUser.age.toString())


        binding!!.updateButton.setOnClickListener {
            updateItem()
        }
        setHasOptionsMenu(true)
        return binding?.root
    }

    private fun updateItem() {

//        변경된 값들을 editText에서 가져옵니다.
        val firstName = updateFirstName_et.text.toString()
        val lastName = updateLastName_et.text.toString()
        val age = Integer.parseInt(updateAge_et.text.toString())

        if (inputCheck(firstName, lastName, updateAge_et.text)) {

            //updatedUser는 업데이트된 데이터입니다.
            val updatedUser = User(args.currentUser.id, firstName, lastName, age)

            //updateUser쿼리를 만들어서 Update Query를 이용하여 database에 추가해줘야합니다.
            //Update 쿼리는 DAO에서 추가해야합니다.
            //지금은 viewModel에 update 쿼리가 생기면 updatedUser가 전달되도록 구현만 해놓겠습니다.

            mUserViewModel.updateUser(updatedUser)

            Toast.makeText(requireContext(), "UpdatedSuccessfully", Toast.LENGTH_SHORT).show()

            //navigate back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)

        } else {

            //EditText가 빈칸이면 토스트 메세지
            Toast.makeText(requireContext(), "Please fill out all field", Toast.LENGTH_SHORT).show()
        }
    }

    //editText가 비어있는지 확인하는 함수
    private fun inputCheck(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }
    //추가 시작

    //action menu resource파일을 연결해줍니다.
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    //action menu에 아이템이 클릭되었을때
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //id.menu_delete이 클릭되면 deleteUser함수 실행
        if (item.itemId == R.id.menu_delete) {
            deleteUser()//아래에 이어서 만들어보겠습니다.
        }

        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("UseRequireInsteadOfGet")
    private fun deleteUser() { //delteIcon이 나오면 Dialog를 띄워서 물어보겠습니다.
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            //yes클릭시 viewMOdel.deleteUser를 실행시킵니다. args.currentUser를 삭제하게됩니다.
            mUserViewModel.deleteUser(args.currentUser)
            Toast.makeText(
                requireContext(),
                "Suscessfully removed: ${args.currentUser.firstName}",
                Toast.LENGTH_SHORT
            ).show()

            findNavController().navigate(R.id.action_updateFragment_to_listFragment)


        }
        builder.setNegativeButton("No") { _, _ ->
            //아무일도 일어나지않습니다.
        }

        //dialog의 UI세팅입니다.
        builder.setTitle("Delete ${args.currentUser.firstName}?")
        builder.setMessage("Are you sure to delete ${args.currentUser.firstName}")

        //dialog가 UI에 보여집니다.
        builder.create().show()
    }
    //추가 끝
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
/*
    override fun onResume() {

        super.onResume()
        binding!!.updateFirstNameEt.setText(null)
        binding!!.updateLastNameEt.setText(null)
        binding!!.updateAgeEt.setText(null)
    }*/
}