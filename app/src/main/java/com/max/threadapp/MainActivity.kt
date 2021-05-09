package com.max.threadapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    var currentvalue = 0
    val percent = "%"
    val LOG_TAG = "requestThread"
    var suspendFlag: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClick(view: View) {
        val indicatorbar: ProgressBar = findViewById(R.id.pb_horizontal)
        val textview: TextView = findViewById(R.id.tv_horizontal)
        object : Thread() {
            override fun run() {
                suspendFlag = true
                while (currentvalue <= 100 && suspendFlag) {
                    try {
                        textview.post {
                            indicatorbar.setProgress(currentvalue)

                            textview.text = getString(R.string.current, currentvalue, percent)
                            Log.d(LOG_TAG, "Запрос на сервер")
                        }
                        sleep(4000)

                    } catch (e: InterruptedException) {

                        textview.post { textview.setText(e.message) }
                        break

                    }

                    currentvalue++

                }

            }
        }.start()
    }

    override fun onPause() {
        super.onPause()
        suspendFlag = false
        while (!suspendFlag) {
            try {
                Thread.interrupted()
                Log.d(LOG_TAG, "Поток остановлен")
                break
            } catch (e: InterruptedException) {

            }
        }
    }

    override fun onResume() {
            val indicatorbar: ProgressBar = findViewById(R.id.pb_horizontal)
            val textview: TextView = findViewById(R.id.tv_horizontal)
            super.onResume()
            suspendFlag = true

            object : Thread() {
                override fun run() {
                    while (currentvalue <= 100 && !suspendFlag) {
                        try {
                            textview.post {
                                indicatorbar.setProgress(currentvalue)
                                textview.text = getString(R.string.current, currentvalue, percent)
                            }
                            sleep(4000)
                            currentvalue++
                        } catch (e: InterruptedException) {
                            textview.post { textview.setText(e.message) }
                            break
                        }

                        Log.d(LOG_TAG, "Запрос на сервер")
                    }
                }
            }.start()
        }
    }


