package apple.example.com.foodorderappexample.ViewHolder;


import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import apple.example.com.foodorderappexample.Interface.ItemClickListener;
import apple.example.com.foodorderappexample.Model.Order;
import apple.example.com.foodorderappexample.R;

class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    public TextView cartPrice;
    public TextView cartName;
    public ImageView cartCountImg;

    private ItemClickListener itemClickListener;

    public void setCartName(TextView cartName) {
        this.cartName = cartName;
    }

    public CartViewHolder(View itemView) {
        super(itemView);
        cartName = (TextView)itemView.findViewById(R.id.cart_item_name);
        cartPrice = (TextView)itemView.findViewById(R.id.cart_item_price);
        cartCountImg = (ImageView) itemView.findViewById(R.id.cart_item_count);
    }

    @Override
    public void onClick(View v) {

    }
}

public class CartAdapter extends RecyclerView.Adapter<CartViewHolder> {


    private List<Order> listData = new ArrayList<>();
    private Context context;

    public CartAdapter(List<Order> listData, Context context) {
        this.listData = listData;
        this.context = context;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.cart_layout, parent, false);
        return new CartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {

        TextDrawable drawable = TextDrawable.builder().buildRound(""+listData.get(position).getQuantity(), Color.RED);
        holder.cartCountImg.setImageDrawable(drawable);
        Locale locale = new Locale("en", "US");
        NumberFormat fmt = NumberFormat.getCurrencyInstance(locale);
        int price  = (Integer.parseInt(listData.get(position).getPrice()))*(Integer.parseInt(listData.get(position).getQuantity()));
        holder.cartPrice.setText(fmt.format(price));
        holder.cartName.setText(listData.get(position).getProductName());
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
