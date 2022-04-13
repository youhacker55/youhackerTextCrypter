package com.privacy.encryption;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


import org.w3c.dom.Text;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Random;


public class MainActivity extends AppCompatActivity {
    TextView text;
    EditText inputText;
    EditText Key;
    private Button encrypt;
    private Button genkey;
    private  Button CopyCont;
    private Button Decrypt;


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        inputText = (EditText) findViewById(R.id.ttocrypt);
        Key = (EditText) findViewById(R.id.editTextTextPersonName2);
        encrypt = (Button) findViewById(R.id.Crypt);
        encrypt.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                text = (TextView) findViewById(R.id.youhacker);
                String Txt = inputText.getText().toString();
                String mykey = Key.getText().toString();
                byte[] data = Txt.getBytes();
                String base64 = Base64. encodeToString(data, Base64.DEFAULT);
                text.setText(toHex(XOREncryption.encryptDecrypt(base64,mykey.toCharArray())));

            }

        });
        genkey = (Button) findViewById(R.id.key);
        genkey.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                Key.setText(randomkey());

            }
        });
        CopyCont = (Button) findViewById(R.id.copy);
        CopyCont.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                try{
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    ClipData clip = ClipData.newPlainText("label", text.getText());
                    clipboard.setPrimaryClip(clip);

                }
                catch (Exception e){
                    e.getMessage();
                }






            }

        });
        Decrypt = (Button) findViewById(R.id.Decrypt);
        Decrypt.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                try {
                    text = (TextView) findViewById(R.id.youhacker);
                    String Txt = inputText.getText().toString();
                    String mykey = Key.getText().toString();
                    String t = unHex(Txt);
                    byte[] data = Base64.decode(XOREncryption.encryptDecrypt(t,mykey.toCharArray()),Base64.DEFAULT);
                    String clear = null;

                    clear = new String(data, "UTF-8");
                    text.setText(clear);
                }


                catch (Exception e) {

                    e.getMessage();

                }









            }

        });





    }
    public static String randomkey() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }
    public String toHex(String arg) {
        return String.format("%x", new BigInteger(1, arg.getBytes(StandardCharsets.UTF_8)));

    }
    public static String unHex(String arg) {

        String str = "";
        for(int i=0;i<arg.length();i+=2)
        {
            String s = arg.substring(i, (i + 2));
            int decimal = Integer.parseInt(s, 16);
            str = str + (char) decimal;
        }
        return str;
    }
    


}

class XOREncryption {

    public static String encryptDecrypt(String input, char[] enckey) {
        char[] key = enckey;
        StringBuilder output = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            output.append((char) (input.charAt(i) ^ key[i % key.length]));
        }

        return output.toString();
    }
}





