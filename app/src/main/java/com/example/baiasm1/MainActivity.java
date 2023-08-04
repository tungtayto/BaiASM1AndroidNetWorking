package com.example.baiasm1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
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
import android.view.MenuItem;
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
    private EditText edColorCar;
    private EditText edYearCar;
    private EditText edEngineTypeCar;
    private EditText edImgCar;
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

        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvListCar.addItemDecoration(itemDecoration);

        getListCars();

        btn_showDialogAddCar.setOnClickListener(this);
    }

    //Lấy danh Sách Xe Service
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
                Log.d("gggggg", "onFailure: " + t.getMessage());
            }
        });
    }

    //Hiện danh sách lên view
    private void showCarList(List<Cars> carsList) {
        if (carsList != null) {
            carsAdapter = new CarsAdapter(carsList, this);
            rcvListCar.setAdapter(carsAdapter);
        }
    }

    //Thêm Xe Service
    private void addCar(String nameCar, String colorCar, int yearCar, String engineTypeCar, int priceCar, int quantityCar, String imgCar) {
        Cars cars = new Cars();
        cars.setNameCar(nameCar);
        cars.setColorCar(colorCar);
        cars.setYearCar(yearCar);
        cars.setEngineTypeCar(engineTypeCar);
        cars.setPriceCar(priceCar);
        cars.setQuantityCar(quantityCar);
        cars.setImgCar(imgCar);

        CarService.carService.addCar(cars).enqueue(new Callback<List<Cars>>() {
            @Override
            public void onResponse(Call<List<Cars>> call, Response<List<Cars>> response) {
                if (response.isSuccessful()) {
                    List<Cars> carsList = response.body();
                    Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    getListCars();
                } else {
                    Log.e("MainActivity", "Response unsuccessful: " + response.message());
                    Toast.makeText(MainActivity.this, "Thêm thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cars>> call, Throwable t) {
                Log.e("MainActivity", "Request failed: " + t.getMessage());
            }
        });
    }

    //Sửa Xe Service
    private void updateCar(String id, String nameCar, String colorCar, int yearCar, String engineTypeCar, int priceCar, int quantityCar, String imgCar) {
        Cars cars = new Cars();
        cars.setNameCar(nameCar);
        cars.setColorCar(colorCar);
        cars.setYearCar(yearCar);
        cars.setEngineTypeCar(engineTypeCar);
        cars.setPriceCar(priceCar);
        cars.setQuantityCar(quantityCar);
        cars.setImgCar(imgCar);

        Call<List<Cars>> call = CarService.carService.updateCar(id, cars);
        call.enqueue(new Callback<List<Cars>>() {
            @Override
            public void onResponse(Call<List<Cars>> call, Response<List<Cars>> response) {
                if (response.isSuccessful()) {
                    List<Cars> cars1 = response.body();
                    Toast.makeText(MainActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                    getListCars();
                } else {
                    Log.d("MAIN", "Respone Fail" + response.message());
                    Toast.makeText(MainActivity.this, "Sửa thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Cars>> call, Throwable t) {
                Log.d("MAIN", "Respone Fail" + t.getMessage());
            }
        });
    }

    //Xóa Xe Service
    private void deleteCars(String id) {
        Call<List<Cars>> call = CarService.carService.deleteCars(id);
        call.enqueue(new Callback<List<Cars>>() {
            @Override
            public void onResponse(Call<List<Cars>> call, Response<List<Cars>> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                    getListCars();
                } else {
                    Toast.makeText(MainActivity.this, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                    Log.e("API_ERROR", "Error: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<Cars>> call, Throwable t) {
                Log.e("API_ERROR", "Error: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        //Xử lý Tạo layout Thêm Xe
        if (view.getId() == R.id.btn_showDialogAddCar) {
            //Dialog Add Car
            final Dialog dialog = new Dialog(MainActivity.this);
            dialog.setCancelable(false);
            dialog.setContentView(R.layout.dialog_add_cars);
            Window window = dialog.getWindow();
            window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            if (dialog != null && dialog.getWindow() != null) {
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            }

            //Ánh xạ
            edNameCar = (EditText) dialog.findViewById(R.id.ed_nameCar);
            edPriceCar = (EditText) dialog.findViewById(R.id.ed_priceCar);
            edQuantityCar = (EditText) dialog.findViewById(R.id.ed_quantityCar);
            edColorCar = (EditText) dialog.findViewById(R.id.ed_colorCar);
            edYearCar = (EditText) dialog.findViewById(R.id.ed_yearCar);
            edEngineTypeCar = (EditText) dialog.findViewById(R.id.ed_engineTypeCar);
            edImgCar = (EditText) dialog.findViewById(R.id.ed_imgCar);

            tvAddCars = (TextView) dialog.findViewById(R.id.tv_addCars);
            tvCancelAddCsrs = (TextView) dialog.findViewById(R.id.tv_cancelAddCsrs);

            //Lấy giá trị Color
            edColorCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenuColor(view);
                }
            });
            //Lấy giá trị Engine Type
            edEngineTypeCar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showPopupMenuType(view);
                }
            });

            //Xử lý sự kiện thêm trong dialog
            tvAddCars.setOnClickListener(v -> {
                //Lấy dữ liệu trong edittext
                String name = edNameCar.getText().toString().trim();
                String color = edColorCar.getText().toString().trim();
                String year = edYearCar.getText().toString().trim();
                String type = edEngineTypeCar.getText().toString().trim();
                String price = edPriceCar.getText().toString().trim();
                String quantity = edQuantityCar.getText().toString().trim();
                String img = edImgCar.getText().toString().trim();
                //Chẹk dữ liệu
                if (checkValidate(name, color, year, type, price, quantity, img) == true){
                    //Gọi Service Thêm
                    addCar(name, color, Integer.parseInt(year), type, Integer.parseInt(price), Integer.parseInt(quantity), img);
                    dialog.dismiss();
                }
            });

            tvCancelAddCsrs.setOnClickListener(v -> {
                dialog.dismiss();
            });

            dialog.show();
        }
    }

    //Xử lý Tạo layout Sửa Xe
    @Override
    public void editCar(Cars cars) {
        //Tạo Dialog Sửa
        final Dialog dialog = new Dialog(MainActivity.this);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.dialog_edit_cars);
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
        //Ánh xạ
        edNameCar = (EditText) dialog.findViewById(R.id.ed_nameCar);
        edPriceCar = (EditText) dialog.findViewById(R.id.ed_priceCar);
        edQuantityCar = (EditText) dialog.findViewById(R.id.ed_quantityCar);
        edColorCar = (EditText) dialog.findViewById(R.id.ed_colorCar);
        edYearCar = (EditText) dialog.findViewById(R.id.ed_yearCar);
        edEngineTypeCar = (EditText) dialog.findViewById(R.id.ed_engineTypeCar);
        edImgCar = (EditText) dialog.findViewById(R.id.ed_imgCar);

        tvEditCars = (TextView) dialog.findViewById(R.id.tv_editCars);
        tvCancelEditCsrs = (TextView) dialog.findViewById(R.id.tv_cancelEditCsrs);

        //đẩy dữ liệu cũ lên textview
        edNameCar.setText(cars.getNameCar());
        edPriceCar.setText(cars.getPriceCar() + "");
        edQuantityCar.setText(cars.getQuantityCar() + "");
        edColorCar.setText(cars.getColorCar());
        edEngineTypeCar.setText(cars.getEngineTypeCar());
        edImgCar.setText(cars.getImgCar());
        edYearCar.setText(cars.getYearCar() + "");

        //Lấy dữ giá trị mới Color
        edColorCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenuColor(view);
            }
        });
        //Lấy giá trị mới Engine Type
        edEngineTypeCar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopupMenuType(view);
            }
        });

        tvEditCars.setOnClickListener(v -> {
            //Lấy giá trị trong ô edittext
            String name = edNameCar.getText().toString().trim();
            String color = edColorCar.getText().toString().trim();
            String year = edYearCar.getText().toString().trim();
            String type = edEngineTypeCar.getText().toString().trim();
            String price = edPriceCar.getText().toString().trim();
            String quantity = edQuantityCar.getText().toString().trim();
            String img = edImgCar.getText().toString().trim();
            //check dữ liệu
            if (checkValidate(name, color, year, type, price, quantity, img) == true){
                //Gọi service sửa
                updateCar(cars.getId(), name, color, Integer.parseInt(year), type, Integer.parseInt(price), Integer.parseInt(quantity), img);
                dialog.dismiss();
            }

        });

        tvCancelEditCsrs.setOnClickListener(v -> {
            dialog.dismiss();
        });

        dialog.show();
    }

    //Xử lý Tạo layout Xóa Xe
    @Override
    public void deleteCar(Cars cars) {
        // Tạo Dialog Xóa
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("Xóa Car");
        builder.setMessage("Bạn có chắc chắn muốn xóa " + cars.getNameCar() + " ?");
        builder.setCancelable(false);
        builder.setPositiveButton("Xóa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Gọi Service Xóa
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

    //Hiển thị Menu Màu
    private void showPopupMenuColor(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_color, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String titleColor = menuItem.getTitle().toString().trim();
                edColorCar.setText(titleColor);
                popupMenu.dismiss();
                return true;
            }
        });
    }

    //Hiển thị Menu Engine Type
    private void showPopupMenuType(View view) {
        PopupMenu popupMenu = new PopupMenu(this, view);
        popupMenu.getMenuInflater().inflate(R.menu.menu_type, popupMenu.getMenu());
        popupMenu.show();
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                String titleType = menuItem.getTitle().toString().trim();
                edEngineTypeCar.setText(titleType);
                popupMenu.dismiss();
                return true;
            }
        });
    }

    //Check dữ liệu
    private boolean checkValidate(String nameCar, String colorCar, String yearCar, String engineTypeCar, String priceCar, String quantityCar, String imgCar) {
        //Check trống
        if (nameCar.isEmpty() || colorCar.isEmpty() || yearCar.isEmpty() || engineTypeCar.isEmpty() || priceCar.isEmpty() || quantityCar.isEmpty() || imgCar.isEmpty()) {
            Toast.makeText(this, "Cần nhập đẩy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return false;
        }

        //Check các dữ liệu int
        int year = Integer.parseInt(yearCar);
        int price = Integer.parseInt(priceCar);
        int quantity = Integer.parseInt(quantityCar);

        if (year <= 1900 && year <= 2023) {
            Toast.makeText(this, "Năm sản xuất cần sau năm 1900 và trước năm 2024!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (price < 10) {
            Toast.makeText(this, "Giá xe cần lớn hơn 10!", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (quantity <= 0) {
            Toast.makeText(this, "Số lượng không được nhập số âm!", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}