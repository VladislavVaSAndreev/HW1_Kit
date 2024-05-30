package org.example;
import javax.swing.*;
import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
public class ServerWindow extends JFrame {private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;

    private final JButton btnStart = new JButton("Старт");
    private final JButton btnStop = new JButton("Стоп");
    private final JTextArea log = new JTextArea();
    private boolean isServerWorking;
    private boolean isServerStopped;

    private final List<ClientGUI> connectedClients = new ArrayList<>();

    public ServerWindow() {
        isServerWorking = false;
        isServerStopped = false;

        btnStop.setEnabled(false);

        btnStop.addActionListener(e -> {
            if (!isServerWorking) {
                logMessage("Сервер уже остановлен");
            } else {
                stopServer();
            }
        });

        btnStart.addActionListener(e -> {
            if (isServerWorking) {
                logMessage("Сервер уже запущен");
            } else {
                startServer();
            }
        });

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Чат");
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.add(btnStart);
        topPanel.add(btnStop);
        add(topPanel, BorderLayout.NORTH);

        log.setEditable(true);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog, BorderLayout.CENTER);

        setVisible(true);
    }

    private void startServer() {
        isServerWorking = true;
        isServerStopped = false;
        btnStart.setEnabled(false);
        btnStop.setEnabled(true);
        logMessage("Сервер запущен");
    }

    public void stopServer() {
        isServerStopped = true;
        isServerWorking = false;
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);
        for (ClientGUI client : connectedClients) {
            client.disconnect();
        }
        logMessage("Сервер остановлен. Все клиенты отключены");
    }

    public boolean isServerRunning() {
        return isServerWorking;
    }

    public boolean isServerStopped() {
        return isServerStopped;
    }

    public void logMessage(String message) {
        log.append(message + "\n");
        saveLogToFile(message);
    }

    public void addClient(ClientGUI client) {
        connectedClients.add(client);
    }

    public void removeClient(ClientGUI client) {
        connectedClients.remove(client);
    }

    public void broadcastMessage(String message, ClientGUI sender) {
        log.append(message + "\n");
        for (ClientGUI client : connectedClients) {
            if (client != sender) {
                client.receiveMessage(message);
            }
        }
        saveLogToFile(message);
    }

    private void saveLogToFile(String message) {
        String filePath = "src/main/java/org/log.txt";
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath, true))) {
            writer.println(message);
        } catch (IOException e) {
            System.err.println("Ошибка при записи в файл лога: " + e.getMessage());
        }
    }

}

