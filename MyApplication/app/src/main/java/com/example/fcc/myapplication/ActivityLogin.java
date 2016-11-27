package com.example.fcc.myapplication;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.test.espresso.core.deps.guava.net.MediaType;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.activeandroid.ActiveAndroid;
import com.activeandroid.Configuration;

import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by FCC on 11/12/2016.
 */
public class ActivityLogin extends Activity implements android.view.View.OnClickListener {

    private static final String LOGINNAME = "Name";
    private static final String LOGINDESCRIPTION = "Description";

    TextView usrComplexTv, forgotPassword, back;

    private AnimatorSet mSetRightOut, mSetRightOut2;
    private AnimatorSet mSetLeftIn, mSetLeftIn2;

    private boolean mIsBackVisible = false;
    private View mCardFrontLayout, bazh1;
    private View mCardBackLayout;
    private EditText etUser, etPassword;

    private ViewSwitcher viewSwitcher;
    private static final int SHOW_NEXT = 1;
    private static final int SHOW_PREVIOUS = 0;

    private static final String CUSTOMERID = "CUSTOMERID";

    private RelativeLayout btnEnter;

    private String customerId;

    private Person person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Configuration configuration = new Configuration.Builder(this)
                .setDatabaseName("PersonDB")
                .setModelClasses(PersonDB.class)
                .create();
        ActiveAndroid.initialize(configuration);

        Intent intent = getIntent();
        String loginName = intent.getStringExtra(LOGINNAME);
        customerId = intent.getStringExtra(CUSTOMERID);

        usrComplexTv = (TextView) findViewById(R.id.user_complex_name);
        forgotPassword = (TextView) findViewById(R.id.tv_forgot_password);
        back = (TextView) findViewById(R.id.btn_back);
        btnEnter = (RelativeLayout) findViewById(R.id.btnEnter);
        etUser = (EditText) findViewById(R.id.et_user_name);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnEnter.setOnClickListener(this);

        usrComplexTv.setText(loginName);

        forgotPassword.setOnClickListener(this);
        back.setOnClickListener(this);

        findViews();

        setAnimation();

    }

    private void setAnimation() {
        mSetRightOut = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip);
        mSetLeftIn = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip_back);
        mSetRightOut2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip);
        mSetLeftIn2 = (AnimatorSet) AnimatorInflater.loadAnimator(this, R.animator.flip_back);
    }

    private void findViews() {
        mCardBackLayout = findViewById(R.id.loginBox2);
        mCardFrontLayout = findViewById(R.id.loginBox);

        bazh1 = findViewById(R.id.bazh1);

        viewSwitcher = (ViewSwitcher) findViewById(R.id.viewSwitcher);
    }

    public void flipCard(View view) {
        if (!mIsBackVisible) {
            mSetLeftIn.setTarget(mCardFrontLayout);
            mSetLeftIn2.setTarget(bazh1);

            mSetLeftIn.start();
            mSetLeftIn2.start();

            showNext();

            mIsBackVisible = true;
        } else {
            mSetRightOut.setTarget(mCardBackLayout);
            mSetRightOut2.setTarget(bazh1);

            mSetRightOut.start();
            mSetRightOut2.start();

            showPrevious();

            mIsBackVisible = false;
        }
    }

    public void showNext() {

        new Thread() {

            public void run() {

                try {

                    Thread.sleep(1000);

                    Refresh.sendEmptyMessage(SHOW_NEXT);
                } catch (Exception e) {
                }
            }
        }.start();
    }

    public void showPrevious() {

        new Thread() {

            public void run() {

                try {
                    Thread.sleep(1000);
                    Refresh.sendEmptyMessage(SHOW_PREVIOUS);
                } catch (Exception e) {
                }
            }
        }.start();
    }

    // Refresh handler, necessary for updating the UI in a/the thread
    Handler Refresh = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {

                case SHOW_NEXT:
                    viewSwitcher.showNext();
                    break;

                case SHOW_PREVIOUS:
                    viewSwitcher.showPrevious();
                    break;
                default:
                    break;
            }
        }
    };

    private void login() {
        String operation, key, rnd, customerId, user, pass;

        Calendar c = Calendar.getInstance();
        long seconds = c.getTimeInMillis();

        MainActivity main = new MainActivity();
        key = main.md5("9876543210" + seconds);
        rnd = seconds + "";

        customerId = this.customerId;
        user = etUser.getText().toString();
        pass = etPassword.getText().toString();

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/x-www-form-urlencoded; charset=utf-8");

        FormBody.Builder bodyBuilder = new FormBody.Builder()
                .add("operation", "login")
                .add("key", "523121e33d0c6668738a12a0c4037dc9")
                .add("rnd", "1479041498")
                .add("customerId", customerId)
                .add("user", user)
                .add("pass", "984d8144fa08bfc637d2825463e184fa");

        RequestBody body = bodyBuilder.build();

        final Request request = new Request.Builder()
                .url("http://service.bazh.ir/api/person").post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                try {

                    JSONObject jo = new JSONObject(response.body().string());

                    person = new Person(jo.getString("personId"),
                            jo.getString("customerId"),
                            jo.getString("name"),
                            jo.getString("des"),
                            jo.getString("avatar"),
                            jo.getString("serverIp"),
                            jo.getString("mobilePort"),
                            jo.getString("exKey"));


                    // Inserting to DB...
                    PersonDB pm = new PersonDB();
                    pm.personId = person.getPersonId();
                    pm.customerId = person.getCustomerId();
                    pm.name = person.getName();
                    pm.des = person.getDes();
                    pm.avatar = person.getAvatar();
                    pm.serverIP = person.getServerIP();
                    pm.mobilePort = person.getMobilePort();
                    pm.exKey = person.getExKey();
                    pm.save();


                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    Intent i = new Intent(ActivityLogin.this, GestureActivity.class);
                    i.putExtra("SERVERIP", person.getServerIP());
                    i.putExtra("MOBILEPORT", person.getMobilePort());
                    i.putExtra("EXKEY", person.getExKey());
                    i.putExtra("CUSTOMERID", person.getCustomerId());
                    i.putExtra("PERSONID", person.getPersonId());

                    startActivity(i);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnEnter:
                login();
                break;
            case R.id.tv_forgot_password:
                flipCard(viewSwitcher);
                break;
        }


    }
}
