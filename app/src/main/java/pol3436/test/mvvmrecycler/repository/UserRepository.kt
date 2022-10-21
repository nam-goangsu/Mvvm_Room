package pol3436.test.mvvmrecycler.repository

import androidx.lifecycle.LiveData
import pol3436.test.mvvmrecycler.data.UserDao
import pol3436.test.mvvmrecycler.model.User

class UserRepository(private val userDao: UserDao) {
    val readAllData: LiveData<List<User>> = userDao.readAllData()

    suspend fun addUser(user: User){ //suspend를 붙여준 이유는 coroutine을 사용하기 위함입니다.
        userDao.addUser(user) //DAO에서 만들었던 adduser을 실행합니다.
    }
    suspend fun updateUser(user: User){
        userDao.updateUser(user)
    }

    fun deleteUser(user:User) { //
        userDao.deleteUser(user)
    }

    fun deleteAllUsers() { //deleteAllUser을 실행합니다.
        userDao.deleteAllUsers()
    }
}