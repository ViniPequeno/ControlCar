/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class ConnectionFactory {

    private final String url, user, password;

    public ConnectionFactory() {

        this.url = "jdbc:mysql://localhost:3306/controlcar";
        this.user = "root";
        this.password = "root";
    }

    public Connection getConnection() {

        try {

            return DriverManager.getConnection(this.url, this.user, this.password);
        } catch (SQLException ex) {
            
            throw new RuntimeException(ex);
        }
    }
}
