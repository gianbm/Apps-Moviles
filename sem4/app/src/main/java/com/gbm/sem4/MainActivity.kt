package com.gbm.sem4
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var listView: ListView
    private lateinit var detailsContainer: LinearLayout
    private lateinit var dataName: TextView
    private lateinit var dataUsername: TextView
    private lateinit var dataPhone: TextView
    private lateinit var dataEmail: TextView
    private lateinit var detailDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listView)
        detailsContainer = findViewById(R.id.detailsContainer)
        dataName = findViewById(R.id.dataName)
        dataUsername = findViewById(R.id.dataUsername)
        dataPhone = findViewById(R.id.dataPhone)
        dataEmail = findViewById(R.id.dataEmail)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val apiService = retrofit.create(Api::class.java)
        val call = apiService.obtenerUsuarios()
        call.enqueue(object : Callback<List<Usuario>> {
            override fun onResponse(call: Call<List<Usuario>>, response:
            Response<List<Usuario>>
            ) {
                if (response.isSuccessful) {
// La solicitud fue exitosa, podemos acceder a los datos en
                    response.body()
                    val users = response.body()
                    val listName = users!!.map { "${it.name} "  }
                    val adapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_list_item_1, listName)
                    listView.adapter = adapter

                    listView.setOnItemClickListener { _, _, position, _ ->
                        // Cuando se selecciona un elemento de la lista, mostrar los detalles
                        val selectedProduct = users[position]
                        dataName.text = "Name: ${selectedProduct.name}"
                        dataUsername.text = "Username: ${selectedProduct.username}"
                        dataPhone.text = "Phone: ${selectedProduct.phone}"
                        dataEmail.text = "Email: ${selectedProduct.email}"

                        // Mostrar el contenedor de detalles
                        detailsContainer.visibility = View.VISIBLE


// Procesar los datos o actualizar la interfaz de usuario
                    }
                }  else {
// La solicitud no fue exitosa, manejar el error
                }
            }
            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
// Ocurrió un error en la comunicación con la API
// Manejar el error
            }
        })
    }
}
