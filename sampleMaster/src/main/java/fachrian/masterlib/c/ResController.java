package fachrian.masterlib.c;


import android.content.Context;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fachrian.fachrian_library.lib.LogMaster;
import fachrian.masterlib.AppController;
import fachrian.masterlib.Setting;
import fachrian.masterlib.c.contract.ApplicationContract;
import fachrian.masterlib.m.SharedPreferences;
import fachrian.masterlib.v.profider.TugasProvider;

/**
 * Created by Fachrian on 24/06/2016.
 */

public class ResController {
    Context context;
    boolean processGetToken;
    boolean processGetAllTugas;


    public ResController(Context context) {
        this.context = context;
    }

    public void processLogin(final ApplicationContract.LoginView loginView, final String user, final String pass) {
        if (!processGetToken) {
            processGetToken = true;
            loginView.showProgress(true);
            final String URL = Setting.URL_Login;

            VolleyLog.DEBUG = VolleyController.VOLLEY_DEBBUG;
            final Request.Priority priority = Request.Priority.HIGH;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            processGetToken = false;
                            loginView.showProgress(false);
                            LogMaster.display(context, "request : " + URL);
                            LogMaster.display(context, "response : " + response);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                switch (jsonObject.get("MESSAGE").toString()) {
                                    case "Login sukses":
                                        if (jsonObject.get("ROLE").toString().equals("3")) {
                                            SharedPreferences.setTOKEN(context, jsonObject.get("TOKEN").toString());
                                            SharedPreferences.setID_USER(context, jsonObject.get("ID_USER").toString());
                                            SharedPreferences.setFOTO(context, jsonObject.get("FOTO").toString());
                                            SharedPreferences.setTLP(context, jsonObject.get("TLP").toString());
                                            SharedPreferences.setNAME(context, jsonObject.get("NAMA").toString());
                                            SharedPreferences.setACCESS(context, "TRUE");

                                            loginView.onResponLoginProcess(1);
                                        } else {
                                            loginView.onResponLoginProcess(2);

                                        }
                                        break;
                                    case "Password salah":
                                        loginView.onResponLoginProcess(4);
                                        break;
                                    case "Email tidak valid":
                                        loginView.onResponLoginProcess(3);
                                        break;
                                    case "Email tidak terdaftar":
                                        loginView.onResponLoginProcess(5);
                                        break;
                                    case "Input tidak lengkap":

                                        break;
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            LogMaster.display(context, "onErrorResponse " + error);


                            processGetToken = false;
                            loginView.showProgress(false);

                            VolleyController.volleyErrorHandler(context, error);

                        }
                    }) {

                @Override
                public Request.Priority getPriority() {
                    return priority;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("EMAIL", user);
                    params.put("PASS", pass);
                    params.put("TOKEN_GCM", SharedPreferences.getPUSH_NOTIFICATION_KEY(context));

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new
                    DefaultRetryPolicy(VolleyController.VOLLEY_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            );

            AppController.getInstance().addToRequestQueue(stringRequest, URL);

        }
    }


    public void processGetAllTugas(final ApplicationContract.TugasView tugasView) {
        if (!processGetAllTugas) {
            tugasView.showProgress(true);
            processGetAllTugas = true;
            final String URL = Setting.URL_GetAllTugas;

            final String TOKEN = SharedPreferences.getTOKEN(context);
            final String ID_USER = SharedPreferences.getID_USER(context);
            final String IN = "0";

            VolleyLog.DEBUG = VolleyController.VOLLEY_DEBBUG;
            final Request.Priority priority = Request.Priority.HIGH;

            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            processGetAllTugas = false;
                            tugasView.showProgress(false);
                            LogMaster.display(context, "request : " + URL);
                            LogMaster.display(context, "response : " + response);

                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                switch (jsonObject.get("MESSAGE").toString()) {
                                    case "Login sukses":

                                        break;
                                    case "Password salah":

                                        break;
                                    case "Registrasi berhasil":

                                        break;
                                    case "Email tidak valid":

                                        break;
                                    case "Data kosong":
                                        Toast.makeText(context, "Tidak ada data tugas", Toast.LENGTH_LONG);
                                        break;
                                }
                                List<TugasProvider> list = new ArrayList<>();
                                JSONArray jsonArray = jsonObject.getJSONArray("DATA");
                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject jsonObjectData = (JSONObject) jsonArray.get(i);
                                    list.add(new TugasProvider(
                                            jsonObjectData.getString("ID_TUGAS"),
                                            jsonObjectData.getString("NM_TUGAS"),
                                            jsonObjectData.getString("ALAMAT_TUGAS"),
                                            jsonObjectData.getString("UPLOADED_AT"),
                                            jsonObjectData.getString("DERAJAT_X"),
                                            jsonObjectData.getString("DERAJAT_Y"),
                                            jsonObjectData.getString("NAMA"),
                                            jsonObjectData.getString("NO_HP"),
                                            jsonObjectData.getString("KETERANGAN"),
                                            jsonObjectData.getString("STATUS")
                                    ));

                                }

                                tugasView.onResponGetTugasProcess(list);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            LogMaster.display(context, "onErrorResponse " + error);

                            processGetAllTugas = false;
                            tugasView.showProgress(false);

                            VolleyController.volleyErrorHandler(context, error);
                        }
                    }) {

                @Override
                public Request.Priority getPriority() {
                    return priority;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("TOKEN", TOKEN);
                    params.put("ID_USER", ID_USER);
                    params.put("IN", IN);
                    if (SharedPreferences.getLATI(context) == null || SharedPreferences.getLONGI(context) == null) {
                        params.put("LAT", "0");
                        params.put("LONG", "0");
                    } else {
                        params.put("LAT", SharedPreferences.getLATI(context));
                        params.put("LONG", SharedPreferences.getLONGI(context));
                    }

                    return params;
                }
            };

            stringRequest.setRetryPolicy(new
                    DefaultRetryPolicy(VolleyController.VOLLEY_TIMEOUT,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT)
            );

            AppController.getInstance().addToRequestQueue(stringRequest, URL);

        }
    }


}
