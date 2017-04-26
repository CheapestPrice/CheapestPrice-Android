package edu.eci.cosw.cheapestPrice;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;


public class RegisterActivity extends AppCompatActivity {

    LinearLayout basic;
    LinearLayout tendero;

    //Formulario básico
    EditText userName;
    EditText email;
    EditText passwd;
    EditText confirmPasswd;

    //Tendero:
    boolean isTendero;
    EditText shopName;
    EditText shopAddress;
    EditText shopPhone;
    EditText shopNit;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        userName= (EditText) findViewById(R.id.userName);
        email= (EditText) findViewById(R.id.userMail);
        passwd= (EditText) findViewById(R.id.passwd);
        confirmPasswd= (EditText) findViewById(R.id.passwdConfirm);
        basic= (LinearLayout) findViewById(R.id.basicForm);
        tendero= (LinearLayout) findViewById(R.id.extraForm);
        isTendero=tendero.getVisibility()==View.VISIBLE;
        shopAddress= (EditText) findViewById(R.id.shopAddress);
        shopName= (EditText) findViewById(R.id.shopName);
        shopPhone= (EditText) findViewById(R.id.shopPhone);
        shopNit= (EditText) findViewById(R.id.shopNit);
    }

    public void reg(View v) {
        if(userName.getText()==null || userName.getText().length()==0) {
            userName.setError("Falta el nombre de usuario");
            System.out.println("Falta el nombre de usuario");
        }
        if(email.getText()==null || email.getText().length()==0){
            email.setError("Falta el correo electrónico");
            System.out.println("Falta el correo electrónico");
        }
        if(!isEmailValid(email.getText().toString())){
            email.setError("Formato incorrecto del correo electrónico");
            System.out.println("Formato incorrecto del correo electrónico");
        }
        if(passwd.getText()==null || passwd.getText().length()==0) {
            passwd.setError("Falta la contraseña");
            System.out.println("Falta la contraseña");
        }
        if(confirmPasswd.getText()==null || confirmPasswd.getText().length()==0){
            confirmPasswd.setError("Falta la confirmación de contraseña");
            System.out.println("Falta la confirmación de contraseña");
        }
        if( !passwd.getText().toString().equals(confirmPasswd.getText().toString())){
            confirmPasswd.setError("Las contraseñas no coinciden");
            System.out.println("las contraseñas no son iguales");
        }
        if(isTendero){
            if(shopAddress.getText()==null || shopAddress.getText().length()==0){
                shopAddress.setError("La dirección de la tienda no puede estar vacía");
                System.out.println("La dirección de la tienda no puede estar vacía");
            }
        }

    }
    private boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
    public void tend(View view){
        isTendero=!isTendero;
        tendero.setVisibility(isTendero ? View.VISIBLE:View.GONE);
    }

}
