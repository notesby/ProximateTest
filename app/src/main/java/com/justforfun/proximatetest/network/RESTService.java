package com.justforfun.proximatetest.network;

import com.justforfun.proximatetest.model.GetProfileResponse;
import com.justforfun.proximatetest.model.LoginRequest;
import com.justforfun.proximatetest.model.LoginResponse;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Created by hectormoreno on 9/5/17.
 */

public interface RESTService {


    @Headers("Content-Type: application/json")
    @POST("catalog/dev/webadmin/authentication/login")
    Observable<LoginResponse> login(@Body LoginRequest request);


    @POST("catalog/dev/webadmin/users/getdatausersession")
    Observable<GetProfileResponse> getProfile(@Header("Authorization") String token);


}
