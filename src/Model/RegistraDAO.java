/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.ConnectionFactory;
import Controller.FuncionarioCTR;
import Controller.VeiculoCTR;
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
public class RegistraDAO {

    private PreparedStatement ps;
    private ResultSet rs;
    private CallableStatement cs;
    private Connection connection;
    private String query;

    public RegistraDAO() {
        connection = new ConnectionFactory().getConnection();
    }

    public int inserirEntrada(RegistraDTO registra) throws SQLException {
        FuncionarioDTO prop = registra.getServidorDTO();
        FuncionarioDTO f = registra.getFuncionarioEntrada();
        VeiculoDTO v = registra.getVeiculo();

        query = " call inserirRegistroEntrada(?, ?, ?, ?, ?); ";
        cs = connection.prepareCall(query);
        cs.setLong(1, prop.getMatricula());
        cs.setLong(2, f.getMatricula());

        cs.setString(3, v.getPlaca());
        System.out.println("Hora === " + registra.getHoraEntrada());
        cs.setString(4, registra.getHoraEntrada());
        cs.setString(5, registra.getDataEntrada());

        cs.executeUpdate();

        cs.close();
        connection.close();

        return 1;
    }

    public int inserirSaida(RegistraDTO registra) throws SQLException {

        FuncionarioDTO f = registra.getFuncionarioSaida();
        VeiculoDTO v = registra.getVeiculo();

        query = " call inserirRegistroSaida(?, ?, ?, ?); ";
        cs = connection.prepareCall(query);

        cs.setString(1, v.getPlaca());
        cs.setLong(2, f.getMatricula());

        cs.setString(3, registra.getHoraSaida());
        cs.setString(4, registra.getDataSaida());

        cs.executeUpdate();

        cs.close();
        connection.close();

        return 1;
    }

    public int enviarObservacao(RegistraDTO registra) throws SQLException {

        query = " call registrarObservacao(?, ?); ";
        cs = connection.prepareCall(query);

        cs.setLong(1, registra.getNumero());
        cs.setString(2, registra.getObs());

        cs.executeUpdate();

        cs.close();
        connection.close();

        return 1;
    }

    public int excluirObservacao(RegistraDTO registra) throws SQLException {
        query = " call excluirObservacao(?); ";
        cs = connection.prepareCall(query);

        cs.setLong(1, registra.getNumero());

        cs.executeUpdate();

        cs.close();
        connection.close();

        return 1;
    }

    public ArrayList<VeiculoDTO> listarVeiculosFora() throws SQLException {
        ArrayList<VeiculoDTO> al = new ArrayList<>();
        query = " call listarVeiculosFora(); ";
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();
        while (rs.next()) {
            VeiculoDTO v = new VeiculoDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            al.add(v);
        }
        ps.close();
        connection.close();
        return al;
    }

    public ArrayList<VeiculoDTO> listarVeiculosDentro() throws SQLException {
        ArrayList<VeiculoDTO> al = new ArrayList<>();
        query = " call listarVeiculosDentro(); ";
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();
        while (rs.next()) {
            VeiculoDTO v = new VeiculoDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
            al.add(v);
        }

        ps.close();
        connection.close();
        return al;
    }

    public ArrayList<RegistraDTO> listarObs() throws SQLException {
        ArrayList<RegistraDTO> al = new ArrayList<>();
        query = " call listarObs(); ";
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();
        while (rs.next()) {
            FuncionarioDTO f1 = new FuncionarioCTR().procurarFunc(rs.getLong(2));
            FuncionarioDTO f2 = new FuncionarioCTR().procurarFunc(rs.getLong(3));
            FuncionarioDTO f3 = new FuncionarioCTR().procurarFunc(rs.getLong(4));
            VeiculoDTO v = new VeiculoCTR().pesquisar(rs.getString(5));
            RegistraDTO r = new RegistraDTO(rs.getLong(1), f1, f2, f3, v,
                    rs.getString(7), rs.getString(6), rs.getString(9),
                    rs.getString(8), rs.getString(10), rs.getString(11));
            al.add(r);
        }

        rs.close();
        connection.close();
        return al;
    }

    public ArrayList<RegistraDTO> listarObs(String placa) throws SQLException {
        ArrayList<RegistraDTO> al = new ArrayList<>();
        query = " call listarObsFiltrado(?); ";
        ps = connection.prepareStatement(query);
        ps.setString(1, placa);
        rs = ps.executeQuery();
        while (rs.next()) {
            FuncionarioDTO f1 = new FuncionarioCTR().procurarFunc(rs.getLong(2));
            FuncionarioDTO f2 = new FuncionarioCTR().procurarFunc(rs.getLong(3));
            FuncionarioDTO f3 = new FuncionarioCTR().procurarFunc(rs.getLong(4));
            VeiculoDTO v = new VeiculoCTR().pesquisar(rs.getString(5));
             RegistraDTO r = new RegistraDTO(rs.getLong(1), f1, f2, f3, v,
                    rs.getString(7), rs.getString(6), rs.getString(9),
                    rs.getString(8), rs.getString(10), rs.getString(11));
            al.add(r);
        }

        rs.close();
        connection.close();
        return al;
    }

