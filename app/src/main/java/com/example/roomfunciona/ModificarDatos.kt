package com.example.roomfunciona

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.roomfunciona.databinding.ActivityBorrarDatosBinding
import com.example.roomfunciona.databinding.ActivityModificarDatosBinding
import kotlinx.coroutines.launch

class ModificarDatos : AppCompatActivity() {

    lateinit var binding: ActivityModificarDatosBinding;
    private var amigoId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityModificarDatosBinding.inflate(layoutInflater)
        setContentView(binding.root)

        amigoId = intent.getIntExtra("id", 0)

        binding.btnModDatos.setOnClickListener {
            modificarContacto()

        }
    }


    private fun modificarContacto(){

        val email = binding.modEmailTxt.text.toString()
        val nombre = binding.modNombreTxt.text.toString()

        if( email.isNotEmpty() && nombre.isNotEmpty() ){

            lifecycleScope.launch {
                try{
                    val amigo = RoomApp.db.misAmigosDao().getPorId( amigoId )

                    amigo.email = email
                    amigo.nombre = nombre

                        RoomApp.db.misAmigosDao().update( amigo )
                }
                catch(e: Exception) {
                    Toast.makeText(this@ModificarDatos, "Error al modificar el amigo", Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            Toast.makeText(this@ModificarDatos, "Rellena los campos", Toast.LENGTH_SHORT).show()
        }

    }
}