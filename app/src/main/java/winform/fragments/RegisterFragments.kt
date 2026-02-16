package winform.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lks_latihan_app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import winform.models.Client
import winform.network.RetrofitClient


class RegisterFragments : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_register, container, false)

        val etName = view.findViewById<EditText>(R.id.etName)
        val etCall = view.findViewById<EditText>(R.id.etCall)
        val etPassword = view.findViewById<EditText>(R.id.etPassword)
        val btnRegister = view.findViewById<Button>(R.id.btnRegister)

        btnRegister.setOnClickListener {
            register(
                etName.text.toString(),
                etCall.text.toString(),
                etPassword.text.toString()
            )
        }

        return view
    }

    private fun register(name: String, call: String, password: String) {

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val client = Client(
                    name = name,
                    call_number = call,
                    password = password
                )

                val response = RetrofitClient.api.register(client)

                if (response.isSuccessful) {
                    Toast.makeText(context, "Register Success", Toast.LENGTH_SHORT).show()

                    parentFragmentManager.popBackStack()

                } else {
                    Toast.makeText(context, "Register Failed", Toast.LENGTH_SHORT).show()
                }

            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
