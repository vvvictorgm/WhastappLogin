package com.barbaburguer.whatsappcurso.Modelo;
import com.barbaburguer.whatsappcurso.Config.ConfiguracaoFirebase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Usuario {
    private String id,nome,email,senha;

    public Usuario() {

    }
    public void salvar(){
        DatabaseReference reference = ConfiguracaoFirebase.getFirebase();
        reference.child("usuários").child(getId()).setValue(this);

    }
    @Exclude
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @Exclude
    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
