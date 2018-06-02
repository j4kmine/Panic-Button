package com.example.asus.panic;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ASUS on 4/21/2018.
 */

public class KontakViewHolder extends RecyclerView.ViewHolder  {
    public TextView nama_kontak;
    public ImageView delete_kontak;
    public  ImageView edit_kontak;

    public KontakViewHolder(View itemView) {
        super(itemView);
        nama_kontak =(TextView)itemView.findViewById(R.id.nama_kontak);
        delete_kontak = (ImageView) itemView.findViewById(R.id.delete_kontak);
        edit_kontak =(ImageView)itemView.findViewById(R.id.edit_kontak);


    }
}
