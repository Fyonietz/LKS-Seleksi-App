package winform.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lks_latihan_app.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import winform.adapters.OrderAdapter
import winform.models.*
import winform.utils.SessionManager
import winform.network.RetrofitClient
class StatusOrderFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private val orderList = mutableListOf<Order>()
    private lateinit var adapter: OrderAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val view = inflater.inflate(R.layout.fragment_status_order, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)

        adapter = OrderAdapter(orderList)
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = adapter

        loadOrders()

        return view
    }

    private fun loadOrders() {
        CoroutineScope(Dispatchers.Main).launch {
            try {

                val response = RetrofitClient.api
                    .getOrdersByPelanggan(SessionManager.clientId)

                if (response.isSuccessful) {
                    orderList.clear()
                    orderList.addAll(response.body()!!)
                    adapter.notifyDataSetChanged()
                }

            } catch (e: Exception) {
                Toast.makeText(context, e.message, Toast.LENGTH_LONG).show()
            }
        }
    }
}
