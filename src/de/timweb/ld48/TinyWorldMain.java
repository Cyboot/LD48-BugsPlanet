package de.timweb.ld48;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import de.timweb.ld48.game.TinyWorldCanvas;



public class TinyWorldMain {

	public static void main(String[] args) {
		TinyWorldCanvas dc = new TinyWorldCanvas(1280, 768,10);
        JFrame frame = new JFrame("Bugs Planet");
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(dc);
        frame.setContentPane(panel);
        frame.pack();
        frame.setResizable(false);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        dc.start();
	}
}
