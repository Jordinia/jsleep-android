package com.RizkiAwantaJordhieJSleepKM.jsleep_android.request;

import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Account;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.BedType;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.City;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Facility;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Payment;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Renter;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Room;
import com.RizkiAwantaJordhieJSleepKM.jsleep_android.model.Voucher;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface BaseApiService {
    //AccountController BaseApi
    @GET("account/{id}")
    Call<Account> getAccount (@Path("id") int id);

    @POST("account/login")
    Call<Account> login (@Query("email") String email,
                         @Query("password") String password);

    @POST("account/register")
    Call<Account> register  (@Query("name") String name,
                             @Query("email") String email,
                             @Query("password") String password);

    @POST("account/{id}/registerRenter")
    Call<Renter> registerRenter(@Path("id") int id,
                                @Query("username") String username,
                                @Query("address") String address,
                                @Query("phoneNumber") String phoneNumber);

    @POST("account/{id}/topUp")
    Call<Boolean> topUp(@Path("id") int id,
                        @Query("balance") int balance);

    //PaymentController BaseApi
    @GET("payment/{id}")
    Call<Payment> getPayment(@Path("id") int id);

    @POST("payment/create")
    Call<Payment> createPayment ( @Query("buyerId") int buyerId,
                                  @Query("renterId") int renterId,
                                  @Query("roomId") int roomId,
                                  @Query("from") String from,
                                  @Query("to") String to);

    @POST("payment/{id}/accept")
    Call<Boolean> accept (@Path("id") int id);

    @POST("payment/{id}/cancel")
    Call<Boolean> cancel (@Path("id") int id);

    @GET("payment/getOrderForRenter")
    Call<List<Payment>> getOrderForRenter(@Query("renterId") int renterId,
                                          @Query("page") int page,
                                          @Query("pageSize") int pageSize);

    @GET("payment/getOrderForBuyer")
    Call<List<Payment>> getOrderForBuyer(@Query("buyerId") int buyerId);

    //RoomController BaseApi
    @GET("room/{id}")
    Call<Room> getRoom (@Path("id") int id);

    @GET("room/{id}/renter")
    Call<List<Room>> getRoomByRenter (@Path("id") int id,
                                      @Query("page") int page,
                                      @Query("pageSize") int pageSize);

    @POST("room/create")
    Call<Room> createRoom(
                        @Query("accountId") int accountId,
                        @Query("name") String name,
                        @Query("size") int size,
                        @Query("price") int price,
                        @Query("facility") ArrayList<Facility> facility,
                        @Query("city") City city,
                        @Query("address") String address,
                        @Query("bedType") BedType bedType);

    @GET("room/getAllRoom")
    Call<List<Room>> getAllRoom(@Query("page") int page, @Query("pageSize") int pageSize);

    //VoucherController BaseApi
    @GET("voucher/{id}")
    Call<Voucher> getVoucher (@Path("id") int id);

    @GET("voucher/{id}/isUsed")
    Call<Boolean> isUsed (@Path("id") int id);

    @GET("voucher/{id}/canApply")
    Call<Boolean> canApply (@Path("id") int id,
                            @Query("price") double price);

    @GET("voucher/getAvailable")
    Call<List<Voucher>> getAvailable(@Query("page") int page,
                                     @Query("pageSize") int pageSize);
}
