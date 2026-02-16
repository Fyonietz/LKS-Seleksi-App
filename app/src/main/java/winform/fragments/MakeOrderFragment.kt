package winform.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.lks_latihan_app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import winform.models.*
import winform.utils.SessionManager
import winform.network.RetrofitClient

class MakeOrderFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_make_order, container, false)

        val tvWelcome = view.findViewById<TextView>(R.id.tvWelcome)
        val etWeight = view.findViewById<EditText>(R.id.etWeight)
        val btnSubmit = view.findViewById<Button>(R.id.btnSubmit)
        val btnStatus = view.findViewById<Button>(R.id.btnStatus)
        val btnLogout = view.findViewById<Button>(R.id.btnLogout)

        tvWelcome.text = "Welcome ${SessionManager.clientName}"

        btnSubmit.setOnClickListener {
            val weightText = etWeight.text.toString()
            if (weightText.isNotEmpty()) {
                createOrder(weightText.toDouble())
            } else {
                Toast.makeText(context, "Enter weight", Toast.LENGTH_SHORT).show()
            }
        }

        btnStatus.setOnClickListener {
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, StatusOrderFragment())
                .addToBackStack(null)
                .commit()
        }

        btnLogout.setOnClickListener {
            SessionManager.clear()

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer, LoginFragment())
                .commit()
        }

        return view
    }

    private fun createOrder(weight: Double) {

        CoroutineScope(Dispatchers.Main).launch {
            try {

                val request = CreateOrderRequest(
                    pelanggan_Id = SessionManager.clientId,
                    weight = weight
                )

                val response = RetrofitClient.api.createOrder(request)

                if (response.isSuccessful) {
                    Toast.makeText(context, "Order Created", Toast.LENGTH_SHORT).show()
                } else {
                    val errorBody = response.errorBody()?.string()
                    Log.d("DEBUG", "Sending Pelanggan_Id = ${SessionManager.clientId}")

                    Toast.makeText(context, "Error: $errorBody", Toast.LENGTH_LONG).show()
                }

            } catch (e: Exception) {
                Toast.makeText(context, "Exception: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }
    }


}
