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
public class VeiculoDAO {

    PreparedStatement ps;
    CallableStatement cs;
    Connection con;
    ResultSet rs;

    public VeiculoDAO() {
        con = new ConnectionFactory().getConnection();
    }

    public int adicionar(VeiculoDTO v) {
        try {
            ps = con.prepareStatement("call adicionarVeiculo(?,?,?,?,?)");

            ps.setString(1, v.getPlaca());
            ps.setString(2, v.getCor());
            ps.setString(3, v.getMarca());
            ps.setString(4, v.getModelo());
            ps.setString(5, v.getAnoFabri());
            System.out.println("olaaa");
            ps.execute();
            ps.close();
            return 1;
        } catch (SQLException ex) {
            if (ex.getErrorCode() == 1062) {
                return 2;
            }
            return 0;
        }
    }

    public VeiculoDTO pesquisa(String placa) {

        try {
            this.cs = this.con.prepareCall("call pesquisarVeiculo(?, ?, ?, ?, ?)");
            cs.setString(1, placa);
            cs.registerOutParameter(2, java.sql.Types.VARCHAR);
            cs.registerOutParameter(3, java.sql.Types.VARCHAR);
            cs.registerOutParameter(4, java.sql.Types.VARCHAR);
            cs.registerOutParameter(5, java.sql.Types.VARCHAR);
            cs.execute();

            return new VeiculoDTO(placa, cs.getString(2), cs.getString(3), cs.getString(4),
                    cs.getString(5));
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public List<VeiculoDTO> listar() {

        try {
            ps = con.prepareStatement("call listar()");
            rs = ps.executeQuery();
            List<VeiculoDTO> veiculos = new ArrayList<>();
            while (rs.next()) {
                VeiculoDTO veiculo = new VeiculoDTO(rs.getString(1),
                        rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5));
                veiculos.add(veiculo);

            }
            rs.close();
            return veiculos;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public ArrayList<VeiculoDTO> listarFiltrado(String nome) {

        try {
            ps = con.prepareStatement("call listarFiltrado(?)");
            ps.setString(1, nome);
            rs = ps.executeQuery();
            ArrayList<VeiculoDTO> veiculos = new ArrayList<>();
            while (rs.next()) {
                VeiculoDTO veiculo = new VeiculoDTO(rs.getString(1),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)
                );
                veiculos.add(veiculo);
            }
            rs.close();
            return veiculos;
        } catch (SQLException ex) {
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

    public VeiculoDTO pesquisaVeiculo(String placa) {
        System.out.println("String é : " + placa);
        try {
            this.cs = this.con.prepareCall("call veiculo(?)");
            cs.setString(1, placa);
            rs = cs.executeQuery();
            if (rs.next()) {
                return new VeiculoDTO(placa, rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5));
            }

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            Logger.getLogger(VeiculoDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;

    }

}
