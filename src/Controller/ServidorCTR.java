/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.ServidorDAO;
import Model.ServidorDTO;
import java.util.ArrayList;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class ServidorCTR {

    public int cadastrarServidor(long matricula, String nome, String email,
            String telFixo, String telCelular) {

        ServidorDTO servidor = new ServidorDTO(matricula, nome, email, telFixo, telCelular, "");

        return new ServidorDAO().adicionarServidor(servidor);
    }

    public int excluirServidor(long matricula) {

        ServidorDTO servidor = new ServidorDTO(matricula, "", "", "", "", "");

        return new ServidorDAO().excluirServidor(servidor);
    }

    public int alterarServidor(long matricula, String nome, String email,
            String telFixo, String telCelular) {

        ServidorDTO servidor = new ServidorDTO(matricula, nome, email, telFixo, telCelular, "");

        return new ServidorDAO().alterarServidor(servidor);
    }

    public ServidorDTO procurarServidor(long matricula) {

        return new ServidorDAO().procurarServidor(matricula);
    }

    public ArrayList<ServidorDTO> listarServidor() {

        return new ServidorDAO().listarServidor();
    }

    public ArrayList<ServidorDTO> listarServidorNome(String nome) {
        return new ServidorDAO().listarServidorNome(nome);
    }

}
