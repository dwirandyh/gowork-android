package com.publisher.androidapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.publisher.androidapp.R;
import com.publisher.androidapp.model.TunaKarya;
import com.publisher.androidapp.service.TunaKaryaService;
import com.publisher.androidapp.utils.Constant;
import com.publisher.androidapp.utils.Injector;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText editTextEmail, editTextPassword;
    Button btnLogin;
    TextView textViewRegistrasi;

    Context context;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        if (isLogged()){
            openMainActivity();
        }
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.edittext_nama);
        editTextPassword = findViewById(R.id.edittext_password);
        btnLogin = findViewById(R.id.btn_login);
        textViewRegistrasi = findViewById(R.id.textview_registrasi);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        textViewRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, RegistrasiActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isLogged(){
        if (Prefs.getInt(Constant.STR_ID, 0) == 0){
            return false;
        }else{
            return true;
        }
    }

    private void login(){
        if (editTextEmail.getText().toString().equals("")){
            Toast.makeText(context, "Email harus diisi", Toast.LENGTH_SHORT).show();
        }else if (editTextPassword.getText().toString().equals("")){
            Toast.makeText(context, "Password harus diisi", Toast.LENGTH_SHORT).show();
        }else{
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Loading..."); // Setting Message
            progressDialog.setTitle("Autentikasi ke server"); // Setting Title
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
            progressDialog.show();

            loginService();
        }
    }

    private void loginService(){
        TunaKaryaService service = Injector.tunaKaryaService();
        service.login(editTextEmail.getText().toString(), editTextPassword.getText().toString()).enqueue(new Callback<TunaKarya>() {
            @Override
            public void onResponse(Call<TunaKarya> call, Response<TunaKarya> response) {
                progressDialog.dismiss();
                if (response.body() != null){
                    Prefs.putInt(Constant.STR_ID, response.body().getId());
                    Prefs.putString(Constant.STR_HAK_AKSES, response.body().getHakAkses());

                    openMainActivity();
                }
            }

            @Override
            public void onFailure(Call<TunaKarya> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, "Email atau password anda salah", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openMainActivity(){
        Intent intent = new Intent(context, MainActivity.class);
        startActivity(intent);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            finishAffinity();
            System.exit(0);
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}
