package com.example.proyecto;

import android.content.Intent;
import android.content.SharedPreferences;
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
import java.util.List;

public class MainActivity extends AppCompatActivity{
    DBHelper_auth dbHelperAuth;
    Button btnIngresar, btn_registrar ;
    EditText txtUsuario, txtContrasena;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        // Inicializar DBHelper_auth
        dbHelperAuth = new DBHelper_auth(this);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtUsuario = (EditText)findViewById(R.id.txtUsuario);
        txtContrasena = (EditText)findViewById(R.id.txtContrasena);
        btnIngresar = (Button) findViewById(R.id.btnIngresar);
        btn_registrar=(Button) findViewById(R.id.btn_registrar);
        btnIngresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer userId = dbHelperAuth.verificarUsuario(txtUsuario.getText().toString(), txtContrasena.getText().toString());
                if (userId != null){
                    // Guardar estado de inicio de sesión e ID del usuario en SharedPreferences
                    SharedPreferences preferences = getSharedPreferences("user_data", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putBoolean("isLoggedIn", true);
                    editor.putInt("userId", userId);
                    editor.apply();

                    // Redirigir al menú principal
                    Intent objintent = new Intent(MainActivity.this, Menu.class);
                    MainActivity.this.startActivity(objintent);
                    finish(); // Finaliza MainActivity para evitar que el usuario regrese a ella con el botón de retroceso
                } else {
                    Toast.makeText(MainActivity.this, "Usuario incorrecto o contraseña incorrecta", Toast.LENGTH_LONG).show();
                }
            }
        });



        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentReg = new Intent(MainActivity.this,RegistroUsuario.class);
                MainActivity.this.startActivity(intentReg);
            }
        });
    }
}