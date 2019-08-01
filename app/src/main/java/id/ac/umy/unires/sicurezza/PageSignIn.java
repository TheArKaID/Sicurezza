package id.ac.umy.unires.sicurezza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class PageSignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_sign_in);
        getSupportActionBar().hide();

        //Button btnsignin = findViewById(R.id.btnsignin);
        //btnsignin.setOnClickListener(new View.OnClickListener() {
            //@Override
            //public void onClick(View v) {
                //Intent intent = new Intent(MainActivity.this, PageSignIn.class);
                //startActivity(intent);

        Button btnsignin2 = findViewById(R.id.button2);
        btnsignin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(PageSignIn.this, NavPage.class);
                startActivity(intent2);
            }
        });
    }
}
