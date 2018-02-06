/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.ConnectionFactory;
import Controller.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class CadastraDAO {

    PreparedStatement ps;
    ResultSet rs;
    Connection con;

    public int adicionar(CadastraDTO cd) {
        con = new ConnectionFactory().getConnection();
        try {
            ps = con.prepareStatement("call adicionarCadastra(?,?,?,?)");
            ps.setLong(1, cd.getMatProp().getMatricula());
            ps.setLong(2, cd.getMatFun().getMatricula());
            ps.setString(3, cd.getPlaca().getPlaca());
            ps.setString(4, cd.getCalendario());

            ps.execute();
            ps.close();
            return 1;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            if (ex.getErrorCode() == 1062) {
                return 2;
            }
            return 0;
        }
    }

    public void exluir(String placa, Long mat) {
        con = new ConnectionFactory().getConnection();
        try {
            ps = con.prepareStatement("call excluirCadastra(?, ?)");
            ps.setString(1, placa);
            ps.setLong(2, mat);
            ps.execute();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(CadastraDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public void readmitirCadastra(String placa, Long mat) {
        con = new ConnectionFactory().getConnection();
        try {
            ps = con.prepareStatement("call readmitirCadastra(?, ?)");
            ps.setString(1, placa);
            ps.setLong(2, mat);
            ps.execute();
            ps.close();

        } catch (SQLException ex) {
            Logger.getLogger(CadastraDAO.class.getName()).log(Level.SEVERE, null, ex);

        }

    }

    public ArrayList<CadastraDTO> listar() {
        con = new ConnectionFactory().getConnection();
        ArrayList a = new ArrayList();
        try {
            ps = con.prepareStatement("call listar()");
            rs = ps.executeQuery();
            while (rs.next()) {
                FuncionarioDTO matProp = new FuncionarioCTR().procurarFunc(rs.getLong(16));
                FuncionarioDTO matFun = new FuncionarioCTR().procurarFunc(rs.getLong(17));
                VeiculoDTO placa = new VeiculoCTR().pesquisarVeiculo(rs.getString(18));
               
                a.add(new CadastraDTO(matProp, matFun, placa, rs.getString(4), rs.getString(20)));
            }
            ps.close();
            rs.close();
            con.close();
            return a;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return null;
        }
    }

    public ArrayList<CadastraDTO> listar(String nome) {
        con = new ConnectionFactory().getConnection();
        ArrayList a = new ArrayList();
        try {
            ps = con.prepareStatement("call listarFiltrado(?)");
            ps.setString(1, nome);
            rs = ps.executeQuery();
            while (rs.next()) {
                FuncionarioDTO matProp = new FuncionarioCTR().procurarFunc(rs.getLong(16));
                FuncionarioDTO matFun = new FuncionarioCTR().procurarFunc(rs.getLong(17));
                VeiculoDTO placa = new VeiculoCTR().pesquisarVeiculo(rs.getString(18));
                a.add(new CadastraDTO(matProp, matFun, placa, rs.getString(4), rs.getString(20)));
            }
            ps.close();
            rs.close();
            con.close();
            return a;

        } catch (SQLException ex) {
            Logger.getLogger(CadastraDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<CadastraDTO> procurarEntrada(VeiculoDTO v) {
        con = new ConnectionFactory().getConnection();
        ArrayList<CadastraDTO> a = new ArrayList<>();

        try {
            ps = con.prepareStatement("call procurarEntrada(?)");
            ps.setString(1, v.getPlaca());
            rs = ps.executeQuery();
            while (rs.next()) {
                FuncionarioDTO matProp = new FuncionarioCTR().procurarFunc(rs.getLong(1));
                FuncionarioDTO matFun = new FuncionarioCTR().procurarFunc(rs.getLong(2));
                VeiculoDTO placa = new VeiculoCTR().pesquisarVeiculo(rs.getString(3));
                a.add(new CadastraDTO(matProp, matFun, placa, rs.getString(4), rs.getString(5)));
            }

            ps.close();
            rs.close();
            con.close();
            return a;

        } catch (SQLException ex) {
            Logger.getLogger(CadastraDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public ArrayList<CadastraDTO> procurarSaida(VeiculoDTO v) {
        con = new ConnectionFactory().getConnection();
        ArrayList<CadastraDTO> a = new ArrayList<>();

        try {
            ps = con.prepareStatement("call procurarSaida(?)");
            ps.setString(1, v.getPlaca());
            rs = ps.executeQuery();
            while (rs.next()) {
                FuncionarioDTO matProp = new FuncionarioCTR().procurarFunc(rs.getLong(1));
                FuncionarioDTO matFun = new FuncionarioCTR().procurarFunc(rs.getLong(2));
                VeiculoDTO placa = new VeiculoCTR().pesquisarVeiculo(rs.getString(3));
                a.add(new CadastraDTO(matProp, matFun, placa, rs.getString(4), rs.getString(5)));
            }

            ps.close();
            rs.close();
            con.close();
            return a;

        } catch (SQLException ex) {
            Logger.getLogger(CadastraDAO.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

}
