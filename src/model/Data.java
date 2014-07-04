package model;

import java.io.*;
import java.util.ArrayList;
import java.util.StringTokenizer;

/**
 * Created by Agatha of Wood Beyond on 7/3/2014.
 */
public class Data {

    private static ArrayList<Account> accounts = new ArrayList<Account>();

    public Data() {
    }

    public static void add(String username, String password) {
        Account account = new Account(username, password);
        accounts.add(account);
    }

    public static void loadData() {
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader("src/bin/accounts.txt");
            br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                add(tokenizer.nextToken(), tokenizer.nextToken());
            }

        } catch (IOException e) {
            e.printStackTrace();

        } finally {
            try {
                if (br != null) br.close();

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static void saveData() {
        try {
            File file = new File("src/bin/accounts.txt");
            // if file doesnt exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter writer = new BufferedWriter(fw);

            String line = "Nothing\n";
            for (int i = 0; i < accounts.size(); i++) {
                line = accounts.get(i).getUsername() + "," + accounts.get(i).getPassword() + "\n";
                writer.write(line);
            }

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Account> getAccounts() {
        return accounts;
    }
}
