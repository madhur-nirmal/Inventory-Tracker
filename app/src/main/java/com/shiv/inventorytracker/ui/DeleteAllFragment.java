package com.shiv.inventorytracker.ui;

import android.app.AlertDialog;
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
      new AlertDialog.Builder(root.getContext())
              .setTitle("Delete")
              .setMessage("All the entries will be deleted")
              .setPositiveButton("Yes", (dialog, which) -> {
                int rows = databaseHelper.deleteAll();
                Toasty.success(root.getContext(), rows + " entries deleted", Toasty.LENGTH_SHORT, true).show();
              })
              .setNegativeButton("No", null)
              .show();
    });
    return root;
  }
}
