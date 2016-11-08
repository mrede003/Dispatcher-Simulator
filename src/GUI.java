import java.awt.BorderLayout;
import java.awt.Dialog.ModalityType;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class GUI extends JFrame {

	private JPanel panel;
	private JMenuBar menuBar;
	private JMenu menu;
	private JMenuItem addNew;
	private JMenuItem unBlock;
	private JMenuItem terminate;
	private JMenuItem readMe;
	private JMenuItem exit;
	private JButton cSwitch;
	private JTextArea info;
	private JScrollPane scroll;
	private simulator sim;
	private final String CS = "CONTEXT SWITCH: ";
	private int count = 1;

	public GUI(simulator disSim) {
		sim = disSim;
		initComponents();
	}

	private void addNewBox() {
		final JDialog addDialog = new JDialog();

		JPanel miniPanel = new JPanel(new BorderLayout());
		miniPanel.setPreferredSize(new Dimension(175, 100));

		ButtonGroup radioGroup = new ButtonGroup();
		final JRadioButton blocked = new JRadioButton("Blocked List");
		final JRadioButton ready = new JRadioButton("Ready Queue");
		ready.setSelected(true);
		radioGroup.add(ready);
		radioGroup.add(blocked);
		JPanel radios = new JPanel();
		radios.setPreferredSize(new Dimension(50, 50));
		radios.add(ready);
		radios.add(blocked);
		final JTextField text = new JTextField(10);

		JButton addButton = new JButton("Add");
		addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					int temp = Integer.parseInt(text.getText());
					if (blocked.isSelected()) {
						sim.addProcessBlocked(temp);
						info.setText(info.getText()
								+ "\nAdded Process to the Blocked List\n"
								+ sim.getStatus());

					} else {
						sim.addProcessReady(temp);
						info.setText(info.getText()
								+ "\nAdded Process to the Ready Queue\n"
								+ sim.getStatus());

					}
					addDialog.dispose();
				} catch (NumberFormatException n) {
					GUI.errorMessage("Please Enter a Number");
				}
			}
		});

		JLabel title = new JLabel("           Type in priority");

		miniPanel.add(title, BorderLayout.NORTH);
		miniPanel.add(addButton, BorderLayout.WEST);
		miniPanel.add(text, BorderLayout.EAST);
		miniPanel.add(radios, BorderLayout.SOUTH);

		addDialog.setTitle("Add New Process");
		addDialog.add(miniPanel);
		addDialog.setResizable(false);
		addDialog.pack();
		addDialog.setLocationRelativeTo(null);
		addDialog.setModal(true);
		addDialog.setAlwaysOnTop(true);
		addDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		addDialog.setVisible(true);

	}

	private void blockerBox() {
		final JDialog addDialog = new JDialog();

		JPanel miniPanel = new JPanel(new BorderLayout());
		miniPanel.setPreferredSize(new Dimension(220, 100));

		JLabel title = new JLabel("           Select Process ID");

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(110, 75));
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(110, 75));

		final JComboBox<String> blockBox = new JComboBox();
		Object[] tempP = sim.getReadyQueue().toArray();
		for (int i = 0; i < tempP.length; i++) {
			blockBox.addItem(((Process) tempP[i]).getID());
		}
		blockBox.setEditable(false);
		JButton block = new JButton("Block");
		block.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] tempArray = sim.getReadyQueue().toArray();
				for (int i = 0; i < tempArray.length; i++) {
					if (((Process) tempArray[i]).getID().equals(
							(String) blockBox.getSelectedItem())) {
						sim.getReadyQueue().remove(((Process) tempArray[i]));
						sim.getBlockedList().add(((Process) tempArray[i]));
					}
				}
				info.setText(info.getText() + "\nBlocked Process "
						+ (String) blockBox.getSelectedItem() + "\n"
						+ sim.getStatus());
				addDialog.dispose();
			}
		});

		leftPanel.add(block, BorderLayout.NORTH);
		leftPanel.add(blockBox, BorderLayout.SOUTH);

		final JComboBox<String> unBlockBox = new JComboBox();
		for (int i = 0; i < sim.getBlockedList().size(); i++) {
			unBlockBox.addItem(sim.getBlockedList().get(i).getID());
		}
		unBlockBox.setEditable(false);
		JButton unBlock = new JButton("UnBlock");
		unBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				for(int i=0; i<sim.getBlockedList().size();i++)
				{
					if(sim.getBlockedList().get(i).getID().equals((String)unBlockBox.getSelectedItem()))
					{
						sim.getReadyQueue().add(sim.getBlockedList().get(i));
						sim.getBlockedList().remove(i);
					}	
				}
				info.setText(info.getText() + "\nUnBlocked Process "
						+ (String) unBlockBox.getSelectedItem() + "\n"
						+ sim.getStatus());
				addDialog.dispose();
			}
		});
		rightPanel.add(unBlock, BorderLayout.NORTH);
		rightPanel.add(unBlockBox, BorderLayout.SOUTH);

		miniPanel.add(title, BorderLayout.NORTH);
		miniPanel.add(leftPanel, BorderLayout.WEST);
		miniPanel.add(rightPanel, BorderLayout.EAST);

		addDialog.setTitle("Block/Unblock Process");
		addDialog.add(miniPanel);
		addDialog.setResizable(false);
		addDialog.pack();
		addDialog.setLocationRelativeTo(null);
		addDialog.setModal(true);
		addDialog.setAlwaysOnTop(true);
		addDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		addDialog.setVisible(true);

	}

	private void terminationBox() {
		final JDialog addDialog = new JDialog();

		JPanel miniPanel = new JPanel(new BorderLayout());
		miniPanel.setPreferredSize(new Dimension(125, 75));

		JLabel title = new JLabel("   Select Process");
		JButton kill = new JButton("Kill");
		final JComboBox<String> termBox = new JComboBox();
		kill.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Object[] tempArray = sim.getReadyQueue().toArray();
				for (int i = 0; i < tempArray.length; i++) {
					if (((Process) tempArray[i]).getID().equals(
							(String) termBox.getSelectedItem()))
						sim.getReadyQueue().remove(((Process) tempArray[i]));

				}
				for (int i = 0; i < sim.getBlockedList().size(); i++) {
					if (sim.getBlockedList().get(i).getID()
							.equals((String) termBox.getSelectedItem()))
						sim.getBlockedList().remove(i);
				}
				info.setText(info.getText() + "\nTerminated Process "
						+ (String) termBox.getSelectedItem() + "\n"
						+ sim.getStatus());
				addDialog.dispose();
			}
		});
		Object[] tempP = sim.getReadyQueue().toArray();
		for (int i = 0; i < tempP.length; i++) {
			termBox.addItem(((Process) tempP[i]).getID());
		}
		for (int i = 0; i < sim.getBlockedList().size(); i++) {
			termBox.addItem(sim.getBlockedList().get(i).getID());
		}
		termBox.setEditable(false);

		miniPanel.add(title, BorderLayout.NORTH);
		miniPanel.add(kill, BorderLayout.CENTER);
		miniPanel.add(termBox, BorderLayout.SOUTH);

		addDialog.setTitle("Kill Process");
		addDialog.add(miniPanel);
		addDialog.setResizable(false);
		addDialog.pack();
		addDialog.setLocationRelativeTo(null);
		addDialog.setModal(true);
		addDialog.setAlwaysOnTop(true);
		addDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		addDialog.setVisible(true);

	}

	private void initComponents() {
		setTitle("CS 471 Dispatcher Simulator");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		panel = new JPanel(new BorderLayout());
		panel.setPreferredSize(new Dimension(550, 380));
		add(panel);

		menuBar = new JMenuBar();
		menu = new JMenu("Options");
		addNew = new JMenuItem("Add New");
		addNew.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addNewBox();
			}
		});
		readMe = new JMenuItem("Readme");
		readMe.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				readMe();
			}
		});
		unBlock = new JMenuItem("(Un)Block");
		unBlock.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				blockerBox();
			}
		});
		terminate = new JMenuItem("Terminate");
		terminate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				terminationBox();
			}
		});
		exit = new JMenuItem("Exit");
		exit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		menu.add(addNew);
		menu.add(unBlock);
		menu.add(terminate);
		menu.add(readMe);
		menu.add(exit);
		menuBar.add(menu);

		cSwitch = new JButton("Context Switch");
		cSwitch.setPreferredSize(new Dimension(150, 50));
		cSwitch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sim.contextSwitch();
				info.setText(info.getText() + "\n" + CS + (count++) + "\n"
						+ sim.getStatus());
			}
		});

		info = new JTextArea();
		info.setEditable(false);
		scroll = new JScrollPane(info, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
				JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
		scroll.setToolTipText("Output");
		info.setText(sim.getStatus());
		scroll.setPreferredSize(new Dimension(400, 300));
		panel.add(scroll, BorderLayout.EAST);
		panel.add(cSwitch, BorderLayout.WEST);
		setJMenuBar(menuBar);
		setLocation(650, 350);
		setVisible(true);
		pack();
	}

	private void readMe() {
		String readMe = "CS471 Operating Systems Summer Session Term Project\n"
				+ "Author: Matthew Redenius\n" + "UIN: 00773960\n"
				+ "Name: Dispatcher Simulator 1.0\n\n"
				+ "This program is intended to simulate a dispatcher\n"
				+ "using a priority queue system. This program has a\n"
				+ "fake priority queue, blocked list, and running\n"
				+ "process. It allows the user to add and terminate\n"
				+ "processes in the ready queue and blocked list.\n"
				+ "It also allows the user to block processss in the\n"
				+ "ready queue, and unblock processes in the blocked\n"
				+ "list\n\n"
				+ "When adding a process, the user only needs to enter\n"
				+ "the desired priority and the ID is automatically\n"
				+ "generated.\n\n"
				+ "Once all desired processes are loaded, the user can\n"
				+ "perform a context switch to load the next process in\n"
				+ "the ready queue into execution.\n"
				+ "\nIn main, there is test data provided.";
		JFrame frame = new JFrame("README");
		JPanel panel = new JPanel();
		JTextArea label = new JTextArea(readMe);
		panel.add(label);
		frame.add(panel);
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);

	}

	public static void errorMessage(String message) {
		JDialog addDialog = new JDialog();

		JPanel miniPanel = new JPanel(new BorderLayout());
		miniPanel.setPreferredSize(new Dimension(175, 100));
		JLabel mess = new JLabel(message);
		miniPanel.add(mess);
		
		addDialog.add(miniPanel);
		addDialog.setTitle("ERROR");
		addDialog.setResizable(false);
		addDialog.pack();
		addDialog.setLocationRelativeTo(null);
		addDialog.setModal(true);
		addDialog.setAlwaysOnTop(true);
		addDialog.setModalityType(ModalityType.APPLICATION_MODAL);
		addDialog.setVisible(true);

	}

}
