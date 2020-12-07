package com.shiv.inventorytracker.ui;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.shiv.inventorytracker.DatabaseHelper;
import com.shiv.inventorytracker.R;

import es.dmoral.toasty.Toasty;

public class RemoveFromStockFragment extends Fragment {
  private DatabaseHelper databaseHelper;
  private EditText id, unitsToRemove;
  private TextView existingStock;
  private Button removeStockBtn, existingStockCheckBtn;
  private View root;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    root = inflater.inflate(R.layout.fragment_remove_from_stock, container, false);

    id = root.findViewById(R.id.idEditText);
    unitsToRemove = root.findViewById(R.id.unitsToRemove);
    existingStock = root.findViewById(R.id.existingStock);
    removeStockBtn = root.findViewById(R.id.removeStockBtn);
    existingStockCheckBtn = root.findViewById(R.id.existingStockCheckBtn);

    databaseHelper = new DatabaseHelper(root.getContext());

    removeStockBtn.setOnClickListener(v -> {
      removeStockBtnClicked();
    });

    existingStockCheckBtn.setOnClickListener(v -> {
      existingStockCheckBtnClicked();
    });
    return root;
  }

  private void existingStockCheckBtnClicked() {
    if (id.getText().toString().equals(String.valueOf(""))) {
      id.setError("ID cannot be empty");
      return;
    }
    String existingStocks;

    Cursor cursor = databaseHelper.getData(id.getText().toString());
    boolean isNull = true;

    if (cursor.moveToNext()) {
      isNull = false;
      existingStocks = cursor.getString(3);
      existingStock.setText(existingStocks);
    }
    if (isNull) {
      id.setError("SOMETHING WENT WRONG");
    }
  }

  private void removeStockBtnClicked() {
    if (id.getText().toString().equals("")) {
      id.setError("ID cannot be empty");
      return;
    }
    if (unitsToRemove.getText().toString().equals("")) {
      unitsToRemove.setError("This field cannot be empty");
      return;
    }

    Cursor cursor = databaseHelper.getData(id.getText().toString());
    boolean isNull = true;
    if (cursor.moveToNext()) {
      isNull = false;
      int existingStocks = Integer.parseInt(cursor.getString(3));
      int remove = Integer.parseInt(unitsToRemove.getText().toString());
      if (existingStocks < remove) {
        Toasty.error(root.getContext(), "Not enough stock", Toasty.LENGTH_SHORT, true).show();
        return;
      }

      if (existingStocks == 0 && remove == 0) {
        Toasty.error(root.getContext(), "Stock is already empty", Toasty.LENGTH_SHORT, true).show();
        return;
      }

      databaseHelper.updateData(id.getText().toString(), String.valueOf(existingStocks - remove));
      Toasty.success(root.getContext(), "Stock Removed",Toasty.LENGTH_SHORT, true).show();
      id.setText("");
      unitsToRemove.setText("");
      existingStock.setText("");
    }
    if (isNull) {
      Toasty.error(root.getContext(), "SOMETHING WENT WRONG", Toasty.LENGTH_SHORT, true).show();
    }
  }

}
