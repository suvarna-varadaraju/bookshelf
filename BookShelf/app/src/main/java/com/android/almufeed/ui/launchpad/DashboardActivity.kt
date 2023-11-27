package com.android.almufeed.ui.launchpad

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.almufeed.databinding.ActivityDashboardBinding
import com.android.almufeed.datasource.network.models.token.CreateTokenRequest
import com.android.almufeed.datasource.network.models.token.CreateTokenResponse
import com.android.almufeed.ui.home.APIServices
import com.android.almufeed.ui.home.DocumentActivity
import com.android.almufeed.ui.home.TaskActivity
import com.android.almufeed.ui.services.BootUpReceiver
import dagger.hilt.android.AndroidEntryPoint
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Date

@AndroidEntryPoint
class DashboardActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDashboardBinding
    private lateinit var resoureId : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        resoureId = intent.getStringExtra("resourceId").toString()

        binding.apply {

            txtDoc.setOnClickListener {
                Intent(this@DashboardActivity, DocumentActivity::class.java).apply {
                    startActivity(this)
                }
                /*val toastIntent = Intent(applicationContext, BootUpReceiver::class.java)
                val toastAlarmIntent = PendingIntent.getBroadcast(
                    applicationContext,
                    0,
                toastIntent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
                )
                val startTime = System.currentTimeMillis() //alarm starts immediately

                val backupAlarmMgr = getSystemService(Context.ALARM_SERVICE) as AlarmManager
                backupAlarmMgr.setInexactRepeating(
                    AlarmManager.RTC_WAKEUP,
                    startTime,
                    5000,
                    toastAlarmIntent
                )*/
              /*  homeViewModel.createBusiness(
                    "7d2f26f6-2e67-4299-9abd-fbac27deff25",
                    "rcI8Q~eugdoR2M0Yx8_gkTPqqyPyT.sn9ab3BdeF",
                    "client_credentials",
                    "https://almdevb0bb67faa678fcfadevaos.axcloud.dynamics.com"
                )*/

                //getTokenApi()
            }

            txtActivity.setOnClickListener {
                Intent(this@DashboardActivity, TaskActivity::class.java).apply {
                    putExtra("resourceId",resoureId)
                    startActivity(this)
                }
            }
        }
    }

     private fun getTokenApi() {

        /* val client = OkHttpClient()
         val mediaType = "application/x-www-form-urlencoded".toMediaType()
         val body = "client_id=7d2f26f6-2e67-4299-9abd-fbac27deff25&client_secret=rcI8Q~eugdoR2M0Yx8_gkTPqqyPyT.sn9ab3BdeF&grant_type=client_credentials&resource=https://almdevb0bb67faa678fcfadevaos.axcloud.dynamics.com".toRequestBody(mediaType)
         val request = Request.Builder()
             .url("https://login.microsoftonline.com/8bd1367c-efa4-40b4-acac-9f3e4c82000b/oauth2/token")
             .post(body)
             .addHeader("Content-Type", "application/x-www-form-urlencoded")
             .addHeader("Cookie", "fpc=AhhlLpiHrg1Lq-3EvIUzBvlCIhD-AQAAAF195twOAAAA; stsservicecookie=estsfd; x-ms-gateway-slice=estsfd")
             .build()*/

         //val getToken = client.newCall(request)
         val mediaType = "application/x-www-form-urlencoded".toMediaType()
         val body = "client_id=7d2f26f6-2e67-4299-9abd-fbac27deff25&client_secret=rcI8Q~eugdoR2M0Yx8_gkTPqqyPyT.sn9ab3BdeF&grant_type=client_credentials&resource=https://almdevb0bb67faa678fcfadevaos.axcloud.dynamics.com".toRequestBody(mediaType)
         val getToken = APIServices.create().getProducts(body)
         getToken.enqueue(object : Callback<CreateTokenResponse> {
             override fun onResponse(
                 call: Call<CreateTokenResponse>,
                 response: Response<CreateTokenResponse>
             ) {
                 if (response.isSuccessful) {
                     Log.d("products", response.body().toString())
                     if (response.body() != null) {

                       login()
                     }
                 }
             }

             override fun onFailure(call: Call<CreateTokenResponse>, t: Throwable) {
                 Log.d("failure", t.message.toString())
                 Toast.makeText(
                     this@DashboardActivity,
                     "onFailure: " + t.message,
                     Toast.LENGTH_SHORT
                 ).show()
             }
         })


       /* val requestBody = CreateTokenRequest("7d2f26f6-2e67-4299-9abd-fbac27deff25", "rcI8Q~eugdoR2M0Yx8_gkTPqqyPyT.sn9ab3BdeF","client_credentials","https://almdevb0bb67faa678fcfadevaos.axcloud.dynamics.com")
         System.out.println("request1 " + requestBody)
         val mediaType = "application/x-www-form-urlencoded".toMediaType()
         val body = "client_id=7d2f26f6-2e67-4299-9abd-fbac27deff25&client_secret=rcI8Q~eugdoR2M0Yx8_gkTPqqyPyT.sn9ab3BdeF&grant_type=client_credentials&resource=https://almdevb0bb67faa678fcfadevaos.axcloud.dynamics.com".toRequestBody(mediaType)
         try {
             val getToken = APIServices.create().getProducts(body)
             System.out.println("request2 " + getToken.request() + " " + getToken.isExecuted)
             getToken.enqueue(object : Callback<CreateTokenResponse> {
                 override fun onResponse(
                     call: Call<CreateTokenResponse>,
                     response: Response<CreateTokenResponse>
                 ) {
                     if (response.isSuccessful) {
                         Log.d("products", response.body()!!.access_token)
                         if (response.body() != null) {

                         }
                     }
                 }

                 override fun onFailure(call: Call<CreateTokenResponse>, t: Throwable) {
                     Log.d("failure", t.message.toString())
                     Toast.makeText(
                         this@DashboardActivity,
                         "onFailure: " + t.message,
                         Toast.LENGTH_SHORT
                     ).show()
                 }
             })*/
       /*  }catch(e:Exception){
             e.printStackTrace()
         }*/
    }
    private fun login() {

        /*     val mediaType = "application/x-www-form-urlencoded".toMediaType()
        val body = "client_id=7d2f26f6-2e67-4299-9abd-fbac27deff25&client_secret=rcI8Q~eugdoR2M0Yx8_gkTPqqyPyT.sn9ab3BdeF&grant_type=client_credentials&resource=https://almdevb0bb67faa678fcfadevaos.axcloud.dynamics.com".toRequestBody(mediaType)
        val getToken = APIServices.create().getProducts(body)
        getToken.enqueue(object : Callback<CreateTokenResponse> {
            override fun onResponse(
                call: Call<CreateTokenResponse>,
                response: Response<CreateTokenResponse>
            ) {
                if (response.isSuccessful) {
                    Log.d("products", response.body().toString())
                    if (response.body() != null) {

                        login()
                    }
                }
            }

            override fun onFailure(call: Call<CreateTokenResponse>, t: Throwable) {
                Log.d("failure", t.message.toString())
                Toast.makeText(
                    this@DashboardActivity,
                    "onFailure: " + t.message,
                    Toast.LENGTH_SHORT
                ).show()
            }
        })*/


        val requestBody = CreateTokenRequest(
            "7d2f26f6-2e67-4299-9abd-fbac27deff25",
            "rcI8Q~eugdoR2M0Yx8_gkTPqqyPyT.sn9ab3BdeF",
            "client_credentials",
            "https://almdevb0bb67faa678fcfadevaos.axcloud.dynamics.com"
        )
        System.out.println("request1 " + requestBody)
        val mediaType = "application/x-www-form-urlencoded".toMediaType()
        val body =
            "client_id=7d2f26f6-2e67-4299-9abd-fbac27deff25&client_secret=rcI8Q~eugdoR2M0Yx8_gkTPqqyPyT.sn9ab3BdeF&grant_type=client_credentials&resource=https://almdevb0bb67faa678fcfadevaos.axcloud.dynamics.com".toRequestBody(
                mediaType
            )
        try {
            val getToken = APIServices.create().getProducts(body)
            System.out.println("request2 " + getToken.request() + " " + getToken.isExecuted)
            getToken.enqueue(object : Callback<CreateTokenResponse> {
                override fun onResponse(
                    call: Call<CreateTokenResponse>,
                    response: Response<CreateTokenResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.d("token", response.body()!!.access_token)
                        if (response.body() != null) {

                        }
                    }
                }

                override fun onFailure(call: Call<CreateTokenResponse>, t: Throwable) {
                    Log.d("failure", t.message.toString())
                    Toast.makeText(
                        this@DashboardActivity,
                        "onFailure: " + t.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}