package rajeshfsd.contacts

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ContactDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(contact: Contact)

    @Update
    fun update(contact: Contact)

    @Delete
    fun delete(contact: Contact)

    @Query("SELECT * FROM contacts WHERE id=:id")
    fun getContactById(id: Int):LiveData<Contact>

    @Query("SELECT * FROM contacts")
    fun getAll():LiveData<List<Contact>>

    @Query("SELECT * FROM contacts WHERE fav=1")
    fun getAllFavourite():LiveData<List<Contact>>
}