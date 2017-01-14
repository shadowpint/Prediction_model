package com.examenigma.ai.suggest.parser;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.squareup.okhttp.FormEncodingBuilder;
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


/**
 * @author Neeraj Giri
 */
public class JSONParser {

    /********
     * URLS
     *******/
    static final String API_KEY = "ILG11REiGgK1BqCsg9iNJmzqtJLE1G4w4dycY4ja/qCPSTACGCoQFK24VvAkw6Q28b6AcnWHbIHvKEteyoqTMw==";
    static final String MAIN_URL = "https://ussouthcentral.services.azureml.net/workspaces/618cc76f645348c1a28383bb02b531d1/services/649e16e4768d45ab824db060aaa7d03e/execute?api-version=2.0&details=true";

    /**
     * TAGs Defined Here...
     */
    public static final String TAG = "TAG";


    private static Response response;


    public static JSONObject getDataFromWeb(String[] passed) {
        try {


            OkHttpClient client = new OkHttpClient();
            MediaType JSON
                    = MediaType.parse("application/json; charset=utf-8");

            JSONObject Inputs = new JSONObject();
            JSONObject GlobalParameters = new JSONObject();

            JSONObject json = new JSONObject();
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
            values.put(passed[0]);
            values.put(passed[1]);
            values.put(passed[2]);
            values.put(passed[3]);
            values.put(passed[4]);
            values.put(passed[5]);
            values.put(passed[6]);
            values.put(passed[7]);
            values.put(passed[8]);
            values.put(passed[9]);
            values.put(passed[10]);
            values.put(passed[11]);
            values.put(passed[12]);


            values1.put(values);
            input1.put("ColumnNames", Columns);
            input1.put("Values", values1);
            Inputs.put("input1",input1);
            json.put("Inputs", Inputs);
            json.put("GlobalParameters", GlobalParameters);
            RequestBody body = RequestBody.create(JSON, json.toString());
            Request request = new Request.Builder()
                    .header("Authorization", "bearer "+API_KEY )
                    .addHeader("Accept", "application/json")
                    .addHeader("Content-Type", "application/json")
                    .url(MAIN_URL)
                    .post(body)
                    .build();
            response = client.newCall(request).execute();
            return new JSONObject(response.body().string());
        } catch (@NonNull IOException | JSONException e) {
            Log.e(TAG, "" + e.getLocalizedMessage());
        }
        return null;
    }


}
