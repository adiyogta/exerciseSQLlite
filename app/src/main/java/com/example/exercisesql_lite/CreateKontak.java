package com.example.exercisesql_lite;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateKontak extends AppCompatActivity {

    private DBHelper mydb ;
    EditText nama, phone, email, alamat;
    private static  String EMAIL_REGEX = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";

    int id_To_Update = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_kontak);

        nama = (EditText) findViewById(R.id.editTextNama);
        phone = (EditText) findViewById(R.id.editTextNoTelp);
        email = (EditText) findViewById(R.id.editTextEmail);
        alamat = (EditText) findViewById(R.id.editTextAlamat);

        mydb = new DBHelper(this);

        Bundle extras = getIntent().getExtras();

        if(extras !=null) {
            int Value = extras.getInt("id");

            if (Value > 0) {
                //means this is the view part not the add contact part.
                Cursor rs = mydb.getData(Value);
                id_To_Update = Value;
                rs.moveToFirst();

                String nam = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_NAMA));
                String phon = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_PHONE));
                String emai = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_EMAIL));
                String alama = rs.getString(rs.getColumnIndex(DBHelper.MHS_COLUMN_ALAMAT));
                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.btnsimpan);
                b.setVisibility(View.INVISIBLE);

                nama.setText((CharSequence) nam);
                nama.setFocusable(false);
                nama.setClickable(false);

                phone.setText((CharSequence) phon);
                phone.setFocusable(false);
                phone.setClickable(false);

                email.setText((CharSequence) emai);
                email.setFocusable(false);
                email.setClickable(false);

                alamat.setText((CharSequence) alama);
                alamat.setFocusable(false);
                alamat.setClickable(false);
            }
        }
    }
    public void run (View view){
        if (nama.getText().toString().equals("")||
            phone.getText().toString().equals("")||
            email.getText().toString().equals("")||
            alamat.getText().toString().equals(""))
        {
            Toast.makeText(getApplicationContext(),
                    "Data Harus Diisi Semua !.",Toast.LENGTH_SHORT).show();
        }else if(!email.getText().toString().matches(EMAIL_REGEX)){
            Toast.makeText(getApplicationContext(),
                    "Format Email Salah",Toast.LENGTH_SHORT).show();
        } else {
            mydb.insertContact(nama.getText().toString(),
                                phone.getText().toString(),
                                email.getText().toString(),
                                alamat.getText().toString());
            Toast.makeText(getApplicationContext(),
                    "Tambah Data Sukses.",Toast.LENGTH_LONG).show();

            Intent i = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(i);
        }
    }
}
