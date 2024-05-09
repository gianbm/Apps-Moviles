package com.gbm.sem3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.Toast
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {
    val database = FirebaseDatabase.getInstance()
    val reference= database.getReference("Producto")
    private lateinit var editTextNombre: EditText
    private lateinit var botonAgregar: Button
    private lateinit var lista: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextNombre = findViewById(R.id.textName)
        botonAgregar= findViewById(R.id.btnAdd)
        lista= findViewById(R.id.lista)

        botonAgregar.setOnClickListener(){
            agregarProducto()
            editTextNombre.setText(null)
        }

        val productList = mutableListOf<String>()

        reference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                productList.clear()
                for (dataSnapshot in snapshot.children){
                    val productoAListar = dataSnapshot.getValue(Producto::class.java)
                    productoAListar?.let {
                        val nombre = "Nombre: ${it.nombre}"
                        productList.add(nombre)
                    }
                }

                //llenar listView con la lista de arriba
                val adapter = ArrayAdapter<String>(this@MainActivity, android.R.layout.simple_list_item_1,productList)
                lista.adapter= adapter

            }

            override fun onCancelled(error: DatabaseError) {
                editTextNombre.setText(null)
            }
        })


    }

    fun agregarProducto(){
        val producto= Producto(editTextNombre.text.toString())
        val productoId = reference.push().key
        reference.child(productoId!!).setValue(producto)
    }
}

data class Producto(var nombre: String){
    constructor():this("")
}