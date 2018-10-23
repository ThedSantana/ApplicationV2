package com.thangnc.applicationv2;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private EditText edtMaSP;
    private EditText edtTenSP;
    private EditText edtGiaSP;
    private Button btnAdd;
    private Button btnEdit;
    private ListView lvListSP;
    private ProductDAO productDAO;
    private ProductAdapter adapter;
    private DatabaseHelper databaseHelperl;
    private List<Product> listProduct;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listProduct = new ArrayList<>();
        databaseHelperl = new DatabaseHelper(this);
        productDAO = new ProductDAO(databaseHelperl);
        initView();
        listProduct.addAll(productDAO.getAllProduct());
        adapter = new ProductAdapter(this, R.layout.item_product, listProduct, new onDeleteListener() {
            @Override
            public void onDeleteListener(final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Delete");
                builder.setMessage("Do U want to delete?????");
                builder.setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productDAO.deleteProduct(listProduct.get(position).getMaSP());
                        listProduct.remove(position);
                        adapter.notifyDataSetChanged();
                    }
                });
                builder.show();
            }
        });
        lvListSP.setAdapter(adapter);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String productID = edtMaSP.getText().toString();
                final String productName = edtTenSP.getText().toString();
                final String productPrice = edtGiaSP.getText().toString();
                if (productID.equalsIgnoreCase("") ||
                        productName.equalsIgnoreCase("") ||
                        productPrice.equalsIgnoreCase("")) {
                    Toast.makeText(MainActivity.this, "Not null", Toast.LENGTH_SHORT).show();
                } else {
                    Product product = new Product(productID, productName, productPrice);
                    long result = productDAO.insertProduct(product);
                    if (result == -1) {
                        Toast.makeText(MainActivity.this, "Trung du lieu", Toast.LENGTH_SHORT).show();
                    } else {
                        listProduct.add(product);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Add success", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        lvListSP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Product product = listProduct.get(position);
                edtMaSP.setText(product.getMaSP());
                edtTenSP.setText(product.getTenSP());
                edtGiaSP.setText(product.getGiaSP());
            }
        });
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String productId = edtMaSP.getText().toString();
                String productName = edtTenSP.getText().toString();
                String productPrice = edtGiaSP.getText().toString();
                int j = 0;
                for (int i = 0; i < productDAO.getAllProduct().size(); i++) {
                    if (productDAO.getAllProduct().get(i).getMaSP().equals(productId)) {
                        j = 1;
                        break;
                    }
                }
                Product product = listProduct.get(j);
                product.setTenSP(productName);
                product.setGiaSP(productPrice);
                productDAO.updateProduct(product);
                adapter.notifyDataSetChanged();
            }
        });
    }

    public void initView() {
        edtMaSP = (EditText) findViewById(R.id.edtMaSP);
        edtTenSP = (EditText) findViewById(R.id.edtTenSP);
        edtGiaSP = (EditText) findViewById(R.id.edtGiaSP);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnEdit = (Button) findViewById(R.id.btnEdit);
        lvListSP = (ListView) findViewById(R.id.lvListSP);
    }
}
