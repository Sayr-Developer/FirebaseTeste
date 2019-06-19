package br.com.firebaseteste;

//IMPORTS:

    import android.app.ProgressDialog;
    import android.support.annotation.NonNull;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.text.TextUtils;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;
    import android.widget.Toast;
    import com.google.android.gms.tasks.OnCompleteListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.AuthResult;
    import com.google.firebase.auth.FirebaseAuth;



    public class MainActivity extends AppCompatActivity implements View.OnClickListener{

        //ATRIBUTOS:

        private Button butaoRegistrar;
        private EditText editTextSenha, editTextEmail;
        private TextView textViewLogin;
        private ProgressDialog barradeprogresso;
        private FirebaseAuth autenticacaoFirebase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        autenticacaoFirebase = FirebaseAuth.getInstance();

        barradeprogresso = new ProgressDialog(this);

        butaoRegistrar = (Button) findViewById(R.id.butaoRegistrar);

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);

        editTextSenha = (EditText) findViewById(R.id.editTextSenha);

        textViewLogin = (TextView) findViewById(R.id.textViewLogin);

        butaoRegistrar.setOnClickListener(this);
        textViewLogin.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == butaoRegistrar){
            registrarUsuario();
        }
        if(v == textViewLogin)
        {
            //vai abrir a activity do login
        }

    }

    private void registrarUsuario() {

       String email = editTextEmail.getText().toString().trim();
       String senha = editTextSenha.getText().toString().trim();

       if(TextUtils.isEmpty(email)){
           //email está em branco
           Toast.makeText(this, "Digite seu email!!", Toast.LENGTH_SHORT).show();
           //Parando o toast
           return;
       }
        if(TextUtils.isEmpty(senha)){
            //senha está em branco
            Toast.makeText(this, "Digite sua senha!!", Toast.LENGTH_SHORT).show();
            //Parando o toast
            return;
        }
        barradeprogresso.setMessage("Registrando Usuário...");
        barradeprogresso.show();
        autenticacaoFirebase.createUserWithEmailAndPassword(email, senha)
        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if(task.isSuccessful()){
                   //usuário foi registrado com sucesso
                   //vamos iniciar a activity do perfil aqui
                   //então vamos mostrar uma mensagem na tela "Usuário registrado com sucesso"
                   Toast.makeText(MainActivity.this, "Usuário Registrado com Sucesso", Toast.LENGTH_SHORT).show();
                   barradeprogresso.dismiss();
               }
               else{
                   Toast.makeText(MainActivity.this, "Tente Efetuar o Cadastro Novamente", Toast.LENGTH_SHORT).show();
                   barradeprogresso.dismiss();
               }
            }
        });
    }
}
