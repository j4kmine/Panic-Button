package com.example.asus.panic;


import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import static android.os.Build.TAGS;


/**
 * A simple {@link Fragment} subclass.
 */
public class HelperFragment extends Fragment {
    protected RadioButton mLinearLayoutRadioButton;
    protected RadioButton mGridLayoutRadioButton;
    View rootView;
    DataHelper mDatabaseHelper;
    public RecyclerView mRecyclerView;
    protected KontakAdapter mAdapter;
    private ViewPager mViewPager;
    protected RecyclerView.LayoutManager mLayoutManager;
    public DataHelper mDatabase;
    SectionPageAdapter mPageAdapter;
    public HelperFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_helper, container, false);
        // Replace 'android.R.id.list' with the 'id' of your RecyclerView
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_contact);
        mLayoutManager = new LinearLayoutManager(this.getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mDatabaseHelper = new DataHelper(getActivity());
        List<Kontak> allkontak = mDatabaseHelper.listKontak();
        if(allkontak.size() > 0){
            mRecyclerView.setVisibility(View.VISIBLE);
            mAdapter = new KontakAdapter(getActivity(),allkontak);
            mRecyclerView.setAdapter(mAdapter);

        }else {
            mRecyclerView.setVisibility(View.GONE);
            Toast.makeText(getActivity(), "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
        }
        FloatingActionButton fab = (FloatingActionButton)rootView.findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // add new quick task
                addTaskDialog();
            }
        });
        return rootView;

    }

    private void addTaskDialog(){
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View subView = inflater.inflate(R.layout.add_kontak, null);

        final EditText nama = (EditText)subView.findViewById(R.id.nama_form);
        final EditText phones = (EditText)subView.findViewById(R.id.phone_form);


        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Menambahkan Kontak Baru");
        builder.setView(subView);
        builder.create();
        builder.setPositiveButton("ADD KONTAK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                final String names = nama.getText().toString();
                final String phone = phones.getText().toString();

                if(TextUtils.isEmpty(names) || TextUtils.isEmpty(phone) ){
                    Toast.makeText(getActivity(), "Silahkan check isian form anda", Toast.LENGTH_LONG).show();
                }
                else{
                    Kontak newKontak = new Kontak(names, phone);
                    DataHelper Initialize = new DataHelper(getActivity());
                    Initialize.addkontak(newKontak);
                    List<Kontak> allkontak = mDatabaseHelper.listKontak();
                    mAdapter = new KontakAdapter(getActivity(),allkontak);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        });

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(getActivity(), "Task cancelled", Toast.LENGTH_LONG).show();
            }
        });
        builder.show();
    }


//    @Override
//    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
////        RecyclerView productView = (RecyclerView) rootView.findViewById(R.id.list_contact);
////        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
////        productView.setLayoutManager(linearLayoutManager);
////        productView.setHasFixedSize(true);
////
////        mDatabase = new DataHelper(getActivity());
////        List<Kontak> allProducts = mDatabase.listKontak();
////
////        if(allProducts.size() > 0){
////            productView.setVisibility(View.VISIBLE);
////            KontakAdapter mAdapter = new KontakAdapter(getActivity(), allProducts);
////            productView.setAdapter(mAdapter);
////
////        }else {
////            productView.setVisibility(View.GONE);
////            Toast.makeText(getActivity(), "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
////        }
//        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.list_contact);
//        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//        //mRecyclerView.setHasFixedSize(true);
//        mDatabaseHelper = new DataHelper(getActivity());
//        List<Kontak> allkontak = mDatabaseHelper.listKontak();
//        if(allkontak.size() > 0){
//            mRecyclerView.setVisibility(View.VISIBLE);
//            KontakAdapter myAdapter = new KontakAdapter(getActivity(),allkontak);
//            mRecyclerView.setAdapter(myAdapter);
//            myAdapter.notifyDataSetChanged();
//        }else {
//            mRecyclerView.setVisibility(View.GONE);
//            Toast.makeText(getActivity(), "There is no product in the database. Start adding now", Toast.LENGTH_LONG).show();
//        }
//
//
//    }



}
