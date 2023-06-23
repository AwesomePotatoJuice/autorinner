package ru.surin;

import com.melloware.jintellitype.JIntellitype;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
public class Main {
    public static void main(String[] args) {
        if (!JIntellitype.isJIntellitypeSupported()) {
            System.exit(1);
        }

        new MainFrame();
    }
}
