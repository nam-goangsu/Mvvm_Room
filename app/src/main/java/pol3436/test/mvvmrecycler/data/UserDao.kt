package pol3436.test.mvvmrecycler.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.*
import pol3436.test.mvvmrecycler.model.User

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addUser(user: User)

    //추가시작
    @Update
    fun updateUser(user:User) //suspend 코루틴사용됨
    //추가끝

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<User>>

    @Query("SELECT firstName FROM user_table ORDER BY id ASC")
    fun spinnerData(): LiveData<List<String>>

    //선택한 User를 지웁니다.
    @Delete
     fun deleteUser(user:User)

   @Query("DELETE FROM user_table WHERE id =:id")
    fun selectData(id : Int): Int

    //모두지웁니다.
    @Query("DELETE FROM user_table")
     fun deleteAllUsers()
}