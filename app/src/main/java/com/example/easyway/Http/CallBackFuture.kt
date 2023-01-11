package com.example.easyway.Http
import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.Call
import okhttp3.Response
import java.util.concurrent.CompletableFuture
import okhttp3.Callback
import java.io.IOException

// Classe necessária para retornar a resposta do CallBack
@RequiresApi(Build.VERSION_CODES.N)
class CallBackFuture: CompletableFuture<Response>(),Callback
{
    override fun onFailure(call: Call, e: IOException) {
        super.completeExceptionally(e)
    }

    override fun onResponse(call: Call, response: Response) {
        super.complete(response)
    }

}