package rajeshfsd.contacts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.viewModels
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import rajeshfsd.contacts.databinding.FragmentContactBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "contact"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactFragment : DialogFragment() {
    // TODO: Rename and change types of parameters
    private var param1: Contact? = null

    private var _binding: FragmentContactBinding? = null

    private val binding get() = _binding!!

    private lateinit var navController : NavController

    private var filePath: String?=null

    private val contactsViewModel: ContactsViewModel by viewModels {
        ContactsViewModelFactory((requireActivity().application as ContactsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getParcelable(ARG_PARAM1)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentContactBinding.inflate(inflater, container, false)
        initGUI()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_container)
        // We use a String here, but any type that can be put in a Bundle is supported
        navController.currentBackStackEntry?.savedStateHandle?.getLiveData<String>("filePath")?.observe(
            viewLifecycleOwner) { result ->
            // Do something with the result.
            System.out.println("Result"+result)
            filePath = result
            Glide.with(requireContext()).load(result).circleCrop().into(binding.ivContactPhoto)
        }

    }

    private fun initGUI(){

        var menu = binding.toolbarContact.menu

        var deleteMenuItem = menu.findItem(R.id.delete)

        if(param1!=null){
            binding.etContactName.setText(param1!!.nm)
            binding.etContactMob.setText(param1!!.mob)
            Glide.with(requireContext()).load(param1!!.fp).circleCrop().into(binding.ivContactPhoto)
            binding.toolbarContact.setTitle("Edit Contact")
            deleteMenuItem.setVisible(true)
        }else{
            deleteMenuItem.setVisible(false)
        }

        binding.toolbarContact.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.delete -> {
                    contactsViewModel.deleteContact(param1!!)
                    Toast.makeText(requireContext(),"Deleted Successfully",Toast.LENGTH_SHORT).show()
                    dialog!!.dismiss()
                    true
                }
                else -> false
            }
        }

        binding.ivContactPhoto.setOnClickListener {
            val _builder = MaterialAlertDialogBuilder(requireContext())
            _builder.setTitle("Change Photo")
            val _options = arrayOf<CharSequence>("Take Photo", "Choose Photo")
            _builder.setItems(_options) { dialog, which ->
                //Toast.makeText(requireContext(), _options[which], Toast.LENGTH_LONG).show()
                if(which==0){
                    navController.navigate(R.id.contact_Fragment_to_camera_fragment)
                }
            }
            _builder.setPositiveButton("Cancel") { dialog, i ->
               // Toast.makeText(requireContext(), "Cancel", Toast.LENGTH_LONG).show()
            }
            _builder.create().show()
        }

        binding.toolbarContact.setNavigationOnClickListener({
            dismiss()
        })

        binding.btnContactSave.setOnClickListener {

            val name:String = binding.etContactName.text.toString().trim()
            val mob:String  = binding.etContactMob.text.toString().trim()
            val isFormValid:MutableList<Boolean> = mutableListOf<Boolean>()

            if(name.isEmpty()){
                binding.tlContactName.error = "Name is required"
                binding.etContactName.requestFocus()
                isFormValid.add(false)
            }else{
                binding.tlContactName.isErrorEnabled=false
                isFormValid.add(true)
            }

            if(mob.isBlank()){
                binding.tlContactMob.error = "Mob is required"
                binding.etContactMob.requestFocus()
                isFormValid.add(false)
            }else{
                isFormValid.add(true)
                binding.tlContactMob.isErrorEnabled=false
            }

            if(isFormValid.contains(false)){
                return@setOnClickListener
            }

            val contact = Contact(nm = name,mob = mob,fp = filePath,fav = false)

            if (param1!=null){
                contact.id = param1!!.id
                if(contact.fp==null){
                    contact.fp = param1!!.fp
                }
                contact.fav = param1!!.fav
                contactsViewModel.updateContact(contact)
            }else{
                contactsViewModel.insertContact(contact)
            }

            Toast.makeText(requireContext(),"Saved Successfully",Toast.LENGTH_SHORT).show()

            dismiss()

        }

    }

    override fun getTheme(): Int {
        return R.style.DialogTheme
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                }
            }
    }
}