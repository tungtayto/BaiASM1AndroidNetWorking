package com.example.baiasm1.adapter;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.baiasm1.R;
import com.example.baiasm1.model.Cars;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder>{

    private List<Cars> list;
    private Callback callback;
    private int idSeletedCar = -1;

    public CarsAdapter(List<Cars> list, Callback callback) {
        this.list = list;
        this.callback = callback;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_cars_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Cars cars = list.get(position);
        if (cars == null){
            return;
        }
        holder.tvNameCar.setText(cars.getName());
        holder.tvPriceCar.setText(cars.getPrice()+"");
        holder.tvQuantityCar.setText(cars.getQuantity()+"");

        holder.imgEditCar.setOnClickListener(v->{
            callback.editCar(cars);
        });
        holder.imgDeleteCar.setOnClickListener(v->{
            callback.deleteCar(cars);
        });
    }

    @Override
    public int getItemCount() {
        return list == null? 0 : list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvNameCar;
        private TextView tvPriceCar;
        private TextView tvQuantityCar;
        private ImageView imgEditCar;
        private ImageView imgDeleteCar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCar = (TextView) itemView.findViewById(R.id.tvNameCar);
            tvPriceCar = (TextView) itemView.findViewById(R.id.tvPriceCar);
            tvQuantityCar = (TextView) itemView.findViewById(R.id.tvQuantityCar);
            imgEditCar = (ImageView) itemView.findViewById(R.id.img_editCar);
            imgDeleteCar = (ImageView) itemView.findViewById(R.id.imgDeleteCar);
        }
    }

    public interface Callback{
        void editCar(Cars cars);
        void deleteCar(Cars cars);
    }
}
