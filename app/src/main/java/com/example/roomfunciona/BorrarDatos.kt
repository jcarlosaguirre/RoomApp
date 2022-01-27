package com.example.roomfunciona

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.roomfunciona.databinding.ActivityBorrarDatosBinding
import com.example.roomfunciona.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class BorrarDatos : AppCompatActivity() {

    lateinit var binding: ActivityBorrarDatosBinding;
    private lateinit var lista: LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBorrarDatosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lista = binding.listaAmigos

        actualizarLista()

        binding.btnBorrar.setOnClickListener {
            borrarContacto()
        }
    }

    private fun borrarContacto(){

        val amigoId = binding.txtIdAmigo.text.toString()

        lifecycleScope.launch {
            try{
                val amigo = RoomApp.db.misAmigosDao().getPorId( amigoId.toInt() )
                RoomApp.db.misAmigosDao().delete( amigo )
                actualizarLista()
            }
            catch(e: Exception) {
                Toast.makeText(this@BorrarDatos, "No existe ese id", Toast.LENGTH_SHORT).show()
            }
        }

    }

    private fun actualizarLista(){
        MainActivity.actualizarLista( this, lista )
        binding.txtIdAmigo.text = null
    }
}