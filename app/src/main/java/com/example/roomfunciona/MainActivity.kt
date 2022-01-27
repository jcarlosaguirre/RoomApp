package com.example.roomfunciona

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginLeft
import androidx.lifecycle.lifecycleScope
import com.example.roomfunciona.RoomApp.Companion.db
import com.example.roomfunciona.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var lista: LinearLayout

    companion object {

        lateinit var activityInstance: MainActivity

        fun actualizarLista( ctx: Context, lista: LinearLayout ){
            activityInstance.actualizarLista( ctx, lista )
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        activityInstance = this

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        lista = binding.contactsList


        binding.btGuardar.setOnClickListener {
            if (binding.etNombre.text.isNotBlank() &&
                binding.etEmail.text.isNotBlank()) {
                    addAmigo(MisAmigos(nombre = binding.etNombre.text.toString(), email = binding.etEmail.text.toString()))
            }

        }

        binding.btConsultar.setOnClickListener {
            actualizarLista( this, lista )
        }

        binding.btIrABorrar.setOnClickListener{
            val intent = Intent(this, BorrarDatos::class.java)
            startActivity(intent)
        }

        binding.btIrAModificar.setOnClickListener{
            Toast.makeText( this@MainActivity, "Selecciona el contacto a modificar", Toast.LENGTH_LONG).show()
        }

    }

    private fun addAmigo(miAmigo: MisAmigos) {
        lifecycleScope.launch {
            db.misAmigosDao().insertar(miAmigo)
        }
    }


    private fun actualizarLista( activityCtx: Context, lista: LinearLayout ){

        lista.removeAllViews()

        lifecycleScope.launch {
            val todos = db.misAmigosDao().getTodo()
            var i = 0;

            for( amigo in todos ) {
                // Crea un textview por cada contacto
                var textView = TextView( activityCtx )
                textView.setPadding(0, 20, 0, 20)

                textView.text = amigo.id.toString() + "  -  " + amigo.nombre + "         " + amigo.email

                textView.setOnClickListener {

                    val intent = Intent(this@MainActivity, ModificarDatos::class.java)

                    intent.putExtra("id", amigo.id )
                    startActivity(intent).apply {

                    }
                }

                lista.addView( textView )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        actualizarLista( this, lista )
    }
}