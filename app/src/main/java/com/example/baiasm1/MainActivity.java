package com.example.baiasm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.baiasm1.adapter.CarsAdapter;
import com.example.baiasm1.api.CarService;
import com.example.baiasm1.model.Cars;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;
import java.util.concurrent.BlockingDeque;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CarsAdapter.Callback {
    private RecyclerView rcvListCar;
    private FloatingActionButton btn_showDialogAddCar;

    private CarsAdapter carsAdapter;

    private EditText edNameCar;
    private EditText edPriceCar;
    private EditText edQuantityCar;
    private TextView tvAddCars;
    private TextView tvCancelAddCsrs;

    private TextView tvEditCars;
    private TextView tvCancelEditCsrs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rcvListCar = (RecyclerView) findViewById(R.id.rcvListCar);
        btn_showDialogAddCar = (FloatingActionButton) findViewById(R.id.btn_showDialogAddCar);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvListCar.setLayoutManager(linearLayoutManager);

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        rcvListCar.addItemDecoration(itemDecoration);

        getListCars();

        btn_showDialogAddCar.setOnClickListener(this);
    }

    private void getListCars() {
        Call<List<Cars>> call = CarService.carService.getCar();
        call.enqueue(new Callback<List<Cars>>() {
            @Override
            public void onResponse(Call<List<Cars>> call, Response<List<Cars>> response) {
                if (response.isSuccessful()) {
                    List<Cars> list = response.body();
                    showCarList(list);
                } else {
                    Toast.makeText(MainActivity.this, "Hien danh sach loi", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cars>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showCarList(List<Cars> carsList) {
        carsAdapter = new CarsAdapter(carsList, this);
        rcvListCar.setAdapter(carsAdapter);
    }

    private void addCar(String name, int price, int quantity) {
        Cars cars = new Cars();
        cars.setName(name);
        cars.setPrice(price);
        cars.setQuantity(quantity);

        Call<Cars> call = CarService.carService.addCar(cars);
        Log.d("Hello", "addCar: "+name+" " + price  + " " +quantity);

        call.enqueue(new Callback<Cars>() {
            @Override
            public void onResponse(Call<Cars> call, Response<Cars> response) {
                if (response.isSuccessful()){
                    Cars cars1 = response.body();
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    getListCars();
                } else {
                    Log.e("MainActivity", "Response unsuccessful: " + response.message());
                    Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cars> call, Throwable t) {
                Log.e("MainActivity", "Request failed: " + t.getMessage());
            }
        });
    }

    private void updateCar(String id, String name, int price, int quantity){
        Cars cars = new Cars();
        cars.setName(name);
        cars.setPrice(price);
        cars.setQuantity(quantity);

        Call<Cars> call = CarService.carService.updateCar(id,cars);
        call.enqueue(new Callback<Cars>() {
            @Override
            public void onResponse(Call<Cars> call, Response<Cars> response) {
                if (response.isSuccessful()){
                    Cars cars1 = response.body();
                    Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    getListCars();
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                    Toast.makeText(MainActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Cars> call, Throwable t) {
                Log.d("MAIN", "Respone Fail" + t.getMessage());
            }
        });
    }

    private void deleteCars(String id){
        Call<Void> call = CarService.carService.deleteCars(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    getListCars();
                } else {
                    Toast.makeText(MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_showDialogAddCar){
            //Add Car
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_add_cars);
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialog != null && dialog.getWindow() != null){
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            edNameCar = (EditText) dialog.findViewById(R.id.ed_nameCar);
            edPriceCar = (EditText) dialog.findViewById(R.id.ed_priceCar);
            edQuantityCar = (EditText) dialog.findViewById(R.id.ed_quantityCar);
            tvAddCars = (TextView) dialog.findViewById(R.id.tv_addCars);
            tvCancelAddCsrs = (TextView) dialog.findViewById(R.id.tv_cancelAddCsrs);

            tvAddCars.setOnClickListener(v -> {
                String name = edNameCar.getText().toString().trim();
                String price = edPriceCar.getText().toString().trim();
                String quantity = edQuantityCar.getText().toString().trim();

                addCar(name,Integer.parseInt(price),Integer.parseInt(quantity));

                dialog.dismiss();
            });

            tvCancelAddCsrs.setOnClickListener(v -> {
                dialog.dismiss();
            });

            dialog.show();
        }
    }

    @Override
    public void editCar(Cars cars) {
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_edit_cars);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null){
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }

        edNameCar = (EditText) dialog.findViewById(R.id.ed_nameCar);
        edPriceCar = (EditText) dialog.findViewById(R.id.ed_priceCar);
        edQuantityCar = (EditText) dialog.findViewById(R.id.ed_quantityCar);
        tvEditCars = (TextView) dialog.findViewById(R.id.tv_editCars);
        tvCancelEditCsrs = (TextView) dialog.findViewById(R.id.tv_cancelEditCsrs);

        edNameCar.setText(cars.getName());
        edPriceCar.setText(cars.getPrice()+"");
        edQuantityCar.setText(cars.getQuantity()+"");

        tvEditCars.setOnClickListener(v->{
            String name = edNameCar.getText().toString().trim();
            String price = edPriceCar.getText().toString().trim();
            String quantity = edQuantityCar.getText().toString().trim();
            updateCar(cars.getId(),name,Integer.parseInt(price),Integer.parseInt(quantity));
            dialog.dismiss();
        });

        tvCancelEditCsrs.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void deleteCar(Cars cars) {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xóa Car");
        builder.setMessage("Bạn có chắc chắn muốn xóa "+cars.getName()+" ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteCars(cars.getId());
                dialogInterface.dismiss();
            }
        });
        builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}