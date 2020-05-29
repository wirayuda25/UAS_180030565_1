package com.bi183.yuda;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    EditText edtJudul, edtPenulis, edtPenerbit, edtTahun, edtTebal, edtIsbn;
    Button btnPilih, btnSimpan, btnBatal;
    ImageView imgBuku;
    private static final int GALLERY_REQUEST_CODE = 100;
    Uri imageData;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        edtJudul = (EditText)findViewById(R.id.edt_judul);
        edtPenulis = (EditText)findViewById(R.id.edt_penulis);
        edtPenerbit = (EditText)findViewById(R.id.edt_penerbit);
        edtTahun = (EditText)findViewById(R.id.edt_tahun);
        edtTebal = (EditText)findViewById(R.id.edt_tebal);
        edtTebal = (EditText)findViewById(R.id.edt_tebal);
        edtIsbn = (EditText)findViewById(R.id.edt_isbn);
        btnPilih = (Button)findViewById(R.id.btn_pilih);
        btnSimpan = (Button)findViewById(R.id.btn_simpan);
        btnBatal = (Button)findViewById(R.id.btn_batal);
        imgBuku = (ImageView)findViewById(R.id.img_buku);
        db = new DatabaseHandler(this);

        // tombol simpan
        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String judul = edtJudul.getText().toString();
                String penulis = edtPenulis.getText().toString();
                String penerbit = edtPenerbit.getText().toString();
                String tahun = edtTahun.getText().toString();
                String tebal = edtTebal.getText().toString();
                String isbn = edtIsbn.getText().toString();

                // check form telah terisi semua
                if (!judul.equals("") && !penulis.equals("") && !penerbit.equals("") && !tahun.equals("") && !tebal.equals("") && !isbn.equals("")) {
                    // check gambar telah dipilih
                    if (imageData != null) {
                        String loc = getPath(imageData);
                        // check apakah proses simpan ke database berhasil
                        if (db.insertData(judul, penulis, penerbit, tahun, tebal, isbn, loc)) {
                            Toast.makeText(getApplicationContext(), "Save successfully", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(getApplicationContext(), "Save failed", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Please choose an image", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "Please fill in all data", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // tombol pilih gambar
        btnPilih.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse(
                        "content://media/internal/images/media"
                ));
                startActivityForResult(intent, GALLERY_REQUEST_CODE);
            }
        });
    }

    // mendapatkan data gambar
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            imageData = data.getData();
            imgBuku.setImageURI(imageData);
        }
    }

    // mendapatkan lokasi gambar
    public String getPath(Uri uri) {
        if (uri == null) return null;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null,null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        return uri.getPath();
    }
}
