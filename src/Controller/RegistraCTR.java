/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.FuncionarioDTO;
import Model.RegistraDAO;
import Model.RegistraDTO;
import Model.VeiculoDTO;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class RegistraCTR {

    private RegistraDTO registra;

    public int inserirRegistroEntrada(FuncionarioDTO matProp, FuncionarioDTO matricula, VeiculoDTO placa, String data, String hora) {

        registra = new RegistraDTO(new Long(0), matProp, matricula, null, placa, data, hora, null, null, null, null);
        try {
            return new RegistraDAO().inserirEntrada(registra);
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                return 2;
            }
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    public int inserirRegistroSaida(FuncionarioDTO matricula, VeiculoDTO placa, String data, String hora) {

        registra = new RegistraDTO(new Long(0), null, null, matricula, placa, null, null, data, hora, null, null);
        try {
            return new RegistraDAO().inserirSaida(registra);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    public int enviarObservacao(long num, String obs) {

        registra = new RegistraDTO(num, null, null, null, null, null, null, null, null, obs, "S");
        try {
            return new RegistraDAO().enviarObservacao(registra);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    public void excluirObservacao(long num) {
        registra = new RegistraDTO(num, null, null, null, null, null, null, null, null, null, "N");
        try {
            new RegistraDAO().excluirObservacao(registra);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public ArrayList<VeiculoDTO> listarVeiculosFora() {
        try {
            return new RegistraDAO().listarVeiculosFora();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public ArrayList<VeiculoDTO> listarVeiculosDentro() {
        try {
            return new RegistraDAO().listarVeiculosDentro();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<RegistraDTO> listarObs() {
        try {
            return new RegistraDAO().listarObs();
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<RegistraDTO> listarObs(String placa) {
        try {
            return new RegistraDAO().listarObs(placa);
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public RegistraDTO getNumEntrada(String placa, String data, String hora) {
        try {
            return new RegistraDAO().procurarRegistraInicio(placa, data, hora);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public RegistraDTO getNumSaida(String placa) {
        try {
            return new RegistraDAO().procurarRegistraSaida(placa);
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public RegistraDTO procurar(String placa) {
        return new RegistraDAO().procurarRegistra(new VeiculoDTO(placa, "", "", "", ""));
    }

    public RegistraDTO procurar(Long numero) {
        return new RegistraDAO().procurarNumero(new RegistraDTO(numero, null, null, null, null,
                null, null, null, null, null, null));
    }

    private String getDataAtual() {

        Date d = new Date();
        return java.text.DateFormat.getDateInstance(DateFormat.MEDIUM).format(d);
    }

    private String getHoraAtual() {

        Calendar tempo = Calendar.getInstance();
        SimpleDateFormat t = new SimpleDateFormat("hh:mm");

        return t.format(tempo.get(Calendar.HOUR) + ":" + tempo.get(Calendar.MINUTE));
    }

    public ArrayList<RegistraDTO> listarEntradas() {
        try {
            return new RegistraDAO().listarEntradas();
        } catch (SQLException ex) {
            return null;
        }
    }
}
