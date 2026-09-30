package com.smartpantry.manager;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.Switch;

import androidx.appcompat.app.AppCompatActivity;

public class SettingsActivity extends AppCompatActivity {

    private static final String PREFS = "pantry_settings";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Switch switchExpiryAlerts =
                findViewById(R.id.switchExpiryAlerts);

        RadioButton radioMetric =
                findViewById(R.id.radioMetric);

        RadioButton radioSimple =
                findViewById(R.id.radioSimple);

        Button btnBackSettings =
                findViewById(R.id.btnBackSettings);

        SharedPreferences preferences =
                getSharedPreferences(PREFS, MODE_PRIVATE);

        switchExpiryAlerts.setChecked(
                preferences.getBoolean("expiry_alerts", false)
        );

        boolean metric =
                preferences.getBoolean("metric_units", true);

        radioMetric.setChecked(metric);
        radioSimple.setChecked(!metric);

        switchExpiryAlerts.setOnCheckedChangeListener(
                (buttonView, isChecked) ->
                        preferences.edit()
                                .putBoolean(
                                        "expiry_alerts",
                                        isChecked
                                )
                                .apply()
        );

        radioMetric.setOnClickListener(v ->
                preferences.edit()
                        .putBoolean("metric_units", true)
                        .apply()
        );

        radioSimple.setOnClickListener(v ->
                preferences.edit()
                        .putBoolean("metric_units", false)
                        .apply()
        );

        btnBackSettings.setOnClickListener(v -> finish());
    }
}