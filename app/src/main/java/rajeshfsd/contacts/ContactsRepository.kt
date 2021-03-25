package rajeshfsd.contacts

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class ContactsRepository(private val contactDao: ContactDao) {

    /*init {
        val db = ContactsDatabase.getDatabase(context)
        contactDao = db.contactDao()
    }*/

    fun getAllContacts(): LiveData<List<Contact>> {
        return contactDao.getAll()
    }

    fun getAllFavContacts(): LiveData<List<Contact>> {
        return contactDao.getAllFavourite()
    }

    // By default Room runs suspend queries off the main thread, therefore, we don't need to
    // implement anything else to ensure we're not doing long running database work
    // off the main thread.
    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(contact: Contact) {
        contactDao.insert(contact)
    }

    @WorkerThread
    suspend fun update(contact: Contact) {
        contactDao.update(contact)
    }

    @WorkerThread
    suspend fun delete(contact: Contact) {
        contactDao.delete(contact)
    }

    @WorkerThread
    fun getContactById(id: Int): LiveData<Contact> {
        return contactDao.getContactById(id)
    }

}