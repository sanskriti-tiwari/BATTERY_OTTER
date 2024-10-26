package com.example.BATTERY_OTTER.data;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

public class DataLogger {

    private UsageStatsManager usageStatsManager;
    private Context context;

    // Constructor
    public DataLogger(Context context) {
        this.context = context;
        usageStatsManager = (UsageStatsManager) context.getSystemService(Context.USAGE_STATS_SERVICE);
    }

    // Method to fetch app usage data
    public void logAppUsageData() {
        Calendar calendar = Calendar.getInstance();
        long endTime = calendar.getTimeInMillis();
        calendar.add(Calendar.DAY_OF_YEAR, -1);
        long startTime = calendar.getTimeInMillis();

        // Get usage stats for the last day
        List<UsageStats> usageStatsList = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_DAILY, startTime, endTime);

        if (usageStatsList == null || usageStatsList.isEmpty()) {
            Log.d("DataLogger", "No app usage data available.");
            return;
        }

        for (UsageStats usageStats : usageStatsList) {
            String appName = usageStats.getPackageName();
            long totalTimeInForeground = usageStats.getTotalTimeInForeground() / 1000;
            Log.d("DataLogger", "App: " + appName + ", Time in Foreground: " + totalTimeInForeground + " seconds");
        }
    }

    // Method to fetch battery data
    public void logBatteryData() {
        IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        Intent batteryStatus = context.registerReceiver(null, ifilter);

        // Get battery percentage
        int level = batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
        int scale = batteryStatus.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
        float batteryPct = level * 100 / (float)scale;

        // Log battery percentage
        Log.d("DataLogger", "Battery level: " + batteryPct + "%");

        // Check if the device is charging
        int status = batteryStatus.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
        boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL;

        Log.d("DataLogger", "Is charging: " + isCharging);
    }
}
