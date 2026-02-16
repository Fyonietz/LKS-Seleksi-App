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

class LoginFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnLogin = view.findViewById<Button>(R.id.btnLogin)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)

        btnLogin.setOnClickListener {
            login(etName.text.toString(), etPassword.text.toString())
        }

        btnRegister.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, RegisterFragments())
                .addToBackStack(null)
                .commit()
        }

        return view
    }

    private fun login(name: String, password: String) {
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = RetrofitClient.api.login(
                    mapOf("name" to name, "password" to password)
                )

                if (response.isSuccessful) {
                    val user = response.body()!!
                    SessionManager.clientId = user.id   // <-- this is Pelanggan_Id
                    SessionManager.clientName = user.name


                    Toast.makeText(context, "Welcome ${user.name}", Toast.LENGTH_SHORT).show()

                    parentFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, MakeOrderFragment())
                        .commit()
                } else {
                    Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
