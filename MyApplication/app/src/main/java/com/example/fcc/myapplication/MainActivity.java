package com.example.fcc.myapplication;

import android.os.Bundle;
import android.support.test.espresso.core.deps.guava.net.MediaType;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Calendar;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView eCustomerName;
    private ListView listView;
    private String cName, key, rnd;

    private ImageView searchBtn;
    private Button insert;

    ArrayList<LoginData> ld;
    private RecyclerAdapter adapter;

    RecyclerView mRecyclerView;

    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        eCustomerName = (TextView) findViewById(R.id.etCname);
        searchBtn = (ImageView) findViewById(R.id.btnSearch);
        insert = (Button) findViewById(R.id.insertBtn);
        insert.setOnClickListener(this);
        searchBtn.setOnClickListener(this);

        LinearLayoutManager mLinearLayoutManager;

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mLinearLayoutManager = new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);

        ld = new ArrayList<>();

        adapter = new RecyclerAdapter(ld, this);

        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_user) {
            Toast.makeText(this, "USER SELECTED", Toast.LENGTH_SHORT).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void tr() {
        String operation, key, rnd, goal;

        Calendar c = Calendar.getInstance();
        int seconds = c.get(Calendar.SECOND);

        key = md5("9876543210" + seconds);
        rnd = seconds + "";

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("aplication/x-www-form-urlencoded; charset=utf-8");

        FormBody.Builder bodyBuilder = new FormBody.Builder()
                .add("Operation", "search")
                .add("Key", key)
                .add("Rnd", rnd)
                .add("Goal", cName);

        RequestBody body = bodyBuilder.build();

        Request request = new Request.Builder()
                .url("http://service.bazh.ir/api/customer").post(body).build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // Log.d("response", response.body().string());

                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());

                    ld = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject resultString = (JSONObject) jsonArray.get(i);
                        LoginData object = new LoginData(resultString.getString("name"), resultString.getString("des"), resultString.getString("id"));
                        ld.add(object);
                    }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.mDatas = ld;
                            adapter.notifyDataSetChanged();


                        }
                    });

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @Override
    public void onClick(View v) {
        cName = eCustomerName.getText().toString();

        tr();

    }
}
