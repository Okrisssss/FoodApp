package apple.example.com.foodorderappexample;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import apple.example.com.foodorderappexample.Common.Common;
import apple.example.com.foodorderappexample.Database.DatabaseOrderInformation;
import apple.example.com.foodorderappexample.Model.Order;
import apple.example.com.foodorderappexample.Model.Request;
import apple.example.com.foodorderappexample.ViewHolder.CartAdapter;

public class CartActivity extends AppCompatActivity {


    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference requests;

    TextView txtTotalPrice;
    Button btnPlace;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests= database.getReference("Requests");

        //Init view
        recyclerView = (RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        txtTotalPrice = (TextView) findViewById(R.id.total);
        btnPlace = (Button) findViewById(R.id.btnPlaceOrder);

        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             showAlerdDialog();
            }
        });
        loadListFood();
    }
    //Create new request
    private void showAlerdDialog() {

        AlertDialog.Builder alerdDialog = new AlertDialog.Builder(CartActivity.this);
        alerdDialog.setTitle("One more step!");
        alerdDialog.setMessage("Enter your Address: ");
        final EditText edtAddress = new EditText(CartActivity.this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT
        );
        edtAddress.setLayoutParams(lp);
        alerdDialog.setView(edtAddress);//Add edit text to allert Dialog
        alerdDialog.setIcon(R.drawable.ic_shopping_cart_black_24dp);

        alerdDialog.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //Create request
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        edtAddress.getText().toString(),
                        Common.currentUser.getName(),
                        txtTotalPrice.getText().toString(),
                        cart);
                //Submiting request to Firebase using System.CurrentMilliss
                requests.child(String.valueOf(System.currentTimeMillis()))
                        .setValue(request);

                //Delete cart
                new DatabaseOrderInformation(getBaseContext()).cleanCart();
                Toast.makeText(CartActivity.this, "Thank you, Order placed", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alerdDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        alerdDialog.show();
    }

    private void loadListFood() {

        cart = new DatabaseOrderInformation(this).getCarts();
        adapter = new CartAdapter(cart,this);
        recyclerView.setAdapter(adapter);

        //Calculate total price
        int total = 0;
        for (Order order :cart)
            total+=(Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));

        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        txtTotalPrice.setText(fmt.format(total));
    }
}
