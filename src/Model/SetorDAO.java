/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import Connection.ConnectionFactory;
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
public class SetorDAO {

    private Connection connection;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;
    private String query;
    private List<SetorDTO> setores;

    public int adicionarSetor(SetorDTO setor) {

        this.query = "call inserirSetor(?, ?, ?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            cs = this.connection.prepareCall(query);

            cs.setLong(1, setor.getCodigo());
            cs.setString(2, setor.getNome());
            cs.setString(3, setor.getRamal());
            cs.execute();
            connection.close();

            return 1;
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                return 2;
            }
            return 0;
        }
    }

    public int excluirSetor(SetorDTO setor) {

        this.query = "call removerSetor(?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);
            this.cs.setLong(1, setor.getCodigo());

            this.cs.execute();
            this.connection.close();

            return 1;
        } catch (SQLException ex) {

            return 0;
        }
    }

    public int alterarSetor(SetorDTO setor) {

        this.query = "call alterarSetor(?, ?, ?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);

            this.cs.setLong(1, setor.getCodigo());
            this.cs.setString(2, setor.getNome());
            this.cs.setString(3, setor.getRamal());

            this.cs.execute();
            this.connection.close();

            return 1;
        } catch (SQLException ex) {

            return (ex.getErrorCode() == 1062) ? 2 : 0;
        }
    }

    public List<SetorDTO> listarSetores() {

        this.query = "call listarSetor()";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.ps = this.connection.prepareStatement(query);

            rs = ps.executeQuery();

            setores = new ArrayList<>();
            SetorDTO setor;

            while (rs.next()) {

                setor = new SetorDTO(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4));
                setores.add(setor);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return this.setores;
    }

    public List<SetorDTO> listarSetores(String pesquisa) {

        this.query = "call procurarSetorNome(?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.ps = this.connection.prepareStatement(query);
            ps.setString(1, pesquisa);
            rs = ps.executeQuery();
            setores = new ArrayList<>();

            SetorDTO setor;

            while (rs.next()) {

                setor = new SetorDTO(rs.getInt(1), rs.getString(2), rs.getString(3),
                        rs.getString(4));
                setores.add(setor);
            }
            return this.setores;
        } catch (SQLException ex) {

            throw new RuntimeException(ex);
        }
    }

    public SetorDTO procurarSetor(long cod) {
        this.query = "call procurarSetor(?, ?, ?, ?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);
            cs.setLong(1, cod);
            cs.registerOutParameter(2, java.sql.Types.VARCHAR);
            cs.registerOutParameter(3, java.sql.Types.VARCHAR);
            cs.registerOutParameter(4, java.sql.Types.CHAR);
            cs.execute();

            if (cs.getString(2) != null) {
                return new SetorDTO(cod, cs.getString(2), cs.getString(3),
                        cs.getString(4));
            }
        } catch (SQLException ex) {

            return null;
        }
        return null;
    }

    public void readmitirSetor(long mat) {
        connection = new ConnectionFactory().getConnection();
        try {

            ps = connection.prepareStatement("call readmitirSetor(?)");

            ps.setLong(1, mat);
            ps.executeUpdate();
            ps.close();
            connection.close();

        } catch (SQLException ex) {
            Logger.getLogger(SetorDAO.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}
