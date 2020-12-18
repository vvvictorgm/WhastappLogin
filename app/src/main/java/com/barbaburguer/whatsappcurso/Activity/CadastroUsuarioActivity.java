package com.barbaburguer.whatsappcurso.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.barbaburguer.whatsappcurso.Config.ConfiguracaoFirebase;
import com.barbaburguer.whatsappcurso.Modelo.Usuario;
import com.barbaburguer.whatsappcurso.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Toast;

public class CadastroUsuarioActivity extends AppCompatActivity {

    private EditText nome,email,senha;
    private Button botãoCadastrar;
    private Usuario usuario;
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_usuario);

        nome = findViewById(R.id.edit_cadastrado_nome);
        email = findViewById(R.id.edit_cadastrado_email);
        senha = findViewById(R.id.edit_cadastrado_senha);
        botãoCadastrar = findViewById(R.id.bt_cadastrar);

        botãoCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario = new Usuario();
                usuario.setNome(nome.getText().toString());
                usuario.setEmail(email.getText().toString());
                usuario.setSenha(senha.getText().toString());
                cadastrarUsuario();

            }
        });
    }
    public void cadastrarUsuario(){
        auth = ConfiguracaoFirebase.getAutenticacao();
        auth.createUserWithEmailAndPassword(
                usuario.getEmail(),usuario.getSenha()
        ).addOnCompleteListener(CadastroUsuarioActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastroUsuarioActivity.this, "Sucesso ao cadsatrar usuário", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro ao cadastrar usuário", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
