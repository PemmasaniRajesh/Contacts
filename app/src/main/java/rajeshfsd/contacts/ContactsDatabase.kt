package rajeshfsd.contacts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope

@Database(entities = arrayOf(Contact::class), version = 1, exportSchema = false)
public abstract class ContactsDatabase : RoomDatabase() {

    public abstract fun contactDao():ContactDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: ContactsDatabase? = null

        fun getDatabase(context: Context,scope:CoroutineScope): ContactsDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactsDatabase::class.java,
                    "contacts_database"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}