package com.shiv.inventorytracker.ui.addToStock;

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
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.shiv.inventorytracker.DatabaseHelper;
import com.shiv.inventorytracker.R;

import es.dmoral.toasty.Toasty;

public class AddToStockFragment extends Fragment {


  private EditText id, productName, price, quantity;
  private Button addStockBtn;
  private DatabaseHelper databaseHelper;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    View root = inflater.inflate(R.layout.fragment_add_to_stock, container, false);


    databaseHelper = new DatabaseHelper(root.getContext());
    id = root.findViewById(R.id.idEditText);
    productName = root.findViewById(R.id.nameEditText);
    price = root.findViewById(R.id.priceEditText);
    quantity = root.findViewById(R.id.quantityEditText);
    addStockBtn = root.findViewById(R.id.addToStockBtn);
    productName.setAllCaps(true);

    addStockBtn.setOnClickListener(v -> {
      addToStockClicked();
    });
    return root;
  }

  private void addToStockClicked() {
    if (id.getText().toString().equals(String.valueOf(""))) {
      id.setError("ID cannot be empty");
      return;
    }

    if (productName.getText().toString().equals(String.valueOf(""))) {
      productName.setError("Name cannot be empty");
      return;
    }

    if (price.getText().toString().equals(String.valueOf(""))) {
      price.setError("Price cannot be empty");
      return;
    }

    if (quantity.getText().toString().equals(String.valueOf(""))) {
      quantity.setError("Quantity cannot be empty");
      return;
    }

    boolean isInserted = databaseHelper.insertData(id.getText().toString(), productName.getText().toString(), price.getText().toString(), quantity.getText().toString());

    if (isInserted) {
      id.setText("");
      productName.setText("");
      price.setText("");
      quantity.setText("");
      Toasty.success(getContext(), "Product added to stock", Toast.LENGTH_SHORT, true).show();
    } else {
      Toasty.error(getContext(), "SOMETHING WENT WRONG", Toast.LENGTH_SHORT, true).show();
    }
  }
}