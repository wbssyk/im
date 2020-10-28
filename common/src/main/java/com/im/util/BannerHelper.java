package com.im.util;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.*;


/**
 * @author shiyakun
 */
public class BannerHelper {
    public static void banner(int color_index) {
        Ansi.Color color;
        switch (color_index) {
            case 0:
                color = Ansi.Color.BLACK;
                break;
            case 1:
                color = Ansi.Color.RED;
                break;
            case 2:
                color = Ansi.Color.GREEN;
                break;
            case 3:
                color = Ansi.Color.YELLOW;
                break;
            case 4:
                color = Ansi.Color.BLUE;
                break;
            case 5:
                color = Ansi.Color.MAGENTA;
                break;
            case 6:
                color = Ansi.Color.CYAN;
                break;
            case 7:
                color = Ansi.Color.WHITE;
                break;
            default:
                color = Ansi.Color.DEFAULT;
        }
        InputStreamReader inputStreamReader = null;
        AnsiConsole.systemInstall();
        try {
            InputStream resourceAsStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("banner.txt");
            inputStreamReader = new InputStreamReader(resourceAsStream);
            StringBuffer sb = new StringBuffer();
            int ch = 0;
            while ((ch = inputStreamReader.read()) != -1) {
                sb.append((char) ch);
            }
            String jsonStr = sb.toString();
            System.out.println(Ansi.ansi().fg(color).a(jsonStr).reset());

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStreamReader != null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            AnsiConsole.systemUninstall();
        }
    }
}
