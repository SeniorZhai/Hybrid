package io.seniorzhai.hybrid

import android.content.ContextWrapper
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import io.flutter.facade.Flutter
import io.flutter.plugin.common.MethodChannel
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        start.setOnClickListener {
            buildFlutter()
        }
    }

    private val flutterView by lazy {
        Flutter.createView(this, lifecycle, null)
    }

    private fun buildFlutter() {
        MethodChannel(flutterView, "seniorzhai.io").setMethodCallHandler { methodCall, result ->
            if (methodCall.method.equals("getBatteryLevel")) {
                val batteryLevel = getBatteryLevel()
                if (batteryLevel != -1) {
                    // 成功
                    result.success(batteryLevel)
                } else {
                    // 失败
                    result.error("UNAVAILABLE", "Battery level not available.", null)
                }
            } else {
                // 表示没有对应实现
                result.notImplemented()
            }
        }
        container.addView(flutterView, FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT))
    }

    private fun getBatteryLevel(): Int {
        val intent = ContextWrapper(applicationContext).registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))
        return intent!!.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) * 100 / intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1)
    }
}
