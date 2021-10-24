package com.example.kindleota

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.tooling.preview.Preview
import com.example.kindleota.data.ResponseDataItem
import com.example.kindleota.navigation.Navigation
import com.example.kindleota.retrofit.RetroService.createRetroInterface
import com.example.kindleota.retrofit.RetroService.getRetrofit
import com.example.kindleota.ui.theme.KindleOTATheme
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

val namelist: MutableState<List<String>> = mutableStateOf(listOf())

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getKindleNames()
        setContent {
            KindleOTATheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Navigation()
                }
            }
        }
    }
}

private fun getKindleNames() {
    val names = mutableListOf<String>()
    val retrodata = createRetroInterface(getRetrofit(BASE_URL)).getData()
    retrodata.enqueue(object : Callback<List<ResponseDataItem>?> {
        override fun onResponse(
            call: Call<List<ResponseDataItem>?>,
            response: Response<List<ResponseDataItem>?>
        ) {
            val reponsebody = response.body()!!
            reponsebody.forEach {
                names.add(it.name)
            }
            namelist.value = names
        }

        override fun onFailure(call: Call<List<ResponseDataItem>?>, t: Throwable) {
            Log.d("MainActivity", "Failure" + t.message)
        }
    })

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Navigation()
}
