package pol3436.test.mvvmrecycler.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import pol3436.test.mvvmrecycler.data.UserDatabase
import pol3436.test.mvvmrecycler.model.User
import pol3436.test.mvvmrecycler.repository.UserRepository

class UserViewModel (application: Application): AndroidViewModel(application) {

     val readAllData: LiveData<List<User>>
     val spinnerData : LiveData<List<String>>

    private val repository: UserRepository

    init {
        val userDao = UserDatabase.getDatabase(application).userDao()
        repository = UserRepository(userDao) //이니셜라이즈 해줍니다.
        readAllData = repository.readAllData // readAlldata는 repository에서 만들어줬던 livedata입니다.
        spinnerData = repository.spinnerData
    }

    fun addUser(user: User){// 파라미터에 만든 데이터클래스가 들어갑니다.
        viewModelScope.launch(Dispatchers.IO) { //코루틴 활성화 dispatcherIO는 백그라운드에서 실행
            repository.addUser(user) //repository에 adduser함수 불러오기
        }
    }
    fun updateUser(user:User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateUser(user)
        }
    }
    fun deleteUser(user:User){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(user)
        }
    }

    fun selectdelete(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.selectData(id)
        }
    }

    fun deleteAllUsers(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()

        }
    }

}