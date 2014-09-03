package model;

import Server.Database;

import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Agatha of Wood Beyond on 7/3/2014.
 */
public class Data {
    static Database db = new Database();
    private static ArrayList<Account> accounts = new ArrayList<Account>();
    private static ArrayList<String> accountInfo = new ArrayList<String>();

    public Data() {

    }

    public static void addAccount(String username, String password, int money) {
        Account account = new Account(username, password, money);
        accounts.add(account);
    }

    public static void loadAccounts() {
        accountInfo = db.getAccountInfo();
        accountInfo = db.getAccountInfo();
        for (int i = 0; i < accountInfo.size(); i++) {
            StringTokenizer tokenizer = new StringTokenizer(accountInfo.get(i), ",");
            String username = tokenizer.nextToken();
            String password = tokenizer.nextToken();
            int money = Integer.valueOf(tokenizer.nextToken());
            addAccount(username, password, money);
        }
    }

    public static void saveAccounts() {

    }

    public static void update(String name, int money) {
        db.updateMoney(name, money);
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }

    public static void main(String[] args) {
        loadAccounts();

    }
}
