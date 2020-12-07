package com.shiv.inventorytracker.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.shiv.inventorytracker.ProductDetails;
import com.shiv.inventorytracker.R;

import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ProductsViewHolder> {

  private final ArrayList<ProductDetails> productsList;

  public ListAdapter(ArrayList<ProductDetails> productsList) {
    this.productsList = productsList;
  }

  @NonNull
  @Override
  public ProductsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    return new ProductsViewHolder(LayoutInflater
            .from(parent.getContext())
            .inflate(R.layout.item_product, parent, false)
    );
  }

  @Override
  public void onBindViewHolder(@NonNull ProductsViewHolder holder, int position) {
    ProductDetails current = productsList.get(position);
    holder.productID.setText(current.productID);
    holder.productName.setText(current.productName);
    holder.productPrice.setText(current.productPrice);
    holder.productQuantity.setText(current.productQuantity);
  }

  @Override
  public int getItemCount() {
    return productsList.size();
  }
}

class ProductsViewHolder extends RecyclerView.ViewHolder {
  public TextView productID, productName, productPrice, productQuantity;

  public ProductsViewHolder(@NonNull View itemView) {
    super(itemView);
    productID = itemView.findViewById(R.id.productID);
    productName = itemView.findViewById(R.id.productName);
    productPrice = itemView.findViewById(R.id.productPrice);
    productQuantity = itemView.findViewById(R.id.productQuantity);
  }
}