package mx.com.aries.activities;

import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.google.android.gms.location.LocationRequest;

import mx.com.aries.BuildConfig;
import mx.com.aries.R;
import mx.com.aries.entities.Scene;
import mx.com.aries.entities.WebApiResult;
import mx.com.aries.listeners.OnLocationChangedListener;
import mx.com.aries.network.GenericRequest;
import mx.com.aries.network.VolleySingleton;
import mx.com.aries.utils.LocationUtil;

public class SaveSceneActivity extends AppCompatActivity implements OnLocationChangedListener {
    private static final String TAG = "SaveSceneActivity";

    private LocationUtil mLocationUtil;
    private Location mSceneLocation;

    private VolleySingleton mVolley;
    private Button mSaveScene;
    private EditText mSceneTitle;
    private EditText mSceneDescription;

    private TextView mResultMessage;

    private String mStringImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getIntent().getExtras();
        mStringImage = bundle.getString("img");

        setContentView(R.layout.activity_save_scene);
        mSceneTitle = (EditText) findViewById(R.id.ac_save_scene_title);
        mSceneDescription = (EditText) findViewById(R.id.ac_save_scene_description);
        mResultMessage = (TextView) findViewById(R.id.ac_save_scene_result_save);
        mSaveScene = (Button) findViewById(R.id.ac_save_scene_share_scene);
        mSaveScene.setOnClickListener(saveSceneOnClickListener);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mVolley = VolleySingleton.getInstance(this.getApplicationContext());
        setUpListeners();
    }

    private void setUpListeners() {
        mLocationUtil = new LocationUtil(this, this);
        mLocationUtil.buildGoogleApiClient(LocationRequest.PRIORITY_HIGH_ACCURACY, 5000, 2000);
        mLocationUtil.start();
    }

    @Override
    public void onLocationChanged(Location mLocation) {
        if ((mLocation.hasAccuracy())) {

            mSceneLocation = mLocation;
            mLocationUtil.stop();
        }
    }

    private View.OnClickListener saveSceneOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Scene scene = getScene();
            if (validateScene(scene)) {
                saveScene(scene);
            }
        }
    };

    private Scene getScene() {
        Scene scene = new Scene();
        scene.setName(mSceneTitle.getText().toString());
        scene.setDescription(mSceneDescription.getText().toString());
        scene.setIdUser(2);//todo Cambiar por la lógica adecuada
        if (mSceneLocation != null) {
            scene.setX(mSceneLocation.getLongitude());
            scene.setY(mSceneLocation.getLatitude());
        }
        scene.setImage(mStringImage);
        return scene;
    }

    private boolean validateScene(Scene scene) {
        boolean isValid = false;
        if (scene.getName().trim().length() == 0) {
            mSceneTitle.setError(getString(R.string.save_scene_activity_INVALID_TITLE));
            return isValid;
        }

        if (scene.getDescription().trim().length() == 0) {
            mSceneDescription.setError(getString(R.string.save_scene_activity_INVALID_DESCRIPTION));
            return isValid;
        }
        return true;
    }

    private void saveScene(Scene scene) {
        String url = BuildConfig.WEB_API_URL + "scenes";
        GenericRequest request = new GenericRequest<WebApiResult>(Request.Method.POST, url, WebApiResult.class, scene, new Response.Listener<WebApiResult>() {
            @Override
            public void onResponse(WebApiResult response) {
                int id = response.getData().get("id").getAsInt();
                if (response.getCode() == 0) {
                    mResultMessage.setText(getString(R.string.save_scene_activity_OK_SAVE) + ": id=" + id);
                } else {
                    mResultMessage.setText(getString(R.string.save_scene_activity_ERROR_SAVE));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }, false);
        mVolley.addToRequestQueue(request, TAG);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    @Override
    protected void onStop() {
        mLocationUtil.stop();
        super.onStop();
        RequestQueue mRequestQueue = mVolley.getRequestQueue();
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(TAG);
        }
    }
}
