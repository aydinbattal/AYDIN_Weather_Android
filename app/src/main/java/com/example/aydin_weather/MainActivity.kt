package com.example.aydin_weather

import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.aydin_weather.network.Weather
import com.example.aydin_weather.viewmodel.WeatherViewModel
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat

/**
 * AYDIN_Weather created by aydin
 * student ID : 991521740
 * on 20/11/20 */

class MainActivity : AppCompatActivity() {
    private val TAG = this@MainActivity.toString()

    private lateinit var locationManager: LocationManager
    private lateinit var location: Location
    private lateinit var currentLocation : LatLng
    private lateinit var locationCallback: LocationCallback

    private lateinit var weatherViewModel: WeatherViewModel
    private var baseUrl = "https://api.aerisapi.com/observations/"
    private var endUrl = "?query&client_id=BQ3gZWzdzR9zHi61O18IO&client_secret=xCPYHTl4b3I9TUp9iudiBwyK8HekHBxIAboWe9na"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        weatherViewModel = WeatherViewModel()

        this.locationManager = LocationManager(this@MainActivity)
        this.currentLocation = LatLng(0.0, 0.0)

        if (LocationManager.locationPermissionsGranted){
            this.getLastLocation()
        }

        locationCallback = object : LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult?){
                locationResult ?: return

                for(location in locationResult.locations){
                    currentLocation = LatLng(location.latitude, location.longitude)

                }
                //dynamic ui
                getWeatherInfo(currentLocation)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        locationManager.requestLocationUpdates(locationCallback)
    }

    override fun onPause() {
        super.onPause()
        locationManager.fusedLocationProviderClient?.removeLocationUpdates(locationCallback)
    }

    private fun getLastLocation(){
        this.locationManager.getLastLocation()?.observe(this, { loc: Location? ->
            if (loc != null) {
                this.location = loc
                this.currentLocation = LatLng(location.latitude, location.longitude)

                Log.e(TAG, "current location : " + this.currentLocation.toString())

                //static ui
//                getWeatherInfo(this.currentLocation)
            }
        })

    }

    private fun getWeatherInfo(weatherLocation: LatLng){
        val apiUrl = this.baseUrl + weatherLocation.latitude + "," + weatherLocation.longitude + this.endUrl

        Log.e(TAG, "apiUrl : " + apiUrl)

        this.weatherViewModel.getWeatherInfo(apiUrl)

        this.weatherViewModel.response.observe(this, {
            Log.e(TAG, "weather response : " + it.toString())
            this.displayWeatherData(it)
        })
    }

    private fun displayWeatherData(weather: Weather){
        if (weather.response != null){
            with(weather.response){
                if (this.obtained != null){
                    with(this.obtained){
                        if (this.degree != null){
                            tvDegree.setText(this.degree.toString() + "\u2103")
                        } else {
                            tvDegree.setText("unavailable")
                        }
                        if (this.degreeFeels != null){
                            tvDegreeFeels.setText("Feels Like " + this.degreeFeels.toString() + "\u2103")
                        } else {
                            tvDegreeFeels.setText("unavailable")
                        }
                        if (this.windSpeed != null && this.windDirection != null){
                            tvWindNum.setText(this.windSpeed.toString() + " kph - " + this.windDirection.toString())
                        } else {
                            tvWindNum.setText("unavailable")
                        }
                        if (this.humidity != null){
                            tvHumidityNum.setText(this.humidity.toString() + "%")
                        } else {
                            tvHumidityNum.setText("unavailable")
                        }
                        if (this.visibility != null){
                            tvVisibilityNum.text = "%.2f".format(visibility) + " km"
                        } else {
                            tvVisibilityNum.setText("unavailable")
                        }

                        val date = System.currentTimeMillis()
                        val sdf = SimpleDateFormat("MMMM dd, yyyy h:mm a")
                        tvDate.setText(sdf.format(date))
                    }
                }
            }
        }
    }


}