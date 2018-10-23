package com.thangnc.applicationv2;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class ProductAdapter extends ArrayAdapter<Product> {
    private LayoutInflater inflater;
    private List<Product> productList;
    private onDeleteListener deleteListener;

    public ProductAdapter(@NonNull Context context, int resource, @NonNull List<Product> objects, onDeleteListener delete) {
        super(context, resource, objects);
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        productList = objects;
        deleteListener = delete;

    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_product, parent, false);
            holder.tvID = convertView.findViewById(R.id.tvID);
            holder.tvName = convertView.findViewById(R.id.tvName);
            holder.tvPrice = convertView.findViewById(R.id.tvPrice);
            holder.imgDelete = convertView.findViewById(R.id.imgDelete);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Product product = productList.get(position);
        holder.tvID.setText(product.getMaSP());
        holder.tvName.setText(product.getTenSP());
        holder.tvPrice.setText(product.getGiaSP());
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteListener.onDeleteListener(position);
            }
        });
        return convertView;
    }

    public class ViewHolder {
        public ImageView imgDelete;
        public TextView tvID, tvName, tvPrice;
    }
}
