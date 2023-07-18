package com.example.baiasm1.api;

import com.example.baiasm1.model.Cars;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CarService {

    Gson gson = new GsonBuilder().setDateFormat("dd-MM-yyyy").create();
    CarService carService = new Retrofit.Builder()
            .baseUrl("http://192.168.1.91:8080/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(CarService.class);
    @GET("listCar")
    Call<List<Cars>> getCar();

    @POST("addCars")
    Call<Cars> addCar(@Body Cars cars);

    @PUT("cars/{id}")
    Call<Cars> updateCar(@Path("id") String id, @Body Cars cars);

    @DELETE("cars/{id}")
    Call<Void> deleteCars(@Path("id") String id);
}
