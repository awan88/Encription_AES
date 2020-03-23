package awan.project.encriptaes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class MainActivity extends AppCompatActivity {

    Button encBtn, decBtn;
    EditText inputText, inputPassword, outputEncript;
    TextView  outputDecryp;

    String outputStringEncript;
    String AES = "AES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inputText = findViewById(R.id.inputText);
        inputPassword = findViewById(R.id.password);

        outputEncript = findViewById(R.id.outputText);
        outputDecryp = findViewById(R.id.outputDecript);
        
        encBtn = (Button)findViewById(R.id.encBtn);
        decBtn = (Button)findViewById(R.id.decBtn);


        encBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try{
                    outputStringEncript = encript(inputText.getText().toString(), inputPassword.getText().toString());
                    outputEncript.setText(outputStringEncript);
                }catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });

        decBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                try {
                    outputStringEncript = decrypt(outputStringEncript, inputPassword.getText().toString());
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Password Salah", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
                outputDecryp.setText(outputStringEncript);

            }
        });
    }

    private String decrypt(String username, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decodeValue = Base64.decode(username, Base64.DEFAULT);
        byte[] decVal = c.doFinal(decodeValue);
        String decriptedValue = new String(decVal);
        return decriptedValue;
    }

    private String encript(String username, String password) throws Exception{
        SecretKeySpec key = generateKey(password);
        Cipher c = Cipher.getInstance(AES);
        c.init(Cipher.ENCRYPT_MODE, key);
        byte[] encodeValue = c.doFinal(username.getBytes());
        String encryptedValue = Base64.encodeToString(encodeValue, Base64.DEFAULT);
        return encryptedValue;
    }

    private SecretKeySpec generateKey(String password) throws Exception{
        final MessageDigest digest = MessageDigest.getInstance("SHA-256");
        byte[] bytes = password.getBytes("UTF-8");
        digest.update(bytes, 0, bytes.length);
        byte[] key = digest.digest();
        SecretKeySpec secretKeySpec = new SecretKeySpec(key, "AES");
        return secretKeySpec;
    }
}
