package com.jinwoo.friendship

import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.*
import android.Manifest


class MainActivity : AppCompatActivity() {
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    lateinit var btn_oilGo: Button
    lateinit var tv_longitude: TextView
    lateinit var tv_latitude: TextView
    lateinit var tv_speed: TextView






    fun start() {
        setContentView(R.layout.activity_start)

        val tv_title: TextView = findViewById(R.id.tv_title)
        val tv_subTitle: TextView = findViewById(R.id.tv_subTitle)
        val btn_meter: Button = findViewById(R.id.btn_meter)
        val btn_oil: Button = findViewById(R.id.btn_oil)

        btn_meter.setOnClickListener {
            meter()
        }

        btn_oil.setOnClickListener {
            oil()
        }
    }

    fun meter() {
        setContentView(R.layout.activity_meter)
        val tv_meterFee: TextView = findViewById(R.id.tv_meterFee)
        val tv_meterWon: TextView = findViewById(R.id.tv_meterWon)
        val imgbtn_meterMain: ImageButton = findViewById(R.id.imgbtn_meterMain)
        val iv_meterRunningHorse: ImageView = findViewById(R.id.iv_meterRunningHorse)

        imgbtn_meterMain.setOnClickListener {
            start()
        }

        Glide.with(this)
            .load(R.drawable.running_horse).override(500,300)
            .skipMemoryCache(true)
            .into(iv_meterRunningHorse)
    }

    fun oil() {
        setContentView(R.layout.activity_oil)
        val tv_oilFee: TextView = findViewById(R.id.tv_oilFee)
        val tv_oilWon: TextView = findViewById(R.id.tv_oilWon)
        val imgbtn_oilMain: ImageButton = findViewById(R.id.imgbtn_oilMain)
        val iv_oilRunningHorse: ImageView = findViewById(R.id.iv_oilRunningHorse)
        btn_oilGo = findViewById(R.id.btn_oilGo)
        tv_longitude = findViewById(R.id.tv_longitude)
        tv_latitude = findViewById(R.id.tv_latitude)
        tv_speed = findViewById(R.id.tv_speed)
        mLocationRequest =  LocationRequest.create().apply {

            priority = LocationRequest.PRIORITY_HIGH_ACCURACY

        }

        // 버튼 이벤트를 통해 현재 위치 찾기
        btn_oilGo.setOnClickListener {
            if (checkPermissionForLocation(this)) {
                startLocationUpdates()

            }
        }

        imgbtn_oilMain.setOnClickListener {
            start()
        }

        Glide.with(this)
            .load(R.drawable.running_horse).override(500,300)
            .skipMemoryCache(true)
            .into(iv_oilRunningHorse)
    }

    private fun startLocationUpdates() {

        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청
        mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.getMainLooper())
    }

    // 시스템으로부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    // 시스템으로부터 받은 위치 정보를 화면에 갱신해 주는 메소드
    fun onLocationChanged(location: Location) {
        mLastLocation = location
        tv_latitude.text = "위도 : " + mLastLocation.latitude // 갱신 된 위도
        tv_longitude.text = "경도 : " + mLastLocation.longitude // 갱신 된 경도
        tv_speed.text = "속도 : " + mLastLocation.speed // 갱신 된 경도

    }

    // 위치 권한이 있는지 확인하는 메서드
    private fun checkPermissionForLocation(context: Context): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }

    // 사용자에게 권한 요청 후 결과에 대한 처리 로직
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION_LOCATION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startLocationUpdates()

            } else {
                Log.d("ttt", "onRequestPermissionsResult() _ 권한 허용 거부")
                Toast.makeText(this, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }





































    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        start()
    }
}