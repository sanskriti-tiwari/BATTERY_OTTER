package com.example.BATTERY_OTTER.ui;

import android.os.Bundle;
import android.util.Log;
import androidx.appcompat.app.AppCompatActivity;
import com.example.yourappname.data.DataLogger;

public class MainActivity extends AppCompatActivity {

    private DataLogger dataLogger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize DataLogger
        dataLogger = new DataLogger(this);

        // Collect and display usage and battery data
        logData();
    }

    private void logData() {
        dataLogger.logAppUsageData();  // Collect app usage data
        dataLogger.logBatteryData();   // Collect battery data
    }
}
