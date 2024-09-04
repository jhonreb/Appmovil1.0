package com.example.proyecto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.proyecto.auth_user.DBHelper_auth;

import java.util.ArrayList;

public class RegistroUsuario extends AppCompatActivity{
    EditText txtusuarioR, txtcontrasenaR, txtcontrasenaRC;
    Button btnRegistrar, btnRegresar;
    DBHelper_auth dbHelperAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_registro_usuario);

        txtusuarioR = (EditText)findViewById(R.id.txtusuarioR);
        txtcontrasenaR = (EditText)findViewById(R.id.txtcontrasenaR);
        txtcontrasenaRC = (EditText)findViewById(R.id.txtcontrasenaRC);
        btnRegistrar = (Button)findViewById(R.id.btnRegistrar);
        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);
        btnRegresar = (Button)findViewById(R.id.btnRegresar);
        dbHelperAuth = new DBHelper_auth(this);

        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                regresar();
            }
        });

        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user, pwd, copwd;
                user = txtusuarioR.getText().toString();
                pwd = txtcontrasenaR.getText().toString();
                copwd = txtcontrasenaRC.getText().toString();
                if(user.equals("") || pwd.equals("") || copwd.equals("")){
                    Toast.makeText(RegistroUsuario.this,"Ingresar datos", Toast.LENGTH_LONG).show();
                }else{
                    if(pwd.equals(copwd)) {
                        if(dbHelperAuth.verificaremail(user)){
                            Toast.makeText(RegistroUsuario.this, "Usuario ya existe", Toast.LENGTH_LONG).show();
                            return;
                        }
                        //Proceso de registro
                        boolean registroexitoso = dbHelperAuth.inserData(user,pwd);
                        if(registroexitoso) {
                            Toast.makeText(RegistroUsuario.this, "Usuario registrado exitosamente", Toast.LENGTH_LONG).show();
                            txtusuarioR.setText("");
                            txtcontrasenaR.setText("");
                            txtcontrasenaRC.setText("");
                        }else
                            Toast.makeText(RegistroUsuario.this,"Fallo al registrar al usuario", Toast.LENGTH_LONG).show();
                    } else{
                        Toast.makeText(RegistroUsuario.this,"Password no coincide", Toast.LENGTH_LONG).show();
                    }

                }
            }
        });
    }
    public void regresar(){
        Intent objIntent = new Intent(RegistroUsuario.this,MainActivity.class);
        RegistroUsuario.this.startActivity(objIntent);
    }

}