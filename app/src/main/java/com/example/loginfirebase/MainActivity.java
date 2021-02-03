package com.example.loginfirebase;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private EditText TextEmail, TextPassword;
    private Button btnRegistrar;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        TextEmail = (EditText) findViewById(R.id.inMail);
        TextPassword = (EditText) findViewById(R.id.inPass);

        btnRegistrar = (Button) findViewById(R.id.btnReg);

        progressDialog = new ProgressDialog(this);

        //attaching listener to button
        btnRegistrar.setOnClickListener(this);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //updateUI(currentUser);
    }

    @Override
    public void onClick(View v) {
        //Invocamos al método:
        createAccount();
    }

    private void createAccount() {
        //Obtenemos el mail y la contraseña de las cajas de texto
        String email = TextEmail.getText().toString();
        String password = TextPassword.getText().toString();

        //Verificamos que las cajas de texto no estén vacías
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Usuario vacío", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Contraseña vacía", Toast.LENGTH_LONG).show();
            return;
        }

        progressDialog.setMessage("Realizando registro en linea...");
        progressDialog.show();


        //Creando un nuevo usuario
        createAccountFirebase(email, password);

    }

    private void createAccountFirebase(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        progressDialog.dismiss();
                    }
                });
    }

    public void loginUser(View v) {
        String email = TextEmail.getText().toString();
        String password = TextPassword.getText().toString();

        //Verificamos que las cajas de texto no estén vacías
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Usuario vacío", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(this, "Contraseña vacía", Toast.LENGTH_LONG).show();
            return;
        }
        signInFirebaseUser(email, password);

    }

    private void signInFirebaseUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            redirectApp();

                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(MainActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            //updateUI(null);
                        }

                        progressDialog.dismiss();
                    }
                });
    }
    private void redirectApp(){
        Intent i = new Intent(this, MainActivity2.class);
        startActivity(i);
    }
}