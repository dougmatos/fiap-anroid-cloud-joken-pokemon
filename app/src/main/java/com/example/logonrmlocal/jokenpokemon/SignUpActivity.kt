package com.example.logonrmlocal.jokenpokemon

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.logonrmlocal.jokenpokemon.model.Usuario
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sing_up.*

class SignUpActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sing_up)

        mAuth = FirebaseAuth.getInstance()

        btCriar.setOnClickListener{
            mAuth.createUserWithEmailAndPassword(
                    etEmail.text.toString(),
                    etSenha.text.toString()
            ).addOnCompleteListener {
                if(it.isSuccessful){
                    salvaNoRealtimeDatabase()
                } else{
                    Toast.makeText(
                        this@SignUpActivity, it.exception?.message, Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun salvaNoRealtimeDatabase(){

        val user = Usuario(
                etNome.text.toString(),
                etEmail.text.toString(),
                etTelefone.text.toString()
        )

        FirebaseDatabase
                .getInstance()
                .getReference("Usuario")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .setValue(user)
                .addOnCompleteListener {
                    if(it.isSuccessful){
                        Toast.makeText(
                                this@SignUpActivity,
                                "usário cadastrado com sucesso!",
                                Toast.LENGTH_LONG
                        ).show()

                        val itent = Intent()
                        itent.putExtra("email", etEmail.text.toString())
                        intent.putExtra("senha", etSenha.text.toString())
                        setResult(Activity.RESULT_OK)
                        finish()
                    } else{
                        Toast.makeText(
                            this@SignUpActivity, it.exception?.message, Toast.LENGTH_LONG
                        ).show()
                    }
                }
    }
}
