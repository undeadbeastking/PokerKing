package model;

/**
 * Created by Agatha of Wood Beyond on 7/3/2014.
 */
public class Account {

    private String username;
    private String password;

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
}
