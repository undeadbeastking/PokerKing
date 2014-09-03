package model;

import java.io.Serializable;

/**
 * Created by Agatha of Wood Beyond on 7/3/2014.
 */
public class Account implements Serializable {

    private String username;
    private String password;
    private int money;

    public Account(String x, String y, int z) {
        this.username = x;
        this.password = y;
        this.money = z;
    }

    public Account(String x, String y) {
        this.username = x;
        this.password = y;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getMoney() {
        return money;
    }

    @Override
    public String toString(){
        return username + ","+ password + "\n";
    }


}
