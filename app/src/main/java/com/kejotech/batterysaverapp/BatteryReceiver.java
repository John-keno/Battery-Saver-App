package com.kejotech.batterysaverapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.BatteryManager;
import android.widget.ProgressBar;
import android.widget.TextView;

public class BatteryReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ProgressBar batteryLevel = ((MainActivity)context).findViewById(R.id.progress_bar);
        ProgressBar tempLevel = ((MainActivity)context).findViewById(R.id.battery_temp);
        TextView statusLabel = ((MainActivity)context).findViewById(R.id.battery_status);
        TextView tempLabel = ((MainActivity)context).findViewById(R.id.temp_value);
        TextView healthLabel = ((MainActivity)context).findViewById(R.id.health_value);
        TextView pluggedLabel = ((MainActivity)context).findViewById(R.id.plugged_value);
        TextView voltageLabel = ((MainActivity)context).findViewById(R.id.voltage_value);
        TextView percentageLabel = ((MainActivity)context).findViewById(R.id.battery_percent);

        String action = intent.getAction();

        if (action != null && action.equals(Intent.ACTION_BATTERY_CHANGED)) {

            // Status
            int status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            int health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1 );
            int plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1);
            float voltage  = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE,-1);
            int temp = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE,-1);
            int realTemp = temp/10;
            float realVoltage = voltage/1000;
            tempLabel.setText(realTemp + "\u00B0");
            tempLevel.setProgress(realTemp);
            voltageLabel.setText(realVoltage + "V");

            String statusMessage = "";

            switch (status) {
                case BatteryManager.BATTERY_STATUS_FULL:
                    statusMessage = "Full";
                    break;
                case BatteryManager.BATTERY_STATUS_CHARGING:
                    statusMessage = "Charging";
                    break;
                case BatteryManager.BATTERY_STATUS_DISCHARGING:
                    statusMessage = "Discharging";
                    break;
                case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                    statusMessage = "Not charging";
                    break;
                case BatteryManager.BATTERY_STATUS_UNKNOWN:
                    statusMessage = "Unknown";
                    break;
            }
            statusLabel.setText(statusMessage);

            String healthMessage = "";
            switch (health){
                case BatteryManager.BATTERY_HEALTH_GOOD:
                    healthMessage = "Good";
                    break;
                case BatteryManager.BATTERY_HEALTH_COLD:
                    healthMessage = "Cold";
                    break;
                case BatteryManager.BATTERY_HEALTH_DEAD:
                    healthMessage = "Dead";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE:
                    healthMessage = "Over Voltage";
                    break;
                case BatteryManager.BATTERY_HEALTH_OVERHEAT:
                    healthMessage = "Over Heat";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE:
                    healthMessage = "Unspecified Failure";
                    break;
                case BatteryManager.BATTERY_HEALTH_UNKNOWN:
                    healthMessage = "Unknown";
                    break;
            }
            healthLabel.setText(healthMessage);

            String pluggedMessage = "";
            switch (plugged){
                case BatteryManager.BATTERY_PLUGGED_AC:
                    pluggedMessage = "AC";
                    break;
                case BatteryManager.BATTERY_PLUGGED_USB:
                    pluggedMessage = "USB";
                    break;
                case BatteryManager.BATTERY_PLUGGED_WIRELESS:
                    pluggedMessage = "Wireless";
                    break;
                default:
                    pluggedMessage = "No";
            }

            pluggedLabel.setText(pluggedMessage);

            // Percentage
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int percentage = level * 100 / scale;
            percentageLabel.setText(percentage + "%");
            batteryLevel.setProgress(percentage);

        }
    }
}
