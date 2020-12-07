package com.shiv.inventorytracker.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.shiv.inventorytracker.DatabaseHelper;
import com.shiv.inventorytracker.ProductDetails;
import com.shiv.inventorytracker.R;

import java.util.ArrayList;

import es.dmoral.toasty.Toasty;

public class ViewProductStockFragment extends Fragment {
  private RecyclerView recyclerView;
  private View root;
  private DatabaseHelper databaseHelper;
  public boolean isEmpty = false;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    root = inflater.inflate(R.layout.fragment_view_stock, container, false);
    databaseHelper = new DatabaseHelper(root.getContext());
    recyclerView = root.findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(root.getContext()));

    ListAdapter listAdapter = new ListAdapter(fetchData());

    if (isEmpty) {
      Toasty.error(root.getContext(), "Empty Stock", Toasty.LENGTH_LONG, true).show();
    } else {
      recyclerView.setAdapter(listAdapter);
    }


    return root;
  }

  private ArrayList<ProductDetails> fetchData() {
    ArrayList<ProductDetails> list = new ArrayList<>();
    Cursor cursor = databaseHelper.getAllData();
    if (cursor.getCount() == 0) {
      isEmpty = true;
      return list;
    }
    while (cursor.moveToNext()) {
      list.add(new ProductDetails(
              "ID : " + cursor.getString(0),
              "NAME : " + cursor.getString(1),
              "PRICE : " + cursor.getString(2),
              "QUANTITY : " + cursor.getString(3)
      ));
    }
    return list;
  }
}
