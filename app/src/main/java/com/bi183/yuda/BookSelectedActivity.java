package com.bi183.yuda;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class BookSelectedActivity extends AppCompatActivity {

    TextView tvJudul, tvPenulis, tvPenerbit, tvTahun, tvTebal, tvIsbn;
    ImageView imgBuku;
    DatabaseHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_selected);

        tvJudul = (TextView)findViewById(R.id.tv_judul);
        tvPenulis = (TextView)findViewById(R.id.tv_penulis);
        tvPenerbit = (TextView)findViewById(R.id.tv_penerbit);
        tvTahun = (TextView)findViewById(R.id.tv_tahun);
        tvTebal = (TextView)findViewById(R.id.tv_tebal);
        tvIsbn = (TextView)findViewById(R.id.tv_isbn);
        imgBuku = (ImageView)findViewById(R.id.img_buku);
        db = new DatabaseHandler(this);

        Intent imgId = getIntent();
        final Integer id = Integer.parseInt(imgId.getExtras().getString("id"));
        imgBuku.setImageBitmap(db.getImage(id));

        // menampilkan detail data buku yang dipilih berdasarkan id
        Cursor res = db.getBukuData(id);
        if (res.getCount() == 0) {
            Toast.makeText(getApplicationContext(), "Data not found", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            while (res.moveToNext()) {
                tvJudul.setText(res.getString(0));
                tvPenulis.setText(res.getString(1));
                tvPenerbit.setText(res.getString(2));
                tvTahun.setText(res.getString(3));
                tvTebal.setText(res.getString(4));
                tvIsbn.setText(res.getString(5));
            }
        }
    }
}
