package mx.com.aries.activities;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;

import android.location.Location;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import mx.com.aries.R;
import mx.com.aries.utils.CameraUtil;
import mx.com.aries.utils.DialogsUtil;
import mx.com.aries.utils.ImageUtil;
import mx.com.aries.utils.PermissionUtil;
import mx.com.aries.views.CameraView;

public class SceneActivity extends AppCompatActivity {
    private static final String TAG = "SceneActivity";

    private Camera mCamera;
    private CameraView mCameraView;
    private FrameLayout mPreview;
    private Button mSaveScene;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_scene);
        mPreview = (FrameLayout) findViewById(R.id.ac_scene_camera_preview1);
        mSaveScene = (Button) findViewById(R.id.ac_scene_save_scene1);
        setUpListeners();
        setUpBroadcastReceiver();
    }

    private void setUpBroadcastReceiver() {
        BroadcastReceiver broadcast = new BroadcastReceiver() {
            @Override
            public void onReceive(Context arg0, Intent intent) {
                String action = intent.getAction();
                if (action.equals("FINISH_ACTIVITY")) {
                    releaseCamera();
                    finish();
                }
            }
        };
        registerReceiver(broadcast, new IntentFilter("FINISH_ACTIVITY"));
    }


    private void setUpListeners() {
        mSaveScene.setOnClickListener(saveSceneListener);
    }

    private View.OnClickListener saveSceneListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (ActivityCompat.checkSelfPermission(view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                PermissionUtil.getPermission((Activity) view.getContext(), Manifest.permission.ACCESS_FINE_LOCATION);
            } else {
                startSaveSceneActivity();
            }
        }
    };

    private void startSaveSceneActivity() {
        Intent intent = new Intent(getBaseContext(), SaveSceneActivity.class);
        String img = getStringBitmapFromView();
        intent.putExtra("img", img);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_up, R.anim.no_change);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PermissionUtil.PERMISSION_UTIL_REQUEST: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    startSaveSceneActivity();
                } else {
                    DialogsUtil.showAlertDialog(this,
                            getString(R.string.main_activity_location_permission_denied_dialog_title),
                            getString(R.string.main_activity_location_permission_denied_dialog_description),
                            getString(R.string.main_activity_location_permission_denied_ok_button),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {

                                }
                            }, getString(R.string.main_activity_location_permission_denied_cancel_button),
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                                    Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                                    startActivity(myIntent);
                                }
                            }
                    );
                }
                return;
            }
        }
    }

    private String getStringBitmapFromView() {
        View view = mPreview.getRootView();
        Bitmap image = ImageUtil.getBitmapFromView(view);
        return ImageUtil.getStringImage(image);
    }

    private void setUpCamera() {
        mCamera = CameraUtil.getCameraInstance();
        if (mCamera != null) {
            mCameraView = new CameraView(this, mCamera);
            mPreview.addView(mCameraView);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseCamera();
    }

    private void releaseCamera() {
        if (mCamera != null) {
            try {
                mCamera.stopPreview();
                mCameraView.getHolder().removeCallback(mCameraView);
                mCamera.release();
                mCamera = null;
            } catch (Exception e) {
                Log.d(TAG, "releaseCamera: " + e.getMessage());
            }
        }
    }


}
