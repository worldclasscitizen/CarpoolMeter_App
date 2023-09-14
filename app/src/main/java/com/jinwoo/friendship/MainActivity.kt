package com.jinwoo.friendship

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide

class MainActivity : AppCompatActivity() {

    fun start() {

        setContentView(R.layout.activity_start);

        val tv_title: TextView = findViewById(R.id.tv_title);
        val tv_subTitle: TextView = findViewById(R.id.tv_subTitle);
        val btn_meter: Button = findViewById(R.id.btn_meter);
        val btn_oil: Button = findViewById(R.id.btn_oil);

        btn_meter.setOnClickListener {
            meter();
        }

        btn_oil.setOnClickListener {
            oil();
        }
    }

    fun meter() {
        setContentView(R.layout.activity_meter);
        val tv_meterFee: TextView = findViewById(R.id.tv_meterFee);
        val tv_meterWon: TextView = findViewById(R.id.tv_meterWon);
        val imgbtn_meterMain: ImageButton = findViewById(R.id.imgbtn_meterMain);
        val iv_meterRunningHorse: ImageView = findViewById(R.id.iv_meterRunningHorse);

        imgbtn_meterMain.setOnClickListener {
            start();
        }

        Glide.with(this)
            .load(R.drawable.running_horse).override(500,300)
            .skipMemoryCache(true)
            .into(iv_meterRunningHorse)
    }

    fun oil() {
        setContentView(R.layout.activity_oil);
        val tv_oilFee: TextView = findViewById(R.id.tv_oilFee);
        val tv_oilWon: TextView = findViewById(R.id.tv_oilWon);
        val imgbtn_oilMain: ImageButton = findViewById(R.id.imgbtn_oilMain);
        val iv_oilRunningHorse: ImageView = findViewById(R.id.iv_oilRunningHorse);

        imgbtn_oilMain.setOnClickListener {
            start();
        }


        Glide.with(this)
            .load(R.drawable.running_horse).override(500,300)
            .skipMemoryCache(true)
            .into(iv_oilRunningHorse)
    }


































    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }
}