package org.example;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        new ClientGUI(serverWindow, "Евгений Петрович");
        new ClientGUI(serverWindow, "Семен Степанович");
        System.out.println("Method main() is over");
    }
}