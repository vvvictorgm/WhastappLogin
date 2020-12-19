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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

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

                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuario.setId(usuarioFirebase.getUid());
                    usuario.salvar();

                    auth.signOut();
                    finish();

                }else{
                    String erro = "";
                    try{
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erro = "Digite uma senha mais forte, contendo mais caracteres, letras e numeros";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erro = "E-mail digitado inválido, digite um novo e-mail";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erro = "E-mail já está foi cadastrado";
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Toast.makeText(CadastroUsuarioActivity.this, "Erro: "+ erro, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}
