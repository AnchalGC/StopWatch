package com.mdev.stopwatch

import android.os.Bundle
import android.os.SystemClock
import android.widget.Button
import android.widget.Chronometer
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    lateinit var stopwatch : Chronometer
    lateinit var startButton : Button
    var running = false
    var offset: Long = 0

    // key string use with bundle
    val OFFSET_KEY = "offset"
    val RUNNING_KEY = "running"
    var BASE_KEY ="base"

    override fun onCreate(myBundle: Bundle?) {
        super.onCreate(myBundle)
        setContentView(R.layout.activity_main)

        // get Reference of the buttons
        stopwatch = findViewById<Chronometer>(R.id.stopwatch)

        //Restore the previous state
        if (myBundle != null){
           offset = myBundle.getLong(OFFSET_KEY)
            running = myBundle.getBoolean(RUNNING_KEY)
            if(running){
                stopwatch.base = myBundle.getLong(BASE_KEY)
                stopwatch.start()
            } else{
                setBaseTime()
            }
        }

        // start button
        val startButton = findViewById<Button>(R.id.start_button)
        startButton.setOnClickListener{
            if(!running){     // when stopwatch is running
                setBaseTime()
                stopwatch.start()
                running = true
            }
        }
       // pause button
        val pauseButton = findViewById<Button>(R.id.pause_button)
        pauseButton.setOnClickListener{
            if(running){ // when stopwatch is not running
                saveOffset()
                stopwatch.stop()
                running = false
            }
        }

        // reset button
        val resetButton = findViewById<Button>(R.id.reset_button)
        resetButton.setOnClickListener{
             offset = 0
            setBaseTime()
        }
    }
    //
    override fun onSaveInstanceState(myBundle: Bundle) {
        // insert value to bundle
        myBundle.putLong(OFFSET_KEY,offset)
        myBundle.putBoolean(RUNNING_KEY,running)
        myBundle.putLong(BASE_KEY,stopwatch.base)
        super.onSaveInstanceState(myBundle)
    }

    override fun onPause() {
        super.onPause()
        if(running){
            saveOffset()
            stopwatch.stop()
        }
    }

    override fun onResume() {
        super.onResume()
        if(running){
            setBaseTime()
            stopwatch.start()
            offset= 0
        }
    }
    // update the stopwatch base time
    fun setBaseTime(){
        val time = findViewById<EditText>(R.id.editTextNumber)
        val offset = time.getText().toString().toInt()
        stopwatch.base = SystemClock.elapsedRealtime() - (offset * 1000)
    }

    // record the offset
    fun saveOffset(){
        offset = SystemClock.elapsedRealtime()-stopwatch.base
    }
}

private fun Bundle.putLong(runningKey: String, running: Boolean) {

}
