import ru.progwards.java2.lessons.http.Account;
import ru.progwards.java2.lessons.http.service.AccountService;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class AtmClient implements AccountService {
    static String [] id;   // массив id из базы, для того, чтобы можно было формировать запросы

    @Override
    public double balance(Account account) {
        String request = "GET /balance?account=" + account.getId() + " HTTP/1.1\n" +
                "host:localhost\n\n";
        Double balance = reqToBank(request);
        if (balance != null) {
            System.out.println("Ваш баланс = " + balance + " рублей");
            return balance;
        } else return 0;
    }

    @Override
    public void deposit(Account account, double amount) {
        String request = "GET /deposit?account=" + account.getId() + "&amount=" + amount + "HTTP/1.1\n" +
                "host:localhost\n\n";
        reqToBank(request);
    }

    @Override
    public void withdraw(Account account, double amount) {
        String request = "GET /withdraw?account=" + account.getId() + "&amount=" + amount + "HTTP/1.1\n" +
                "host:localhost\n\n";
        reqToBank(request);
    }

    @Override
    public void transfer(Account from, Account to, double amount) {
        String request = "GET /transfer?from=" + from.getId() + "&to=" + to.getId() + "&amount=" + amount + "HTTP/1.1\n" +
                "host:localhost\n\n";
        reqToBank(request);
    }

    public Double reqToBank (String request) {
        boolean balanceNeed = false;
        Double balance = null;
        try (Socket client = new Socket("127.0.0.1", 4500);
             InputStream is = client.getInputStream();
             OutputStream os = client.getOutputStream();
             Scanner scanner = new Scanner(is);
             PrintWriter pw = new PrintWriter(os, true)) {

            //отправка запроса на сервер и полузакрытие
            pw.println(request);
            client.shutdownOutput();

            //чтение ответа от сервера
            String str;
            while (scanner.hasNextLine()) {
                str = scanner.nextLine();
                System.out.println(str);
                if (balanceNeed)
                    balance = Double.parseDouble(str);
                if (str.equals(""))
                    balanceNeed = true;
            }
        }catch (IOException ex) {
            ex.printStackTrace();
        }
        return balance;
    }

    public static void main(String[] args) {
        try {
            //чтение id аккаунтов из файла и занесение в массив id
            String idString = new String(Files.readAllBytes(Paths.get("IDFile.txt")));
            id = idString.substring(1, idString.length()-1).replace(" ", "").split(",");

            AtmClient atmClient = new AtmClient();

            Account acc1 = new Account();
            acc1.setId(id[0]);
            Account acc2 = new Account();
            acc2.setId(id[1]);

            atmClient.balance(acc1);
//            atmClient.deposit(acc1, 1000);
//            atmClient.withdraw(acc1, 500);
//            atmClient.balance(acc2);
//            atmClient.transfer(acc1, acc2, 5000000);
//            atmClient.balance(acc1);
//            atmClient.balance(acc2);

        }catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
