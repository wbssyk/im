package com.example.server;

import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import java.io.*;

import static org.fusesource.jansi.Ansi.Color.*;
import static org.fusesource.jansi.Ansi.ansi;

/**
 * @author shiyakun
 */
public class BannerHelper {
    public static void banner(int color_index){
        Ansi.Color color;
        switch (color_index){
            case 0:color=BLACK;break;
            case 1:color=RED;break;
            case 2:color=GREEN;break;
            case 3:color=YELLOW;break;
            case 4:color=BLUE;break;
            case 5:color=MAGENTA;break;
            case 6:color=CYAN;break;
            case 7:color=WHITE;break;
            default:color=DEFAULT;
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
            System.out.println(ansi().fg(color).a(jsonStr).reset());

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(inputStreamReader!=null) {
                try {
                    inputStreamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            AnsiConsole.systemUninstall();
        }
    }

    public static void main(String[] args) {
        banner(0);
    }
}
