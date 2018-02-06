/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.ConnectionFactory;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class FuncionarioDAO {

    private Connection connection;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;
    private String query;
    private List<FuncionarioDTO> funcionarios;

    public int adicionarFuncionario(FuncionarioDTO funcionario, File file) {

        this.query = "call adicionaFunc(?, ?, ?, ?, ?, ?, ?)";
        this.connection = new ConnectionFactory().getConnection();
        FileInputStream fis = null;
        try {

            fis = new FileInputStream(file);
            this.cs = this.connection.prepareCall(query);

            this.cs.setLong(1, funcionario.getMatricula());
            this.cs.setString(2, funcionario.getNome());
            this.cs.setString(3, funcionario.getCargo().substring(0, 1));
            this.cs.setString(4, funcionario.getEmail());
            this.cs.setString(5, funcionario.getTelFixo());
            this.cs.setString(6, funcionario.getTelCelular());

            this.cs.setBinaryStream(7, fis);

            this.cs.execute();
            this.connection.close();

            return 1;
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                return 2;
            }
            System.out.println(ex);
            return 0;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    public int excluirFuncionario(FuncionarioDTO funcionario) {

        this.query = "call excluirFunc(?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);
            this.cs.setLong(1, funcionario.getMatricula());

            this.cs.execute();
            this.connection.close();

            return 1;
        } catch (SQLException ex) {

            return 0;
        }
    }

    public int alterarFuncionario(FuncionarioDTO funcionario, File file) {

        this.query = "call alterarFunc(?, ?, ?, ?, ?, ?, ?,?)";
        this.connection = new ConnectionFactory().getConnection();
        FileInputStream fis = null;
        try {

            this.cs = this.connection.prepareCall(query);

            this.cs.setLong(1, funcionario.getMatricula());
            this.cs.setString(2, funcionario.getNome());
            this.cs.setString(3, funcionario.getCargo().substring(0, 1));
            this.cs.setString(5, funcionario.getEmail());
            this.cs.setString(4, funcionario.getSenha());
            this.cs.setString(6, funcionario.getTelFixo());
            this.cs.setString(7, funcionario.getTelCelular());
            if (file != null) {
                fis = new FileInputStream(file);
                this.cs.setBinaryStream(8, fis);
            } else {
                this.cs.setBlob(8, funcionario.getImagem());
            }

            this.cs.execute();
            this.connection.close();

            return 1;
        } catch (SQLException ex) {

            return (ex.getErrorCode() == 1062) ? 2 : 0;
        } catch (FileNotFoundException ex) {
            return 3;
        }

    }

    public List<FuncionarioDTO> listarFuncionarios() {

        this.query = "call listarFunc()";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.ps = this.connection.prepareStatement(query);

            rs = ps.executeQuery();

            funcionarios = new ArrayList<>();
            FuncionarioDTO funcionario;

            while (rs.next()) {

                funcionario = new FuncionarioDTO(rs.getLong(1), rs.getString(2),
                        rs.getString(3).equals("G") ? "GAM" : "Porteiro",
                        rs.getString(4), rs.getString(5),
                        rs.getString(6), rs.getString(7),
                        rs.getString(9), rs.getBlob(8));
                funcionarios.add(funcionario);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return this.funcionarios;
    }

    public FuncionarioDTO procurarFuncionario(FuncionarioDTO funcionario) {

        this.query = "call procurarFunc(?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.ps = connection.prepareCall(query);
            ps.setLong(1, funcionario.getMatricula());
            rs = ps.executeQuery();
            if (rs.next()) {
                String s = rs.getString(3);
                if (s == null) {
                    s = "";
                } else {
                    s = rs.getString(3).equals("G") ? "GAM" : "Porteiro";
                }
                return new FuncionarioDTO(funcionario.getMatricula(), rs.getString(2), s,
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(9), rs.getBlob(8));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }

    }

    public List<FuncionarioDTO> listarFuncionarios(String nome) {

        this.query = "call procurarFuncNome(?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.ps = this.connection.prepareStatement(query);
            ps.setString(1, nome);
            rs = ps.executeQuery();

            funcionarios = new ArrayList<>();
            FuncionarioDTO funcionario;

            while (rs.next()) {

                funcionario = new FuncionarioDTO(rs.getLong(1), rs.getString(2), rs.getString(3).equals("G") ? "GAM" : "Porteiro",
                        rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7),
                        rs.getString(9), rs.getBlob(8));
                funcionarios.add(funcionario);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return this.funcionarios;
    }

    public void readmitirFunc(FuncionarioDTO func) {
        this.query = "call readmitirFunc(?)";
        this.connection = new ConnectionFactory().getConnection();
        try {

            this.ps = this.connection.prepareStatement(query);
            this.ps.setLong(1, func.getMatricula());
            this.ps.execute();
            this.ps.close();
            this.connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void inserirSenha(FuncionarioDTO f) {
        this.query = "call inserirSenha(?, ?)";
        this.connection = new ConnectionFactory().getConnection();
        try {

            this.ps = this.connection.prepareStatement(query);
            this.ps.setLong(1, f.getMatricula());
            this.ps.setString(2, f.getSenha());
            this.ps.execute();
            this.ps.close();
            this.connection.close();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public FuncionarioDTO procurarFuncionarioGeral(FuncionarioDTO funcionario) {

        this.query = "call procurarFuncGeral(?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.ps = connection.prepareCall(query);
            ps.setLong(1, funcionario.getMatricula());
            rs = ps.executeQuery();
            if (rs.next()) {
                String s = rs.getString(3);
                if (s == null) {
                    s = "";
                } else {
                    s = rs.getString(3).equals("G") ? "GAM" : "Porteiro";
                }
                return new FuncionarioDTO(funcionario.getMatricula(), rs.getString(2), s,
                        rs.getString(4), rs.getString(5), rs.getString(6),
                        rs.getString(7), rs.getString(9), rs.getBlob(8));
            } else {
                return null;
            }
        } catch (SQLException ex) {
            return null;
        }
    }

}
