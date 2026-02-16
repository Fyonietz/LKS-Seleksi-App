package winform.network
import winform.models.Client
import winform.models.Order
import winform.models.User
import retrofit2.Response
import retrofit2.http.*
import winform.models.CreateOrderRequest
import winform.models.UpdateOrderStatusRequest

interface ApiService {
    @POST("api/v2/auth/register")
    suspend fun register(@Body client: Client): Response<Client>

    @POST("api/v2/auth/login")
    suspend fun login(@Body request: Map<String, String>): Response<User>
    @GET("api/v2/orders/")
    suspend fun getAllOrders(): Response<List<Order>>

    @GET("api/v2/orders/pelanggan/{pelangganId}")
    suspend fun getOrdersByPelanggan(
        @Path("pelangganId") pelangganId: Int
    ): Response<List<Order>>

    @POST("api/v2/orders/")
    suspend fun createOrder(
        @Body request: CreateOrderRequest
    ): Response<Map<String, Any>>

    @PUT("api/v2/orders/{orderId}/status")
    suspend fun updateOrderStatus(
        @Path("orderId") orderId: Int,
        @Body request: UpdateOrderStatusRequest
    ): Response<Map<String, Any>>

    @DELETE("api/v2/orders/{orderId}")
    suspend fun deleteOrder(
        @Path("orderId") orderId: Int
    ): Response<Map<String, Any>>
}