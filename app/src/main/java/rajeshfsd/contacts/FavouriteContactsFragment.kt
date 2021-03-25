package rajeshfsd.contacts

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import rajeshfsd.contacts.databinding.FragmentAllContactsBinding
import rajeshfsd.contacts.databinding.FragmentFavouriteContactsBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FavouriteContactsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FavouriteContactsFragment : Fragment(),OnContactItemClickListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentFavouriteContactsBinding? = null
    private val binding get() = _binding!!

    private var contactsList : List<Contact>?=null
    private lateinit var navController : NavController

    private val contactsViewModel: ContactsViewModel by viewModels {
        ContactsViewModelFactory((requireActivity().application as ContactsApplication).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentFavouriteContactsBinding.inflate(inflater, container, false)
        initGUI()
        return binding.root
    }

    private fun initGUI() {

        val linearLayoutManager = LinearLayoutManager(requireContext())
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL

        binding.rvFavContactsList.layoutManager = linearLayoutManager
        binding.rvFavContactsList.setHasFixedSize(true)

        binding.rvFavContactsList.addItemDecoration(DividerItemDecoration(requireContext(), LinearLayoutManager.VERTICAL))

        val contactsListAdapter = ContactsListAdapter(requireContext(),this,true)
        binding.rvFavContactsList.adapter = contactsListAdapter

        contactsViewModel.getAllFavContacts().observe(requireActivity(), {response ->
            contactsListAdapter.setContacts(response)
        })
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(requireActivity(),R.id.nav_host_fragment_container)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FavouriteContactsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FavouriteContactsFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun onItemClicked(contact: Contact) {
        val bundle = Bundle();
        bundle.putParcelable("contact",contact)
        navController.navigate(R.id.favourite_contacts_fragment_to_contact_fragment,bundle)
    }

    override fun onUpdateFav(contact: Contact) {

    }
}