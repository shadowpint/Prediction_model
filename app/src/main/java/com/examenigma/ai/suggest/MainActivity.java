package com.examenigma.ai.suggest;

import android.app.ProgressDialog;

import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.examenigma.ai.suggest.parser.JSONParser;
import com.examenigma.ai.suggest.utils.InternetConnection;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity {

    private static Response response;

    static final String API_KEY = "ILG11REiGgK1BqCsg9iNJmzqtJLE1G4w4dycY4ja/qCPSTACGCoQFK24VvAkw6Q28b6AcnWHbIHvKEteyoqTMw==";
    static final String MAIN_URL = "https://ussouthcentral.services.azureml.net/workspaces/618cc76f645348c1a28383bb02b531d1/services/649e16e4768d45ab824db060aaa7d03e/execute?api-version=2.0&details=true";


    Button predict;
    EditText name;
    EditText email;
    RadioGroup gender;
    private RadioButton genderButton;
    RadioGroup married;
    private RadioButton marriedButton;
    RadioGroup education;
    private RadioButton educationButton;
    RadioGroup employed;
    private RadioButton employedButton;
    EditText income;
    EditText coincome;
    EditText loanamount;
    EditText loanterm;
    EditText credit;
    private RadioButton creditButton;
    RadioGroup property;
    private RadioButton propertyButton;
    ProgressBar progressBar;
TextView respon;

    private String TAG = MainActivity.class.getSimpleName();

    private ProgressDialog pDialog;
    private String responsview;
    private String ressponsview;
    private String jsonString;
    private String resp;
    private String smarried;
    private String sloanamount;
    public String sincome;
    private String sgender;
    private String seducation;
    private String semployed;
    private String scoincome;
    private String sloanterm;
    private String scredit;
    private String sproperty;
    JSONObject json = new JSONObject();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        name = (EditText) findViewById(R.id.name);

        email = (EditText) findViewById(R.id.email);
        gender = (RadioGroup) findViewById(R.id.gender);
        genderButton = (RadioButton) findViewById(gender.getCheckedRadioButtonId());

        married = (RadioGroup) findViewById(R.id.married);
        marriedButton = (RadioButton) findViewById(married.getCheckedRadioButtonId());

        education = (RadioGroup) findViewById(R.id.education);
        educationButton = (RadioButton) findViewById(education.getCheckedRadioButtonId());

        employed= (RadioGroup) findViewById(R.id.employed);
        employedButton = (RadioButton) findViewById(employed.getCheckedRadioButtonId());

        income = (EditText) findViewById(R.id.income);

        coincome = (EditText) findViewById(R.id.co_income);

        loanamount = (EditText) findViewById(R.id.loan_amount);

        loanterm = (EditText) findViewById(R.id.loan_amount_term);
sloanterm=loanterm.getText().toString();
        predict = (Button) findViewById(R.id.Predict);
        credit =  (EditText) findViewById(R.id.credit);


        property= (RadioGroup) findViewById(R.id.property);
        propertyButton = (RadioButton) findViewById(property.getCheckedRadioButtonId());

        respon = (TextView)findViewById(R.id.responseView);

               predict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(@NonNull View view) {
                sgender=genderButton.getText().toString();
                scoincome=coincome.getText().toString();
                smarried=marriedButton.getText().toString();
                seducation=educationButton.getText().toString();
                semployed=employedButton.getText().toString();
                sincome=income.getText().toString();
                sloanamount=loanamount.getText().toString();
                scredit =credit.getText().toString();


                sproperty=propertyButton.getText().toString();
                JSONObject Inputs = new JSONObject();
                JSONObject GlobalParameters = new JSONObject();


                JSONObject input1 = new JSONObject();

                JSONArray Columns = new JSONArray();
                Columns.put("Loan_ID");
                Columns.put("Gender");
                Columns.put("Married");
                Columns.put("Dependents");
                Columns.put("Education");
                Columns.put("Self_Employed");
                Columns.put("ApplicantIncome");
                Columns.put("CoapplicantIncome");
                Columns.put("LoanAmount");
                Columns.put("Loan_Amount_Term");
                Columns.put("Credit_History");
                Columns.put("Property_Area");
                Columns.put("Loan_Status");


                JSONArray values1 = new JSONArray();
                JSONArray values = new JSONArray();

                values.put("12345");
                values.put(sgender);
                values.put(smarried);
                values.put("0");
                values.put(seducation);
                values.put(semployed);
                values.put(sincome);
                values.put(scoincome);
                values.put(sloanamount);
                values.put(sloanterm);
                values.put(scredit);
                values.put(sproperty);
                values.put("");

                values1.put(values);
                try {
                input1.put("ColumnNames", Columns);
                input1.put("Values", values1);
                Inputs.put("input1",input1);

                    json.put("Inputs", Inputs);
                    json.put("GlobalParameters", GlobalParameters);
                }


                catch (JSONException e) {
                    e.printStackTrace();
                }
                    new GetDataTask().execute();



            }
        });



    }

    class GetDataTask extends AsyncTask<Void, Void, String> {

        ProgressDialog dialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            /**
             * Progress Dialog for User Interaction
             */
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setTitle("Hey Wait Please...");
            dialog.setMessage("I am getting your JSON");
            dialog.show();
        }

        @Nullable
        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient client = new OkHttpClient();
                MediaType JSON
                        = MediaType.parse("application/json; charset=utf-8");


                RequestBody body = RequestBody.create(JSON, json.toString());
                Request request = new Request.Builder()
                        .header("Authorization", "bearer "+API_KEY )
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .url(MAIN_URL)
                        .post(body)
                        .build();
                response = client.newCall(request).execute();

                return response.body().string();
            } catch (@NonNull IOException e) {
                Log.e(TAG, "" + e.getLocalizedMessage());
            }


            return null;
        }

        @Override
        protected void onPostExecute(String Response) {
            super.onPostExecute(Response);
            dialog.dismiss();
            JSONObject json = null;
            try {
                json = new JSONObject(Response);
            } catch (JSONException e) {
                e.printStackTrace();
            }


            try {
                respon.setText(json.getJSONObject("Results").getJSONObject("output1").getJSONObject("value").getJSONArray("Values").getJSONArray(0).optString(6));
            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
}