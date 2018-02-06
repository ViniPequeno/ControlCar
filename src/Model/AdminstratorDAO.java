package Model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class AdminstratorDAO {

    public void salvar(AdminstratorDTO adm) {
        try {
            FileOutputStream fos = new FileOutputStream("adm.ser", false);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(adm);
            oos.close();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public boolean permitirAcesso(AdminstratorDTO adm) {
        try {

            FileInputStream fis = new FileInputStream("adm.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);

            AdminstratorDTO admSalvo = (AdminstratorDTO) ois.readObject();
            ois.close();
            fis.close();

            System.out.println(adm.getMatricula() + " " + adm.getSenha());
            System.out.println(admSalvo.getMatricula() + " " + admSalvo.getSenha());

            if (adm.getMatricula().equals(admSalvo.getMatricula()) && adm.getSenha().equals(admSalvo.getSenha())) {

                return true;
            } else {

                return false;
            }
        } catch (Exception ex) {
            System.out.println(ex);
            throw new RuntimeException();
        }
    }

    public AdminstratorDTO lerArquivo() {
        AdminstratorDTO adm = null;
        try {
            FileInputStream fos = new FileInputStream("adm.ser");
            ObjectInputStream oos = new ObjectInputStream(fos);
            adm = (AdminstratorDTO) oos.readObject();
            oos.close();
            fos.close();
        } catch (Exception e) {
            throw new RuntimeException();
        }
        return adm;
    }

}
