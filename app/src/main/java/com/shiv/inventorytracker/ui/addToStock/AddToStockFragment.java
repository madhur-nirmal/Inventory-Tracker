package com.shiv.inventorytracker.ui.addToStock;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.shiv.inventorytracker.DatabaseHelper;
import com.shiv.inventorytracker.R;

import es.dmoral.toasty.Toasty;

public class AddToStockFragment extends Fragment {


  private EditText id, productName, price, quantity;
  private Button addStockBtn;
  private DatabaseHelper databaseHelper;
  private ImageButton qrCodeScannerBtn;
  private View root;

  public View onCreateView(@NonNull LayoutInflater inflater,
                           ViewGroup container, Bundle savedInstanceState) {
    root = inflater.inflate(R.layout.fragment_add_to_stock, container, false);


    databaseHelper = new DatabaseHelper(root.getContext());
    id = root.findViewById(R.id.idEditText);
    productName = root.findViewById(R.id.nameEditText);
    price = root.findViewById(R.id.priceEditText);
    quantity = root.findViewById(R.id.quantityEditText);
    addStockBtn = root.findViewById(R.id.addToStockBtn);
    qrCodeScannerBtn = root.findViewById(R.id.scanBtn);
    productName.setAllCaps(true);

    addStockBtn.setOnClickListener(v -> {
      addToStockClicked();
    });

    qrCodeScannerBtn.setOnClickListener(v -> {
      scanCode();
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

    boolean isInserted = databaseHelper.insertData(id.getText().toString(),
            productName.getText().toString().toUpperCase(), price.getText().toString(),
            quantity.getText().toString());

    if (isInserted) {
      id.setText("");
      productName.setText("");
      price.setText("");
      quantity.setText("");
      Toasty.success(root.getContext(), "Product added to stock", Toast.LENGTH_SHORT, true).show();
    } else {
      Toasty.error(root.getContext(), "SOMETHING WENT WRONG", Toast.LENGTH_SHORT, true).show();
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

    IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
    boolean isString = false;
    if (result != null) {
      if (result.getContents() != null) {
        try {
          Long.parseLong(result.getContents());
        } catch (NumberFormatException e) {
          isString = true;
          Toasty.error(root.getContext(), "Code contains alphabets", Toasty.LENGTH_SHORT, true).show();
        }
        if (!isString) id.setText(result.getContents());
      } else {
        Toasty.error(root.getContext(), "No Results", Toasty.LENGTH_SHORT, true).show();
      }
    } else super.onActivityResult(requestCode, resultCode, data);
  }

  private void scanCode() {
    IntentIntegrator intent =
            IntentIntegrator.forSupportFragment(AddToStockFragment.this);
    intent.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
    intent.setPrompt("Scanning");
    intent.setCameraId(0);
    intent.setBeepEnabled(false);
    intent.setBarcodeImageEnabled(false);
    intent.initiateScan();
  }
}