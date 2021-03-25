package rajeshfsd.contacts

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ContactsApplication : Application() {

    // No need to cancel this scope as it'll be torn down with the process
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ContactsDatabase.getDatabase(this,applicationScope) }
    val repository by lazy { ContactsRepository(database.contactDao()) }

}