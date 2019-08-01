package id.ac.umy.unires.sicurezza;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class PageSignIn extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_sign_in);
        getSupportActionBar().hide();

        EditText etUser = findViewById(R.id.etUsername);
        EditText etPass = findViewById(R.id.etPassword);
        final String Username = etUser.getText().toString();
        final String Password = etPass.getText().toString();

        Button btnsignin2 = findViewById(R.id.button2);
        btnsignin2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(PageSignIn.this, NavPage.class);
                startActivity(intent2);
                signingIn(Username, Password);
            }
        });
    }

    private void signingIn(String username, String password) {

    }
}
