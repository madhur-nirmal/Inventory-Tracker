package com.shiv.inventorytracker.ui.viewStock;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.shiv.inventorytracker.DatabaseHelper;
import com.shiv.inventorytracker.R;

public class ViewStockFragment extends Fragment {

  private EditText id;
  private View root;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {

    root = inflater.inflate(R.layout.fragment_product_stock, container, false);

    id = root.findViewById(R.id.idEditText);
    Button checkStockBtn = root.findViewById(R.id.checkStockBtn);

    checkStockBtn.setOnClickListener(v -> {
      checkStockBtnClicked();
    });
    return root;
  }

  private void checkStockBtnClicked() {
    DatabaseHelper databaseHelper = new DatabaseHelper(root.getContext());
    String ID = id.getText().toString();
    if(ID.equals(String.valueOf(""))) {
      id.setError("ID cannot be empty");
      return;
    }

    Cursor cursor = databaseHelper.getData(ID);
    String data = null;
    boolean isNull = true;
    if(cursor.moveToNext()) {
      isNull = false;
      data = "ID : " + cursor.getString(0) + "\n" +
              "PRODUCT NAME : " + cursor.getString(1) + "\n" +
              "PRICE : " + cursor.getString(2) + "\n" +
              "QUANTITY : " + cursor.getString(3) + "\n";
    }
    if (isNull) {
      data = "NOT FOUND";
    }
    showMessage("STOCK : ", data);
  }

  public void showMessage(String title, String message) {

    AlertDialog.Builder builder = new AlertDialog.Builder(root.getContext());
    builder.create();
    builder.setCancelable(true);
    builder.setTitle(title);
    builder.setMessage(message);
    builder.show();
  }

}