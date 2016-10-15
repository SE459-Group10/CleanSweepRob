package edu.depaul.cdm.se459.service;

import edu.depaul.cdm.se459.ui.MainFrame;

import javax.swing.*;

/**
 * Created by Suqing on 10/2/16.
 */
public class AppMain {

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainFrame main = new MainFrame();
            }
        });
    }

}
