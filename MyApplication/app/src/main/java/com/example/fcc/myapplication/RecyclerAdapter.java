package com.example.fcc.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by FCC on 11/12/2016.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.LoginDataHolder> {

    public ArrayList<LoginData> mDatas;
    private Context context;

    int position;

    public RecyclerAdapter(ArrayList<LoginData> logins, Context context) {
        mDatas = logins;
        this.context = context;
    }

    @Override
    public LoginDataHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View inflatedView = LayoutInflater.from(context)
                .inflate(R.layout.recyclerview_item_row, parent, false);

        return new LoginDataHolder(inflatedView);
    }

    @Override
    public void onBindViewHolder(LoginDataHolder holder, int position) {
        LoginData loginData = mDatas.get(position);
        holder.bindLoginData(loginData);

        this.position = position;
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    class LoginDataHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private static final String LOGINNAME = "Name";
        private static final String LOGINDESCRIPTION = "Description";
        private final static String CUSTOMERID = "CUSTOMERID";

        private TextView mLoginName;
        private TextView mLoginDescription;

        public LoginDataHolder(View itemView) {
            super(itemView);

            mLoginName = (TextView) itemView.findViewById(R.id.login_name);
            mLoginDescription = (TextView) itemView.findViewById(R.id.logindescription);

            itemView.setOnClickListener(this);
        }

        public void bindLoginData(LoginData loginData) {
            mLoginName.setText(loginData.getLoginName());
            mLoginDescription.setText(loginData.getLoginDescription());
        }


        @Override
        public void onClick(View v) {
            Log.d("RECYCLER", "CLick found");

            Context context = itemView.getContext();
            Intent iNext = new Intent(context, ActivityLogin.class);
            iNext.putExtra(LOGINNAME, mLoginName.getText().toString());
            iNext.putExtra(CUSTOMERID, mDatas.get(position).getCustomerId());

            context.startActivity(iNext);
        }
    }
}
