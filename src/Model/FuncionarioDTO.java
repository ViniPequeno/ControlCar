/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Blob;
import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FuncionarioDTO {

    protected final SimpleLongProperty matricula;
    protected final SimpleStringProperty nome;
    protected final SimpleStringProperty cargo;
    protected final SimpleStringProperty senha;
    protected final SimpleStringProperty email;
    protected final SimpleStringProperty telefoneFixo;
    protected final SimpleStringProperty telefoneCelular;
    protected final SimpleStringProperty ativo;
    protected Blob imagem;

    public FuncionarioDTO(long matricula, String nome, String cargo, String senha,
            String email, String telefoneFixo,
            String telefoneCelular, String ativo) {
        this.matricula = new SimpleLongProperty(matricula);
        this.nome = new SimpleStringProperty(nome);
        this.cargo = new SimpleStringProperty(cargo);
        this.senha = new SimpleStringProperty(senha);
        this.email = new SimpleStringProperty(email);
        this.telefoneFixo = new SimpleStringProperty(telefoneFixo);
        this.telefoneCelular = new SimpleStringProperty(telefoneCelular);
        this.ativo = new SimpleStringProperty(ativo);

    }

    public FuncionarioDTO(long matricula, String nome, String cargo, String senha,
            String email, String telefoneFixo,
            String telefoneCelular, String ativo, Blob imagem) {
        this.matricula = new SimpleLongProperty(matricula);
        this.nome = new SimpleStringProperty(nome);
        this.cargo = new SimpleStringProperty(cargo);
        this.senha = new SimpleStringProperty(senha);
        this.email = new SimpleStringProperty(email);
        this.telefoneFixo = new SimpleStringProperty(telefoneFixo);
        this.telefoneCelular = new SimpleStringProperty(telefoneCelular);
        this.ativo = new SimpleStringProperty(ativo);
        this.imagem = imagem;
    }

    public Blob getImagem() {
        return imagem;
    }

    public String getAtivo() {
        return ativo.get();
    }

    public long getMatricula() {
        return this.matricula.get();
    }

    public String getNome() {
        return this.nome.get();
    }

    public String getCargo() {
        return this.cargo.get();
    }

    public String getSenha() {
        return this.senha.get();
    }

    public String getEmail() {
        return this.email.get();
    }

    public String getTelFixo() {
        return this.telefoneFixo.get();
    }

    public String getTelCelular() {
        return this.telefoneCelular.get();
    }
}