    public RegistraDTO procurarRegistraInicio(String placa, String data, String hora) throws SQLException {
        query = " call procurarRegistraInicio(?, ?, ?); ";
        ps = connection.prepareStatement(query);
        ps.setString(1, placa);
        ps.setString(2, data);
        ps.setString(3, hora);
        rs = ps.executeQuery();
        if (rs.next()) {
            return new RegistraDTO(rs.getLong(1),
                    new FuncionarioCTR().procurarFunc(rs.getLong(2)),
                    new FuncionarioCTR().procurarFunc(rs.getLong(3)),
                    new FuncionarioCTR().procurarFunc(rs.getLong(4)),
                    new VeiculoCTR().pesquisarVeiculo(rs.getString(5)),
                    rs.getString(7), rs.getString(6), rs.getString(9), rs.getString(8),
                    rs.getString(10), rs.getString(11));
        }

        rs.close();
        connection.close();
        return null;
    }

    public RegistraDTO procurarRegistraSaida(String placa) throws SQLException {
        query = " call procurarRegistra(?); ";
        ps = connection.prepareStatement(query);
        ps.setString(1, placa);

        rs = ps.executeQuery();
        if (rs.next()) {
            return new RegistraDTO(rs.getLong(1),
                    new FuncionarioCTR().procurarFunc(rs.getLong(2)),
                    new FuncionarioCTR().procurarFunc(rs.getLong(3)),
                    new FuncionarioCTR().procurarFunc(rs.getLong(4)),
                    new VeiculoCTR().pesquisarVeiculo(rs.getString(5)),
                    rs.getString(7), rs.getString(6), rs.getString(9), rs.getString(8),
                    rs.getString(10), rs.getString(11));
        }

        rs.close();
        connection.close();
        return null;
    }

    public RegistraDTO procurarRegistra(VeiculoDTO placa) {

        connection = new ConnectionFactory().getConnection();
        try {
            ps = connection.prepareStatement("call procurarRegistra(?)");
            ps.setString(1, placa.getPlaca());
            rs = ps.executeQuery();
            if (rs.next()) {
                FuncionarioDTO matProp = new FuncionarioCTR().procurarFunc(rs.getLong(2));
                FuncionarioDTO matFunE = new FuncionarioCTR().procurarFunc(rs.getLong(3));
                FuncionarioDTO matFunS = new FuncionarioCTR().procurarFunc(rs.getLong(4));
                VeiculoDTO placa2 = new VeiculoCTR().pesquisarVeiculo(rs.getString(5));
                System.out.println("Numerod do registro : " + rs.getLong(1));
                return new RegistraDTO(rs.getLong(1), matProp, matFunE, matFunS, placa2,
                        rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getString(8), rs.getString(10), rs.getString(11));
            }
            ps.close();
            rs.close();
            connection.close();
            return null;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public RegistraDTO procurarNumero(RegistraDTO r) {

        connection = new ConnectionFactory().getConnection();
        try {
            ps = connection.prepareStatement("call procurarRegistraNumero(?)");
            ps.setLong(1, r.getNumero());
            rs = ps.executeQuery();
            if (rs.next()) {
                FuncionarioDTO matProp = new FuncionarioCTR().procurarFunc(rs.getLong(2));
                FuncionarioDTO matFunE = new FuncionarioCTR().procurarFunc(rs.getLong(3));
                FuncionarioDTO matFunS = new FuncionarioCTR().procurarFunc(rs.getLong(4));
                VeiculoDTO placa2 = new VeiculoCTR().pesquisarVeiculo(rs.getString(5));
                System.out.println("Numerod do registro : " + rs.getLong(1));
                return new RegistraDTO(rs.getLong(1), matProp, matFunE, matFunS, placa2,
                        rs.getString(7), rs.getString(8), rs.getString(9),
                        rs.getString(8), rs.getString(10), rs.getString(11));
            }
            ps.close();
            rs.close();
            connection.close();
            return null;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public ArrayList<RegistraDTO> listarEntradas() throws SQLException {
        ArrayList<RegistraDTO> al = new ArrayList<>();
        query = " call listarEntradas(); ";
        ps = connection.prepareStatement(query);
        rs = ps.executeQuery();
        while (rs.next()) {
            FuncionarioDTO f1 = new FuncionarioCTR().procurarFunc(rs.getLong(2));
            FuncionarioDTO f2 = new FuncionarioCTR().procurarFunc(rs.getLong(3));
            FuncionarioDTO f3 = new FuncionarioCTR().procurarFunc(rs.getLong(4));
            VeiculoDTO v = new VeiculoCTR().pesquisar(rs.getString(5));
            RegistraDTO r = new RegistraDTO(rs.getLong(1), f1, f2, f3, v,
                    rs.getString(7), rs.getString(6), rs.getString(9),
                    rs.getString(8), rs.getString(10), rs.getString(11));
            al.add(r);
        }

        rs.close();
        connection.close();
        return al;
    }

}
