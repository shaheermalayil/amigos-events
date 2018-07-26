package com.example.shaheer.crazyamigosevents

import android.app.AlertDialog
import android.app.DownloadManager
import android.app.VoiceInteractor
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.intentFor
import org.jetbrains.anko.toast
import org.jetbrains.anko.uiThread
import org.json.JSONObject

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val pref=getSharedPreferences("event",0)
        val token=pref.getString("access_token","")
        if (token !=""){
            startActivity(intentFor<Events>())
            finish()
        }

    }
    fun doLogin(view:View){

        if(username.text.toString().isEmpty()||password.text.toString().isEmpty())
            Toast.makeText(applicationContext,"Username or password invalid..!",Toast.LENGTH_SHORT).show()
        else
        {
            login()
        }
    }
    fun login(){
        progress.visibility=View.VISIBLE
        doAsync {
            val body = FormBody.Builder()
                    .add("username", username.text.toString())
                    .add("password", password.text.toString())
                    .build()
            val request = Request.Builder()
                    .url("https://test3.htycoons.in/api/login")
                    .post(body)
                    .build()
            val client = OkHttpClient()
            val response = client.newCall(request).execute()
        uiThread {
            toast(response.body().toString())
            progress.visibility=View.INVISIBLE
            when(response.code()){
                200 ->{
                    if(response.body()!=null){
                        val jsonResponse=JSONObject(response.body()!!.string())
                        val token=jsonResponse.getString("access_token")
                        Log.d("ACCESS",token)
                        val pref=getSharedPreferences("event",0)
                        val editor=pref.edit()
                        editor.putString("access_token",token)
                        editor.apply()
                        startActivity(intentFor<Events>())
                        finish()
                    }
                }
                400 ->{
                    AlertDialog.Builder(this@MainActivity)
                            .setTitle("Error")
                            .setMessage("An error occured !..")

                }
                404 ->{}
            }

        }

        }

    }
}
