package com.example.gestion_inventario.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// Singleton que contiene la config de Retrofit
object RetrofitInstance {
    // Se instancia servicio de la API una sola vez
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://gestion-inventario-app-production.up.railway.app/api/v1/") // URL de la API
            .addConverterFactory(GsonConverterFactory.create()) // Conversor JSON
            .build()
            .create(ApiService::class.java)                     // Implementacion de interfaz ApiService
    }
}