package com.dwirandyh.gowork.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.pixplicity.easyprefs.library.Prefs;
import com.dwirandyh.gowork.R;
import com.dwirandyh.gowork.activity.MainActivity;
import com.dwirandyh.gowork.model.TunaKarya;
import com.dwirandyh.gowork.service.TunaKaryaService;
import com.dwirandyh.gowork.utils.Constant;
import com.dwirandyh.gowork.utils.Injector;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrasiTunaKaryaFragment extends Fragment {

    EditText editTextNoKtp, editTextNama, editTextAlamat, editTextBidangKeahlian, editTextPendidikanTerakhir, editTextEmail, editTextPassword;
    Button btnRegistrasi;
    private ProgressDialog progressDialog;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_registrasi_tuna_karya, container, false);
        editTextNoKtp = rootView.findViewById(R.id.edittext_noktp);
        editTextNama = rootView.findViewById(R.id.edittext_nama);
        editTextAlamat = rootView.findViewById(R.id.edittext_alamat);
        editTextBidangKeahlian = rootView.findViewById(R.id.edittext_bidang_keahlian);
        editTextPendidikanTerakhir = rootView.findViewById(R.id.edittext_pendidikan_terakhir);
        editTextEmail = rootView.findViewById(R.id.edittext_email);
        editTextPassword = rootView.findViewById(R.id.edittext_password);
        btnRegistrasi = rootView.findViewById(R.id.btn_registrasi);

        btnRegistrasi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validasi()){
                    progressDialog = new ProgressDialog(getActivity());
                    progressDialog.setMessage("Loading..."); // Setting Message
                    progressDialog.setTitle("Megirim data ke server"); // Setting Title
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
                    progressDialog.show();
                    registrasi();
                }
            }
        });

        return rootView;
    }

    private boolean validasi(){
        if (editTextNoKtp.getText().toString().equals("") || editTextNama.getText().toString().equals("") || editTextAlamat.getText().toString().equals("") ||
                editTextBidangKeahlian.getText().toString().equals("") || editTextPendidikanTerakhir.getText().toString().equals("") || editTextEmail.getText().toString().equals("") ||
                editTextPassword.getText().toString().equals("")){

            Toast.makeText(getActivity(), "Formulir pendaftaran belum lengkap", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void registrasi(){
        HashMap<String, String> fields = new HashMap<String, String>();
        fields.put("noKtp", editTextNoKtp.getText().toString());
        fields.put("nama", editTextNama.getText().toString());
        fields.put("alamat", editTextAlamat.getText().toString());
        fields.put("bidangKeahlian", editTextBidangKeahlian.getText().toString());
        fields.put("pendidikanTerakhir", editTextPendidikanTerakhir.getText().toString());
        fields.put("email", editTextEmail.getText().toString());
        fields.put("password", editTextPassword.getText().toString());

        TunaKaryaService service = Injector.tunaKaryaService();
        service.registrasi(fields).enqueue(new Callback<TunaKarya>() {
            @Override
            public void onResponse(Call<TunaKarya> call, Response<TunaKarya> response) {
                progressDialog.dismiss();
                Prefs.putInt(Constant.STR_ID, response.body().getId());
                Prefs.putString(Constant.STR_HAK_AKSES, response.body().getHakAkses());

                openMainActivity();
            }

            @Override
            public void onFailure(Call<TunaKarya> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    private void openMainActivity(){
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }

}
