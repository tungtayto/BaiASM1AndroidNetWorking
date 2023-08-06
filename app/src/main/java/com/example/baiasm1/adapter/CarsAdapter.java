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
import com.squareup.picasso.Picasso;

import java.util.List;

public class CarsAdapter extends RecyclerView.Adapter<CarsAdapter.ViewHolder>{

    private List<Cars> list;
    private Callback callback;
    private Picasso picasso = Picasso.get();

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
        picasso.load(cars.getImgCar()).into(holder.imgCar);
        holder.tvNameCar.setText(cars.getNameCar());
        holder.tvColorCar.setText(cars.getColorCar());
        holder.tvYearCar.setText(cars.getYearCar()+"");
        holder.tvEngineTypeCar.setText(cars.getEngineTypeCar()+"");
        holder.tvPriceCar.setText(cars.getPriceCar()+"");
        holder.tvQuantityCar.setText(cars.getQuantityCar()+"");
//        holder.linkimg.setText(cars.getImgCar());

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
        private TextView tvColorCar;
        private TextView tvYearCar;
        private TextView tvEngineTypeCar;
        private ImageView imgCar;
        private TextView linkimg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNameCar = (TextView) itemView.findViewById(R.id.tvNameCar);
            tvPriceCar = (TextView) itemView.findViewById(R.id.tvPriceCar);
            tvQuantityCar = (TextView) itemView.findViewById(R.id.tvQuantityCar);
            imgEditCar = (ImageView) itemView.findViewById(R.id.img_editCar);
            imgDeleteCar = (ImageView) itemView.findViewById(R.id.imgDeleteCar);
            tvColorCar = (TextView) itemView.findViewById(R.id.tvColorCar);
            tvYearCar = (TextView) itemView.findViewById(R.id.tvYearCar);
            tvEngineTypeCar = (TextView) itemView.findViewById(R.id.tvEngineTypeCar);
            imgCar = (ImageView) itemView.findViewById(R.id.img_Car);
            linkimg = (TextView) itemView.findViewById(R.id.linkimg);
        }
    }

    public interface Callback{
        void editCar(Cars cars);
        void deleteCar(Cars cars);
    }
}
