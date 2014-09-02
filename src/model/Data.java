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

    public static void addAccount(String username, String password) {
        Account account = new Account(username, password);
        accounts.add(account);
    }

    public static void loadAccounts() {
        BufferedReader br = null;
        try {
            FileReader fr = new FileReader("src/bin/accounts.txt");
            br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                StringTokenizer tokenizer = new StringTokenizer(line, ",");
                addAccount(tokenizer.nextToken(), tokenizer.nextToken());
            }

        } catch (IOException e) {
            System.out.println("Nothing to read.");

        } finally {
            try {
                if (br != null) br.close();

            } catch (IOException ex) {
                System.out.println("Cannot close file.");
            }
        }
    }

    public static void saveAccounts() {
        try {
            File file = new File("src/bin/accounts.txt");
            //if file doesn't exists, then create it
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter writer = new BufferedWriter(fw);

            String line = "Nothing\n";
            for (int i = 0; i < accounts.size(); i++) {
                line = accounts.get(i).toString();
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
