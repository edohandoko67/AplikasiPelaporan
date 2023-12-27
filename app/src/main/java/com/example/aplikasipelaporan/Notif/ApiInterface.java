package com.example.aplikasipelaporan.Notif;


import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiInterface {
//    @Headers({"Authorization: "+SERVER_KEY,"Content-Type: "+CONTENT_TYPE})
@Headers({
        "Authorization: key=AAAAJkX_m-s:APA91bFAaF6Y1mj9G6V1IWOIPz3_82ooYP_nXP3Drdz6h7axhhnZDbAuqTGh3yQHRPg9ZqOnJmsCFadFiznhhAN1U5o59nq0xBJNhPziu9GO7X7talpn0CqTE4q8-otQTJEn9JMYLYj4",  // Replace YOUR_SERVER_KEY with your actual server key
        "Content-Type: application/json"
})
    @POST("fcm/send")
    Call<PushNotification> sendNotification(@Body PushNotification pushNotification);
}
