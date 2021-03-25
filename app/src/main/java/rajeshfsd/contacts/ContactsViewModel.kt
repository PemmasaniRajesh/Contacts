package rajeshfsd.contacts

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactsViewModel(private val contactsRepository: ContactsRepository) : ViewModel() {

    fun insertContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.insert(contact)
    }

    fun getAllContacts(): LiveData<List<Contact>> {
        return contactsRepository.getAllContacts()
    }

    fun getAllFavContacts(): LiveData<List<Contact>> {
        return contactsRepository.getAllFavContacts()
    }

    fun updateContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.update(contact)
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        contactsRepository.delete(contact)
    }

}

class ContactsViewModelFactory(private val repository: ContactsRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ContactsViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
