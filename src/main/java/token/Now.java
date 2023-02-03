/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package token;

import dbtable.Connexion;
import dbtable.DBTable;
import java.sql.Connection;

/**
 *
 * @author Fanjava-P14A-V46
 */
public class Now extends DBTable{
    private String now;

    public String getNow() {
        return now;
    }

    public void setNow(String now) {
        this.now = now;
    }
}
