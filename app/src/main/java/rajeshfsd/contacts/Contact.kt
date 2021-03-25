package rajeshfsd.contacts

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "Contacts",indices = arrayOf(Index(value = ["fav"])))
data class Contact(
    @PrimaryKey(autoGenerate = true) var id:Int?,
    @ColumnInfo(name = "nm") var nm: String?,
    @ColumnInfo(name = "mob") var mob: String?,
    @ColumnInfo(name = "file_path") var fp:String?,
    @ColumnInfo(name = "fav",defaultValue = "false") var fav:Boolean?
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readValue(Int::class.java.classLoader) as? Int,
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readValue(Boolean::class.java.classLoader) as? Boolean) {
    }

    constructor(nm: String, mob: String, fp: String?, fav: Boolean) : this(id=null,nm,mob,fp,fav)

    override fun describeContents(): Int {
        TODO("Not yet implemented")
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("Not yet implemented")
    }

    companion object CREATOR : Parcelable.Creator<Contact> {
        override fun createFromParcel(parcel: Parcel): Contact {
            return Contact(parcel)
        }

        override fun newArray(size: Int): Array<Contact?> {
            return arrayOfNulls(size)
        }
    }
}
