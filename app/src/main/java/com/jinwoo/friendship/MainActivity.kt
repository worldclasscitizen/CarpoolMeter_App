package com.jinwoo.friendship

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Geocoder
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import android.Manifest
import android.content.Context
import android.os.Build
import android.os.Looper
import android.util.Log
import androidx.core.app.ActivityCompat
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null // 현재 위치를 가져오기 위한 변수
    lateinit var mLastLocation: Location // 위치 값을 가지고 있는 객체
    internal lateinit var mLocationRequest: LocationRequest // 위치 정보 요청의 매개변수를 저장하는
    private val REQUEST_PERMISSION_LOCATION = 10

    lateinit var btnStartupdate: Button
    lateinit var btnStopUpdates: Button
    lateinit var txtLat: TextView
    lateinit var txtLong: TextView
    lateinit var txtTime: TextView


    protected fun startLocationUpdates() {

        //FusedLocationProviderClient의 인스턴스를 생성.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
            && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        // 기기의 위치에 관한 정기 업데이트를 요청하는 메서드 실행
        // 지정한 루퍼 스레드(Looper.myLooper())에서 콜백(mLocationCallback)으로 위치 업데이트를 요청합니다.
        Looper.myLooper()?.let {
            mFusedLocationProviderClient!!.requestLocationUpdates(mLocationRequest, mLocationCallback,
                it
            )
        }
    }

    // 시스템으로부터 위치 정보를 콜백으로 받음
    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // 시스템에서 받은 location 정보를 onLocationChanged()에 전달
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }
    // 시스템으로부터 받은 위치정보를 화면에 갱신해주는 메소드
    fun onLocationChanged(location: Location) {
        mLastLocation = location
        val date: Date = Calendar.getInstance().time
        val simpleDateFormat = SimpleDateFormat("hh:mm:ss a")
        txtTime.text = "Updated at : " + simpleDateFormat.format(date) // 갱신된 날짜
        txtLat.text = "LATITUDE : " + mLastLocation.latitude // 갱신 된 위도
        txtLong.text = "LONGITUDE : " + mLastLocation.longitude // 갱신 된 경도
    }
    // 위치 업데이터를 제거 하는 메서드
    private fun stoplocationUpdates() {
        // 지정된 위치 결과 리스너에 대한 모든 위치 업데이트를 제거
        mFusedLocationProviderClient!!.removeLocationUpdates(mLocationCallback)
    }

    // 위치 권한이 있는지 확인하는 메서드
    fun checkPermissionForLocation(context: Context): Boolean {
        // Android 6.0 Marshmallow 이상에서는 지리 확보(위치) 권한에 추가 런타임 권한이 필요합니다.
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // 권한이 없으므로 권한 요청 알림 보내기
                ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), REQUEST_PERMISSION_LOCATION)
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
                // View Button 활성화 상태 변경
                btnStartupdate.isEnabled = false
                btnStopUpdates.isEnabled = true
            } else {
                Toast.makeText(this@MainActivity, "권한이 없어 해당 기능을 실행할 수 없습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }




    fun start() {

        setContentView(R.layout.activity_start);

        val tv_title: TextView = findViewById(R.id.tv_title);
        val tv_subTitle: TextView = findViewById(R.id.tv_subTitle);
        val btn_meter: Button = findViewById(R.id.btn_meter);
        val btn_oil: Button = findViewById(R.id.btn_oil);
        val btn_test: Button = findViewById(R.id.btn_test);

        btn_meter.setOnClickListener {
            meter();
        }

        btn_oil.setOnClickListener {
            oil();
        }

        btn_test.setOnClickListener {
            test();
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

    fun test() {
        setContentView(R.layout.activity_g_p_s_permission)
        val imgbtn_gpsMain: ImageButton = findViewById(R.id.imgbtn_gpsMain);

        imgbtn_gpsMain.setOnClickListener {
            start();
        }

        // 화면뷰 inflate.
        btnStartupdate = findViewById(R.id.btn_start_upds)
        btnStopUpdates = findViewById(R.id.btn_stop_upds)
        txtLat = findViewById(R.id.tv_txtLat)
        txtLong = findViewById(R.id.tv_txtLong)
        txtTime = findViewById(R.id.tv_txtTime)

        // LocationRequest() deprecated 되서 아래 방식으로 LocationRequest 객체 생성
        // mLocationRequest = LocationRequest() is deprecated
        mLocationRequest =  LocationRequest.create().apply {
            interval = 500 // 업데이트 간격 단위(밀리초)
            fastestInterval = 500 // 가장 빠른 업데이트 간격 단위(밀리초)
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY // 정확성
            maxWaitTime= 500 // 위치 갱신 요청 최대 대기 시간 (밀리초)
        }

        // 위치 추척 시작 버튼 클릭시 처리
        btnStartupdate.setOnClickListener {
            if (checkPermissionForLocation(this)) {
                startLocationUpdates()
                // View Button 활성화 상태 변경
                btnStartupdate.isEnabled = false
                btnStopUpdates.isEnabled = true
            }
        }

        // 위치 추적 중지 버튼 클릭시 처리
        btnStopUpdates.setOnClickListener {
            stoplocationUpdates()
            txtTime.text = "Updates Stoped"
            // View Button 활성화 상태 변경
            btnStartupdate.isEnabled = true
            btnStopUpdates.isEnabled = false
        }
    }


































    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        start()
    }
}