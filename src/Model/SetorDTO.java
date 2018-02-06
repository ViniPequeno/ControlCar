/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.beans.property.SimpleLongProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class SetorDTO {

    private final SimpleLongProperty codigo;
    private final SimpleStringProperty nome;
    private final SimpleStringProperty ramal;
    private final SimpleStringProperty ativo;

    public SetorDTO(long codigo, String nome, String ramal, String ativo) {

        this.codigo = new SimpleLongProperty(codigo);
        this.nome = new SimpleStringProperty(nome);
        this.ramal = new SimpleStringProperty(ramal);
        this.ativo = new SimpleStringProperty(ativo);
    }

    public String getAtivo() {
        return ativo.get();
    }

    public long getCodigo() {

        return this.codigo.get();
    }

    public String getNome() {

        return this.nome.get();
    }

    public String getRamal() {

        return this.ramal.get();
    }
}
