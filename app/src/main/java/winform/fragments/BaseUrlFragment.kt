package winform.fragments

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.lks_latihan_app.R
import winform.fragments.RegisterFragments
import winform.fragments.MakeOrderFragment
import winform.network.RetrofitClient
import winform.utils.SessionManager
import kotlinx.coroutines.*

class BaseUrlFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_base_url, container, false)

        val etBaseUrl = view.findViewById<EditText>(R.id.etBaseUrl)
        val btnSave = view.findViewById<Button>(R.id.btnSaveBaseUrl)

        btnSave.setOnClickListener {
            val url = etBaseUrl.text.toString().trim()
            if (url.isNotEmpty()) {
                // Save in RetrofitClient
                RetrofitClient.setBaseUrl(url)
                Toast.makeText(context, "Base URL saved!", Toast.LENGTH_SHORT).show()

                // Navigate to login/register screen
                parentFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, LoginFragment())
                    .commit()
            } else {
                Toast.makeText(context, "Enter valid URL", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }}