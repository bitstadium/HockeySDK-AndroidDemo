package net.hockeyapp.demo.android;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import net.hockeyapp.android.*;
import net.hockeyapp.android.metrics.MetricsManager;
import net.hockeyapp.android.utils.HockeyLog;
import net.hockeyapp.android.utils.Util;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private boolean mScreenshotActivitySet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // 1. Crash Reporting - forcing a crash

        Button crashButton = (Button) findViewById(R.id.crash_button);
        crashButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Just some code to cause an IndexOutOfBoundsException
                List<String> list = new ArrayList<>();
                String fail = list.get(0);
            }
        });

        // 2. Updates, check for updates

        Button updateButton = (Button) findViewById(R.id.update_button);
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForUpdates();
            }
        });

        Button updateCustomButton = (Button) findViewById(R.id.button_update_custom);
        updateCustomButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkForUpdatesCustom();
            }
        });


        // 3.1 Feedback, showing the feedback activity

        Button feedbackButton = (Button) findViewById(R.id.feedback_button);
        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FeedbackManager.showFeedbackActivity(MainActivity.this);
            }
        });

        FeedbackManager.register(this);

        // 3.2 Feedback, show Screenshot for Feedback action

        Button feedbackScreenshotButton = (Button) findViewById(R.id.button_feedback_screenshot);
        feedbackScreenshotButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!mScreenshotActivitySet) {
                    FeedbackManager.setActivityForScreenshot(MainActivity.this);
                } else {
                    FeedbackManager.unsetCurrentActivityForScreenshot(MainActivity.this);
                }
                mScreenshotActivitySet = !mScreenshotActivitySet;

            }
        });

        // 5. Login, show login dialog then private activity

        Button loginEmailButton = (Button) findViewById(R.id.button_login_email);
        loginEmailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent privateActivityIntent = new Intent(MainActivity.this, PrivateActivity.class);
                doLoginCheck(LoginManager.LOGIN_MODE_EMAIL_ONLY, privateActivityIntent);
            }
        });

        Button loginFullButton = (Button) findViewById(R.id.button_login_full);
        loginFullButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent privateActivityIntent = new Intent(MainActivity.this, PrivateActivity.class);
                doLoginCheck(LoginManager.LOGIN_MODE_EMAIL_PASSWORD, privateActivityIntent);
            }
        });


        Button registerMetricsManagerButton = (Button) findViewById(R.id.button_init_metrics);
        registerMetricsManagerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MetricsManager.register(getApplication());
            }
        });

        final Button enableMetricsButton = (Button) findViewById(R.id.button_enable_metrics);
        enableMetricsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(MetricsManager.sessionTrackingEnabled()) {
                    MetricsManager.setSessionTrackingDisabled(true);
                    enableMetricsButton.setText(getText(R.string.action_erable_metrics));
                }
                else {
                    MetricsManager.setSessionTrackingDisabled(false);
                    enableMetricsButton.setText(getText(R.string.action_disable_metrics));

                }
            }
        });

        final Button sendEventButton = (Button) findViewById(R.id.button_send_event);
        sendEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> properties = new HashMap<>();
                properties.put("property-a", "value-a");
                properties.put("date", new java.util.Date().toString());
                Map<String, Double> measurements = new HashMap<>();
                measurements.put("time", Math.random());
                MetricsManager.trackEvent("Test Event", properties, measurements);
            }
        });


        HockeyLog.setLogLevel(Log.VERBOSE);
    }

    @Override
    protected void onResume() {
        super.onResume();

        checkForCrashes();
    }

    private void checkForCrashes() {
        CrashManager.register(this);
    }

    private void checkForUpdates() {
        UpdateManager.register(this);
    }

    private void checkForUpdatesCustom() {
        UpdateManager.register(this, Util.getAppIdentifier(this), new UpdateManagerListener() {
            @Override
            public void onNoUpdateAvailable() {
                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.no_updates_title)
                        .setMessage(R.string.no_updates_message)
                        .setPositiveButton(R.string.ok, null)
                        .create();
                alertDialog.show();
            }

            @Override
            public void onUpdateAvailable(JSONArray data, String url) {
                super.onUpdateAvailable(data, url);

                AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                        .setTitle(R.string.updates_title)
                        .setMessage(R.string.updates_message)
                        .setPositiveButton(R.string.ok, null)
                        .create();
                alertDialog.show();
            }
        });
    }

    private void doLoginCheck(int loginMode, final Intent onSuccessIntent) {
        LoginManager.register(this, Util.getAppIdentifier(this), Util.getManifestString(this, "net.hockeyapp.android.appSecret"), loginMode, new LoginManagerListener() {
            @Override
            public void onSuccess() {
                startActivity(onSuccessIntent);
            }
        });

        LoginManager.verifyLogin(this, getIntent());
    }

}
