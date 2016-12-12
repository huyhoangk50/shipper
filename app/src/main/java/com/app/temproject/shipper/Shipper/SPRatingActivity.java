package com.app.temproject.shipper.Shipper;

import android.app.Dialog;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.Toast;

import com.app.temproject.shipper.R;

public class SPRatingActivity extends Activity {
        private Button btnCheck;
        private int ratingStarsNumber;
        private Button btn_SubmitRatingNumber;
        private Button btn_CancleRatingNumber;
        private RatingBar rb_RatingStarsNumber;
        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.sp_rating_bar);
            addListenerOnButtonClick();
        }

        public void addListenerOnButtonClick(){
            btnCheck=(Button)findViewById(R.id.btn_check);
            btnCheck.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View arg0) {
//                    //Getting the rating and displaying it on the toast
//                    String rating=String.valueOf(spRatingBar.getRating());
//                    Toast.makeText(getApplicationContext(), rating, Toast.LENGTH_LONG).show();

                    Showdialog();
                }

            });
        }

        public void Showdialog(){

//            AlertDialog.Builder popDialog = new AlertDialog.Builder(this);
//            final RatingBar rating = new RatingBar(this);
            Dialog popdialog = new Dialog(SPRatingActivity.this);
            popdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            popdialog.setContentView(R.layout.sp_rating_dialog);

            btn_SubmitRatingNumber = (Button) popdialog.findViewById(R.id.btnSubmitRating);
            btn_CancleRatingNumber = (Button) popdialog.findViewById(R.id.btnCancelRating);
            rb_RatingStarsNumber = (RatingBar) popdialog.findViewById(R.id.ratingBar);

            popdialog.show();

            btn_SubmitRatingNumber.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    ratingStarsNumber = (int) rb_RatingStarsNumber.getRating();
                    Toast.makeText(getApplicationContext(), String.valueOf(ratingStarsNumber), Toast.LENGTH_SHORT).show();
                }

            });

            btn_CancleRatingNumber.setOnClickListener(new OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    ratingStarsNumber = 0;
                    Toast.makeText(getApplicationContext(), String.valueOf(ratingStarsNumber), Toast.LENGTH_SHORT).show();
                }

            });
        }
//        @Override
//        public boolean onCreateOptionsMenu(Menu menu) {
//            // Inflate the menu; this adds items to the action bar if it is present.
//            getMenuInflater().inflate(R., menu);
//            return true;
//        }
//

}
