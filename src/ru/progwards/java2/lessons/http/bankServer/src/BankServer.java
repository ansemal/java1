import ru.progwards.java2.lessons.http.Account;
import ru.progwards.java2.lessons.http.Store;
import ru.progwards.java2.lessons.http.service.AccountServiceImpl;
import ru.progwards.java2.lessons.http.service.StoreServiceImpl;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BankServer {
    public static ConcurrentHashMap<String, Account> store = Store.getStore();

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(4500);
             RandomAccessFile change = new RandomAccessFile("IDFile.txt", "rw")) {

            System.out.println(serverSocket.getLocalPort());
            //запись в файл id аккаунтов, чтобы на клиенте можно было знать и послать запрос
            change.writeBytes(store.keySet().toString());

            //установка продолжительности работы сервера
            serverSocket.setSoTimeout(15000);
            while (true) {
                Socket fromClient = serverSocket.accept();
                new Thread(new MyRunnable(fromClient, store)).start();
            }
        } catch (SocketTimeoutException ex) {
            System.out.println("Время работы сервера истекло");
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}

class MyRunnable implements Runnable {

    Socket fromClient;
    ArrayList<String> request = new ArrayList<>(); // запрос от клиента
    String response = "";                          // ответ от сервера
    Map<String, Account> store;                    //база аккаунтов

    Account account, to;
    double amount;

    public MyRunnable (Socket fromClient, Map<String, Account> store) {
        this.fromClient = fromClient;
        this.store = store;
    }

    @Override
    public void run() {
        String method;
        try (InputStream iStream = fromClient.getInputStream();
             OutputStream oStream = fromClient.getOutputStream();
             Scanner scanner = new Scanner(iStream)) {

            while (scanner.hasNextLine())
                request.add(scanner.nextLine());

            request.set(0, request.get(0).trim());

            if (request.get(request.size()-1).equals("") && request.get(0).startsWith("GET") && request.get(0).endsWith("HTTP/1.1")) {
                //замена разделителей на &, затем берем запрос и заносим метод с параметрами в массив
                String [] msg = request.get(0).replaceAll("[=?]", "&").substring(5,request.get(0).length()-9).split("&");

                for (int i=1; i<msg.length-1; i+=2) {
                    switch(msg[i]) {
                        case "account", "from" -> account = store.get(msg[i + 1]);
                        case "to" -> to = store.get(msg[i + 1]);
                        case "amount" -> amount = Double.parseDouble(msg[i + 1]);
                    }
                }

                AccountServiceImpl acSImpl = new AccountServiceImpl(new StoreServiceImpl());

                method = msg[0];

                try {
                    switch (method) {
                        case "balance" -> amount = acSImpl.balance(account);
                        case "deposit" -> acSImpl.deposit(account, amount);
                        case "withdraw" -> acSImpl.withdraw(account, amount);
                        case "transfer" -> acSImpl.transfer(account, to, amount);
                    }
                } catch (Exception ex) {
                    //Ловим ошибку о недостатке денег
                    response = "HTTP/1.1 400 " + ex.getMessage();
                }

                //если ошибок в операциях не было
                if (response.equals("")) {
                    response = "HTTP/1.1 200 OK\n" +
                            "Content-Type: text/html; charset=utf-8\n" +
                            "Content-Length: 1234\n\n";
                    // при запросе баланса - добавляем в ответ баланс
                    if (method.equals("balance")) {
                        response += amount;
                    }
                }
            } else
                response = "HTTP/1.1 400 Bad Request";

            //отправляем ответ клиенту
            PrintWriter pr = new PrintWriter(oStream,true);
            pr.println(response);
            fromClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
