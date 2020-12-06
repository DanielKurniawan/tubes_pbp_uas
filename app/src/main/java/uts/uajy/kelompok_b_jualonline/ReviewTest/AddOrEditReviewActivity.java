package uts.uajy.kelompok_b_jualonline.ReviewTest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import uts.uajy.kelompok_b_jualonline.R;
import uts.uajy.kelompok_b_jualonline.api.ReviewAPI;
import www.sanju.motiontoast.MotionToast;

import static com.android.volley.Request.Method.POST;

public class AddOrEditReviewActivity extends AppCompatActivity implements ReviewView {

    Bundle b;
    String id_barang, id_user_from_sp, status, id_review, sReview, status_from_sp;

    TextInputEditText review;
    MaterialButton btnSubmitReview;

    private ReviewPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_or_edit_review);

        presenter = new ReviewPresenter(AddOrEditReviewActivity.this, new ReviewService());

        loadUserId();

        review = findViewById(R.id.txtReview);
        btnSubmitReview = findViewById(R.id.btnSubmitReview);

        b = getIntent().getBundleExtra("id");

        status_from_sp="";

        status = b.getString("status");

        if(status.equalsIgnoreCase("edit")) {
            id_review = b.getString("id_review");
            sReview = b.getString("review");
            review.setText(sReview);
        }
        else if(status.equalsIgnoreCase("add")) {
            id_barang = b.getString("id_barang");
        }

        btnSubmitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.onAddReviewClicked();
                System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> ON BACKPRESSED");
                loadAddedStatus();
                if (status_from_sp.equalsIgnoreCase("success")) {
                    onBackPressed();
                }
//                if(review.getText().toString().isEmpty()) {
//                    Toast.makeText(AddOrEditReviewActivity.this, "Review tidak boleh kosong", Toast.LENGTH_SHORT).show();
//                    review.setError("Review tidak boleh kosong");
//                }
//                else {
//                    if(status.equalsIgnoreCase("add"))
//                    {
//                        addReview(id_user_from_sp, id_barang, review.getText().toString());
//                        onBackPressed();
//                    }
//                    else {
//                        editReview(Integer.parseInt(id_review), review.getText().toString());
//                        onBackPressed();
//                    }
//                }

            }
        });
    }

    public void loadUserId(){
        SharedPreferences sharedPreferences = getSharedPreferences("id_user", Context.MODE_PRIVATE);
        id_user_from_sp = sharedPreferences.getString("id_user","");
    }

    public void loadAddedStatus(){
        SharedPreferences sharedPreferences = getSharedPreferences("status", Context.MODE_PRIVATE);
        status_from_sp = sharedPreferences.getString("review_add","");
    }

    //untuk testing add review
//    public void addReview(String id_user, String id_barang_parameter, String review){
//        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
//
//        //Memulai membuat permintaan request menghapus data ke jaringan
//        StringRequest stringRequest = new StringRequest(POST, ReviewAPI.URL_CREATE, new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//
//                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error
//                try {
//                    JSONObject obj = new JSONObject(response);
//                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
////                    if(obj.getString("message").equals("Add Transaksi Success"))
////                    {
////
////                    }
//                    MotionToast.Companion.createColorToast(AddOrEditReviewActivity.this,"Review Added","You've successfully published your product review",
//                            MotionToast.TOAST_SUCCESS,
//                            MotionToast.GRAVITY_BOTTOM,
//                            MotionToast.LONG_DURATION,
//                            ResourcesCompat.getFont(getApplicationContext(),R.font.helvetica_regular));
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//                //Disini bagian jika response jaringan terdapat ganguan/error
//                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        }){
//            @Override
//            protected Map<String, String> getParams()
//            {
//                /*
//                    Disini adalah proses memasukan/mengirimkan parameter key dengan data value,
//                    dan nama key nya harus sesuai dengan parameter key yang diminta oleh jaringan
//                    API.
//                */
//                Map<String, String>  params = new HashMap<String, String>();
//                params.put("id_user", id_user);
//                params.put("id_barang", id_barang_parameter);
//                params.put("review", review);
//                return params;
//            }
//        };
//        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
//        queue.add(stringRequest);
//    }

    public void editReview(int id_review, String review){
        //Pendeklarasian queue
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());


        //Memulai membuat permintaan request menghapus data ke jaringan
        StringRequest  stringRequest = new StringRequest(POST, ReviewAPI.URL_UPDATE + id_review, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Disini bagian jika response jaringan berhasil tidak terdapat ganguan/error

                try {
                    //Mengubah response string menjadi object
                    JSONObject obj = new JSONObject(response);

                    //obj.getString("message") digunakan untuk mengambil pesan message dari response
//                    Toast.makeText(getApplicationContext(), "Review : "+review, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), ReviewAPI.URL_UPDATE + id_review, Toast.LENGTH_SHORT).show();
//                    Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
                    MotionToast.Companion.createColorToast(AddOrEditReviewActivity.this,"Review Edited !","You've successfully edited your previous product review",
                            MotionToast.TOAST_SUCCESS,
                            MotionToast.GRAVITY_BOTTOM,
                            MotionToast.LONG_DURATION,
                            ResourcesCompat.getFont(getApplicationContext(),R.font.helvetica_regular));
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Disini bagian jika response jaringan terdapat ganguan/error
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                /*
                    Disini adalah proses memasukan/mengirimkan parameter key dengan data value,
                    dan nama key nya harus sesuai dengan parameter key yang diminta oleh jaringan
                    API.
                */
                Map<String, String>  params = new HashMap<String, String>();
                params.put("review", review);
                return params;
            }
        };

        //Disini proses penambahan request yang sudah kita buat ke reuest queue yang sudah dideklarasi
        queue.add(stringRequest);
    }

    @Override
    public String getReview() {
        return review.getText().toString();
    }

    @Override
    public String getIdUser() {
        return id_user_from_sp;
    }

    @Override
    public String getIdBarang() {
        return id_barang;
    }

    @Override
    public void showReviewError(String message) {
        MotionToast.Companion.createColorToast(this,"Review Not Added",message,
                MotionToast.TOAST_WARNING,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(getContext(), R.font.helvetica_regular));
    }

    @Override
    public void startMainActivity() {
        new ActivityUtil(this).startMainActivity();
    }

    @Override
    public void showAddError(String message) {
        MotionToast.Companion.createColorToast(this,"Review Not Added","Check your network again",
                MotionToast.TOAST_ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(getContext(), R.font.helvetica_regular));
    }

    @Override
    public void showErrorResponse(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    @Override
    public Activity getEditActivity() {
        return this;
    }
}