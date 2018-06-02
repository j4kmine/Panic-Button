package com.example.asus.panic;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.*;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ASUS on 4/21/2018.
 */
public class KontakAdapter extends RecyclerView.Adapter<KontakViewHolder> {
    private Context context;
    private List<Kontak> listKontak;
    private DataHelper mDatabase;
    DataHelper mDatabaseHelper;
    View rootView;
    protected RecyclerView mRecyclerView;
    protected KontakAdapter mAdapter;
    public KontakAdapter(Context context, List<Kontak> listKontak) {
        this.context = context;
        this.listKontak = listKontak;
        this.mDatabase = new DataHelper(context);
    }

    @Override
    public KontakViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_kontak, parent, false);
        KontakViewHolder kontakViewHolder = new KontakViewHolder(view);
        return kontakViewHolder;
    }
    @Override
    public void onBindViewHolder(KontakViewHolder holder, final int position) {
        final Kontak singleKontak = listKontak.get(position);
        holder.nama_kontak.setText(singleKontak.getNama());
        holder.edit_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //editTaskDialog(position,singleKontak);
                final LayoutInflater inflater = LayoutInflater.from(context);
                View subView = inflater.inflate(R.layout.add_kontak, null);

                final EditText nama = (EditText)subView.findViewById(R.id.nama_form);
                final EditText phones = (EditText)subView.findViewById(R.id.phone_form);
                if(singleKontak != null){
                    nama.setText(singleKontak.getNama());
                    phones.setText(singleKontak.getPhone());
                }


                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Mengedit Kontak");
                builder.setView(subView);
                builder.create();
                builder.setPositiveButton("EDIT KONTAK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        final String names = nama.getText().toString();
                        final String phone = phones.getText().toString();

                        if(TextUtils.isEmpty(names) || TextUtils.isEmpty(phone) ){
                            Toast.makeText(context, "Silahkan check isian form anda", Toast.LENGTH_LONG).show();
                        }
                        else{
                            String namas = names;
                            String phones = phone;
                            Integer id = singleKontak.getId();
                            DataHelper Initialize = new DataHelper(context);
                            Initialize.editkontak(namas,phones,id);
                            Kontak new_editKontak = new Kontak(id,names, phone);
                            listKontak.remove(position);
                            listKontak.add(new_editKontak);
                            notifyItemChanged(position);
                            notifyDataSetChanged();
                        }
                    }
                });

                builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
                    }
                });
                builder.show();



            }
        });
        holder.delete_kontak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //delete row from database

                mDatabase.deleteKontak(singleKontak.getId());
                listKontak.remove(position);
                notifyDataSetChanged();
//                ((FragmentActivity)context).finish();
//                context.startActivity(((FragmentActivity)context).getIntent());


            }
        });
    }

    @Override
    public int getItemCount() {
        return listKontak.size();
    }

    public void editTaskDialog(final int position, final Kontak kontak){
        final LayoutInflater inflater = LayoutInflater.from(context);
        View subView = inflater.inflate(R.layout.add_kontak, null);

        final EditText nama = (EditText)subView.findViewById(R.id.nama_form);
        final EditText phones = (EditText)subView.findViewById(R.id.phone_form);
        if(kontak != null){
            nama.setText(kontak.getNama());
            phones.setText(kontak.getPhone());
        }


        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Mengedit Kontak");
        builder.setView(subView);
        builder.create();

        builder.setPositiveButton("EDIT KONTAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String names = nama.getText().toString();
                final String phone = phones.getText().toString();

                if(TextUtils.isEmpty(names) || TextUtils.isEmpty(phone) ){
                    Toast.makeText(context, "Silahkan check isian form anda", Toast.LENGTH_LONG).show();
                }
                else{
                    String namas = names;
                    String phones = phone;
                    Integer id = kontak.getId();
                    DataHelper Initialize = new DataHelper(context);
                    Initialize.editkontak(namas,phones,id);
                    Kontak new_editKontak = new Kontak(id,names, phone);
                    //UpdateData(position,kontak);
//                    ((FragmentActivity)context).finish();
//                    context.startActivity(((FragmentActivity)context).getIntent());

//                    View subViews = inflaters.inflate(R.layout.fragment_helper, null);
//                    mRecyclerView = (RecyclerView) subViews.findViewById(R.id.list_contact);
//                    mRecyclerView.setAdapter(mAdapter);
//                    mAdapter.notifyDataSetChanged();


                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }

    public void UpdateData(int position,Kontak userData){


    }
}
