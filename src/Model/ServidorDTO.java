/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Gabriel San Martin, Pedro Vinícius, Wyller Douglas.
 */
public class ServidorDTO extends FuncionarioDTO {

    public ServidorDTO(long matricula, String nome, String email,
            String telFixo, String telCelular, String ativo) {

        super(matricula, nome, "", "", email, telFixo, telCelular, ativo);
    }
}
