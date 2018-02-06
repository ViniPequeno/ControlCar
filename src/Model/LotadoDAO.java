/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.ConnectionFactory;
import Controller.ServidorCTR;
import Controller.SetorCTR;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class LotadoDAO {

    private Connection connection;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;
    private String query;
    private ArrayList<ServidorDTO> servidores;

    public int adicionarLotado(long matFunc, long codSetor, String dataInicio) {

        this.query = "call inserirLotado(?, ?, ?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);

            this.cs.setLong(1, matFunc);
            this.cs.setLong(2, codSetor);
            this.cs.setString(3, dataInicio);
            this.cs.execute();
            this.connection.close();

            return 1;
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                return 2;
            }
            return 0;
        }
    }

    public int excluirLotado(long matFunc) {

        this.query = "call excluirLotado(?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);
            this.cs.setLong(1, matFunc);

            this.cs.execute();
            this.connection.close();

            return 1;
        } catch (SQLException ex) {

            return 0;
        }
    }

    public LotadoDTO procurarLotado(long mat) {

        this.query = "call procuraLotado(?, ?, ?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);
            this.cs.setLong(1, mat);
            this.cs.registerOutParameter(2, java.sql.Types.BIGINT);
            this.cs.registerOutParameter(3, java.sql.Types.VARCHAR);

            this.cs.executeQuery();

            if (cs.getString(3) == null) {
                return null;
            }
            return new LotadoDTO(new ServidorDAO().procurarServidor(mat), new SetorDAO().procurarSetor(cs.getLong(2)), cs.getString(3));
        } catch (SQLException ex) {

            return null;
        }
    }

    public void trocarLotado(long matFunc, long codSetor, String dataInicio) {
        this.query = "call trocarLotado(?, ?, ?)";
        this.connection = new ConnectionFactory().getConnection();
        try {
            ps = connection.prepareStatement(query);
            ps.setLong(1, matFunc);
            ps.setLong(3, codSetor);
            ps.setString(2, dataInicio);
            ps.executeUpdate();
            ps.close();
            connection.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            throw new RuntimeException(ex);
        }
    }

    public ArrayList<LotadoDTO> listar() {
        ArrayList<LotadoDTO> a = new ArrayList<>();
        this.query = "call listarLotado()";
        this.connection = new ConnectionFactory().getConnection();
        try {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            while (rs.next()) {
                ServidorDTO se = new ServidorCTR().procurarServidor(rs.getLong(1));
                SetorDTO s = new SetorCTR().procurarSetor(rs.getLong(2));
                LotadoDTO l = new LotadoDTO(se, s, rs.getString(3));
                a.add(l);
            }
            return a;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;

        }
    }

    public ArrayList<LotadoDTO> listar(String nome) {
        ArrayList<LotadoDTO> a = new ArrayList<>();
        this.query = "call listarLotadoNome(?)";
        this.connection = new ConnectionFactory().getConnection();
        try {
            ps = connection.prepareStatement(query);
            ps.setString(1, nome);
            rs = ps.executeQuery();
            while (rs.next()) {
                ServidorDTO se = new ServidorCTR().procurarServidor(rs.getLong(1));
                SetorDTO s = new SetorCTR().procurarSetor(rs.getLong(2));
                LotadoDTO l = new LotadoDTO(se, s, rs.getString(3));
                a.add(l);
            }
            return a;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;

        }
    }
}
