package com.example.gestion_inventario.data.remote

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

// .baseUrl("https://gestion-inventario-app-production.up.railway.app/api/v1/") // URL de la API

// Singleton que contiene la config de Retrofit
object RetrofitInstance {
    // Se instancia servicio de la API una sola vez
    val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl("https://vampishly-semimanneristic-jeannie.ngrok-free.dev/api/v1/")  // URL de la API (version local con Ngrok)
            .addConverterFactory(GsonConverterFactory.create()) // Conversor JSON
            .build()
            .create(ApiService::class.java)                     // Implementacion de interfaz ApiService
    }
}