package br.ufes.inf.nemo.soplpattern.dynamic.ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.UIManager;
import java.awt.Color;

public class janP_O_OU_Provider {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					janP_O_OU_Provider window = new janP_O_OU_Provider();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public janP_O_OU_Provider() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 642, 318);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "O-Provider", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		panel.setBounds(10, 11, 608, 118);
		frame.getContentPane().add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(10, 50, 133, 20);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(181, 50, 126, 20);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Person Provider");
		lblNewLabel.setBounds(10, 25, 133, 14);
		panel.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Service Provider");
		lblNewLabel_1.setBounds(207, 25, 78, 14);
		panel.add(lblNewLabel_1);
		
		JLabel label = new JLabel("Organization Unit Provider");
		label.setBounds(327, 25, 126, 14);
		panel.add(label);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(327, 50, 126, 20);
		panel.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(475, 50, 126, 20);
		panel.add(textField_3);
		
		JLabel label_1 = new JLabel("Organization Provider");
		label_1.setBounds(475, 25, 126, 14);
		panel.add(label_1);
		
		JLabel lblDescription = new JLabel("Description :");
		lblDescription.setBounds(10, 140, 77, 14);
		frame.getContentPane().add(lblDescription);
		
		JLabel lblRepresentsPersonsAs = new JLabel("Represents Persons, Organizations and Organizations Units as Service Providers.");
		lblRepresentsPersonsAs.setBounds(73, 140, 567, 14);
		frame.getContentPane().add(lblRepresentsPersonsAs);
	}
}
