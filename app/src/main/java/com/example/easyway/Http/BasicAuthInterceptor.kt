package com.example.easyway.Http

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class BasicAuthInterceptor(val userName:String,val password:String): Interceptor
{
private var credentials: String = Credentials.basic(userName,password)

    // Método que possibita a autenticação básica para todos os pedidos do web service
    override fun intercept(chain: Interceptor.Chain): Response
    {
        var request = chain.request()
        request = request.newBuilder().header("Authorization",credentials).build()
        return chain.proceed(request)
    }


}