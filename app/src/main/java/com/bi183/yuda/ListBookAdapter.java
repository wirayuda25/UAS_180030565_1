package com.bi183.yuda;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ListBookAdapter extends ArrayAdapter<Book> {

    private Context mContext;
    int mResource;
    private static class ViewHolder {
        ImageView imgThumbbuku;
        TextView tvId, tvTitle, tvPenulis, tvIsbn;
    }
    public ListBookAdapter(Context context, int resource, ArrayList<Book> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    // fungsi mendapatkan data untuk ditampilkan pada listview
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Integer id = getItem(position).getIdbuku();
        String judul = getItem(position).getJudul();
        String penulis = getItem(position).getPenulis();
        String penerbit = getItem(position).getPenerbit();
        String tahun = getItem(position).getTahun();
        String tebal = getItem(position).getTebal();
        String isbn = getItem(position).getIsbn();
        byte[] img = getItem(position).getImg();

        Book book = new Book(id, judul, penulis, penerbit, tahun, tebal, isbn, img);
        View res;
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.tvId = (TextView)convertView.findViewById(R.id.tv_id);
            holder.tvTitle = (TextView)convertView.findViewById(R.id.tv_judul);
            holder.tvPenulis = (TextView)convertView.findViewById(R.id.tv_penulis);
            holder.tvIsbn = (TextView)convertView.findViewById(R.id.tv_isbn);
            holder.imgThumbbuku = (ImageView)convertView.findViewById(R.id.img_thumbbuku);
            res = convertView;
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
            res = convertView;
        }
        holder.tvId.setText(book.getIdbuku().toString());
        holder.tvTitle.setText(book.getJudul());
        holder.tvPenulis.setText(book.getPenulis());
        holder.tvIsbn.setText(book.getIsbn());
        holder.imgThumbbuku.setImageBitmap(BitmapFactory.decodeByteArray(book.getImg(), 0, book.getImg().length));
        return convertView;
    }
}
