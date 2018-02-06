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

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class ServidorDAO {

    private Connection connection;
    private PreparedStatement ps;
    private CallableStatement cs;
    private ResultSet rs;
    private String query;
    private ArrayList<ServidorDTO> servidores;

    public int adicionarServidor(ServidorDTO servidor) {

        this.query = "call inserirServidor(?, ?, ?, ?, ?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);

            this.cs.setLong(1, servidor.getMatricula());
            this.cs.setString(2, servidor.getNome());
            this.cs.setString(3, servidor.getEmail());
            this.cs.setString(4, servidor.getTelFixo());
            this.cs.setString(5, servidor.getTelCelular());

            this.cs.execute();
            this.connection.close();

            return 1;
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                return 2;
            }
            ex.printStackTrace();
            return 0;
        }
    }

    public int excluirServidor(ServidorDTO servidor) {

        this.query = "call excluirFunc(?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);
            this.cs.setLong(1, servidor.getMatricula());

            this.cs.execute();
            this.connection.close();

            return 1;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return 0;
        }
    }

    public int alterarServidor(ServidorDTO servidor) {

        this.query = "call alterarServ(?, ?, ?, ?, ?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);

            this.cs.setLong(1, servidor.getMatricula());
            this.cs.setString(2, servidor.getNome());
            this.cs.setString(3, servidor.getEmail());
            this.cs.setString(4, servidor.getTelFixo());
            this.cs.setString(5, servidor.getTelCelular());

            this.cs.execute();
            this.connection.close();

            return 1;
        } catch (SQLException ex) {

            return (ex.getErrorCode() == 1062) ? 2 : 0;
        }
    }

    public ArrayList<ServidorDTO> listarServidor() {

        this.query = "call listarServ()";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.ps = this.connection.prepareStatement(query);

            rs = ps.executeQuery();

            servidores = new ArrayList<>();

            while (rs.next()) {

                this.servidores.add(new ServidorDTO(this.rs.getInt(1), this.rs.getString(2),
                        this.rs.getString(5), this.rs.getString(6), this.rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException ex) {

            throw new RuntimeException(ex);
        }

        return this.servidores;
    }

    public ServidorDTO procurarServidor(long mat) {

        this.query = "call procurarServ(?, ?, ?, ?, ?, ?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.cs = this.connection.prepareCall(query);
            this.cs.setLong(1, mat);
            this.cs.registerOutParameter(2, java.sql.Types.VARCHAR);
            this.cs.registerOutParameter(3, java.sql.Types.VARCHAR);
            this.cs.registerOutParameter(4, java.sql.Types.VARCHAR);
            this.cs.registerOutParameter(5, java.sql.Types.VARCHAR);
            this.cs.registerOutParameter(3, java.sql.Types.CHAR);

            this.cs.execute();
            System.out.println(mat);
            if (cs.getString(2) == null) {
                return null;
            }

            return (new ServidorDTO(mat, cs.getString(2), cs.getString(3), cs.getString(4), cs.getString(5), cs.getString(6)));
        } catch (SQLException ex) {
            ex.printStackTrace();

            return null;
        }
    }

    public ArrayList<ServidorDTO> listarServidorNome(String nome) {

        this.query = "call procurarServNome(?)";
        this.connection = new ConnectionFactory().getConnection();

        try {

            this.ps = this.connection.prepareStatement(query);
            ps.setString(1, nome);
            rs = ps.executeQuery();

            servidores = new ArrayList<>();

            while (rs.next()) {

                this.servidores.add(new ServidorDTO(this.rs.getInt(1), this.rs.getString(2),
                        this.rs.getString(5), this.rs.getString(6), this.rs.getString(7), rs.getString(8)));
            }
        } catch (SQLException ex) {

            return null;
        }

        return this.servidores;
    }
}
