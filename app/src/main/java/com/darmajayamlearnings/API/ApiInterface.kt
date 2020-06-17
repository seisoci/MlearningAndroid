package com.darmajaya.globalsurya.API

import com.darmajayamlearnings.Model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("api/user/login")
    fun signIn(@Field("email") username: String?, @Field("password") password: String?): Call<Auth>

    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("api/kelas")
    fun joinKelas(@Header("Authorization") token:String, @Field("kelas_mlearning_id") kelas_mlearning_id: String?, @Field("siswa_id") siswa_id: String?): Call<RJoinKelas>

    @GET("api/user/kelas")
    @Headers("Accept: application/json")
    fun userkelas(@Header("Authorization") token:String): Call<Kelas>

    @GET("api/kelas/listkelas")
    @Headers("Accept: application/json")
    fun listkelas(): Call<RKelas>

    @GET("api/kelas")
    @Headers("Accept: application/json")
    fun listKelasTersedia(@Header("Authorization") token:String): Call<TambahKelas>

    @GET("api/materi/{id}")
    @Headers("Accept: application/json")
    fun listmateri(@Header("Authorization") token:String, @Path("id") id: String): Call<Materi>

    @Multipart
    @POST("api/user/register")
    @Headers("Accept: application/json")
    fun register(
        @Part photo: MultipartBody.Part,
        @PartMap text: Map<String, @JvmSuppressWildcards RequestBody>
    ): Call<Auth>


    @GET("api/komentar/{id}}")
    @Headers("Accept: application/json")
    fun listkomentar(@Header("Authorization") token:String, @Path("id") id: String): Call<Komentar>


    @FormUrlEncoded
    @Headers("Accept: application/json")
    @POST("api/komentar")
    fun addKomentar(@Header("Authorization") token:String, @Field("users_id") users_id: String?, @Field("materi_mlearning_id") materi_mlearning_id: String?, @Field("komentar") komentar: String?): Call<RJoinKelas>
}