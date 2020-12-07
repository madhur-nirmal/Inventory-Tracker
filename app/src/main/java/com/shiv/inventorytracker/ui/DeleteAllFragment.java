package com.shiv.inventorytracker.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.shiv.inventorytracker.DatabaseHelper;
import com.shiv.inventorytracker.R;

import es.dmoral.toasty.Toasty;

public class DeleteAllFragment extends Fragment {
  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_delete_all, container, false);

    DatabaseHelper databaseHelper = new DatabaseHelper(root.getContext());
    Button deleteAllBtn = root.findViewById(R.id.deleteAllBtn);

    deleteAllBtn.setOnClickListener(v -> {
      int rows = databaseHelper.deleteAll();
    });
    return root;
  }
}
