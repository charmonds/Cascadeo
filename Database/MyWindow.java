import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MyWindow extends JFrame implements ActionListener {
	MyDatabase db;
        Container c;
	JButton b1, b2, b3, b4, b5, b6, b7;
	JLabel label1, label2, label3;
	JTextField field1, field2, field3;
        JLabel Alien = new JLabel();
        JLabel error1 = new JLabel();
        TextArea Info = new TextArea("/* Welcome to the Ben10 Database */\n\n", 4, 30, TextArea.SCROLLBARS_VERTICAL_ONLY);

	public MyWindow() {
                db = new MyDatabase();

		this.setSize(1090,720);
		this.setTitle("Ben10: Alien Database");
		this.setLocation(0,0);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                this.setResizable(false);
		c = this.getContentPane();
		c.setLayout(new BorderLayout());

                
                b1 = new JButton();
                b1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Insertdefault.gif"))); 
                b1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                b1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Insertpushed.gif")));
                b1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Inserthover.gif")));
                b1.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Inserthover.gif")));

                b2 = new JButton();
                b2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Deletedefault.gif")));
                b2.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                b2.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Deletepushed.gif")));
                b2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Deletethover.gif")));
                b2.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Deletethover.gif")));

		b3 = new JButton();
                b3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Replacedefault.gif")));
                b3.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                b3.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Replacepushed.gif")));
                b3.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Replacehover.gif")));
                b3.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Replacehover.gif")));
                
                b4 = new JButton();
                b4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Searchdefault.gif")));
                b4.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                b4.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Searchpushed.gif")));
                b4.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Searchhover.gif")));
                b4.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Searchhover.gif")));

		b5 = new JButton();
                b5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Browsedefault.gif")));
                b5.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
                b5.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Browsepushed.gif")));
                b5.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/Browsehover.gif")));
                b5.setRolloverSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/Browsehover.gif")));

		b6 = new JButton();
                b6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Savedefault.gif")));
                b6.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                b6.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Savepushed.gif")));

                b7 = new JButton();
                b7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Loaddefault.gif")));
                b7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
                b7.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/Loadpushed.gif")));
                
		label1 = new JLabel("Name");
                label1.setFont(new java.awt.Font("Broadway", 0, 40));
		field1 = new JTextField(12);
                field1.setFont(new java.awt.Font("Arial", 0, 36));
                field1.setSize(367, 70);

                label2 = new JLabel("Type of Alien");
                label2.setFont(new java.awt.Font("Broadway", 0, 40));
		field2 = new JTextField(12);
                field2.setFont(new java.awt.Font("Arial", 0, 36));
                field2.setSize(367, 70);

                label3 = new JLabel("Planet");
                label3.setFont(new java.awt.Font("Broadway", 0, 40));
		field3 = new JTextField(12);
                field3.setFont(new java.awt.Font("Arial", 0, 36));
                field3.setSize(367, 70);

		b1.addActionListener(this);
		b2.addActionListener(this);
		b3.addActionListener(this);
                b4.addActionListener(this);
                b5.addActionListener(this);
                b6.addActionListener(this);
                b7.addActionListener(this);

                b1.setLocation(0, 0);
                b1.setSize(306, 97);
                b2.setLocation(0, 98);
                b2.setSize(306, 97);
                b3.setLocation(0, 196);
                b3.setSize(306, 97);
                
                Info.setEditable(false);
                Info.setSize(306, 400);
                Info.setLocation(0, 293);
                Info.setBackground(new java.awt.Color(0, 0, 0));
                Info.setForeground(new java.awt.Color(153, 255, 0));
                
                c.add(b1, BorderLayout.WEST);
                c.add(b2, BorderLayout.WEST);
                c.add(b3, BorderLayout.WEST);
                c.add(Info, BorderLayout.WEST);
                JPanel Image = new JPanel();
                Image.setLocation(0, 0);
                Image.setSize(306, 720);
                Image.setBackground(Color.black);
                JPanel Imaging = new JPanel();
                
		c.add(Image, BorderLayout.WEST);
                c.add(Imaging, BorderLayout.WEST);

                b4.setLocation(778, 0);
                b4.setSize(306, 97);
                b5.setLocation(778, 98);
                b5.setSize(306, 97);
                
                error1.setLocation(778, 194);
                error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NoError.gif")));
                error1.setSize(306, 161);

		
                Alien.setLocation(778, 355);
                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ben10.gif")));
                Alien.setSize(306, 362);


                JLabel Imagex = new JLabel();

                c.add(b4, BorderLayout.EAST);
                c.add(b5, BorderLayout.EAST);
                c.add(error1, BorderLayout.EAST);
                c.add(Alien, BorderLayout.EAST);
                c.add(Imagex, BorderLayout.EAST);
                

                JPanel s = new JPanel ();
                s.setLayout(new BorderLayout());
                    b6.setLocation(306, 628);
                    b6.setSize(238, 64);
                    b7.setLocation(540, 628);
                    b7.setSize(238, 64);
                    
                    
                s.add(b6, BorderLayout.SOUTH);
                s.add(b7, BorderLayout.SOUTH);
                s.add(Imagex, BorderLayout.SOUTH);
                
               
                    JPanel InputField = new JPanel();
                    InputField.setLayout(new GridLayout(10,1));
                        JPanel InputField1 = new JPanel();
                        InputField1.setLayout(new FlowLayout());
                            label1.setLocation(0, 0);
                            label1.setBackground(Color.black);
                        InputField1.add(label1);
                        

                        JPanel InputField2 = new JPanel();
                        InputField2.setLayout(new FlowLayout());
                            field1.setLocation(306,97);
                        InputField2.add(field1);

                        JPanel InputField3 = new JPanel();
                        InputField3.setLayout(new FlowLayout());
                            label2.setLocation(0, 0);

                        InputField3.add(label2);

                        JPanel InputField4 = new JPanel();
                        InputField4.setLayout(new FlowLayout());
                            field2.setLocation(306,97);
                        InputField4.add(field2);

                        JPanel InputField5= new JPanel();
                        InputField5.setLayout(new FlowLayout());
                            label3.setLocation(0, 0);

                        InputField5.add(label3);

                        JPanel InputField6 = new JPanel();
                        InputField6.setLayout(new FlowLayout());
                            field3.setLocation(306,97);
                        InputField6.add(field3);

                    InputField.add(Imagex);
                    InputField.add(InputField1);
                    InputField.add(InputField2);
                    InputField.add(InputField3);
                    InputField.add(InputField4);
                    InputField.add(InputField5);
                    InputField.add(InputField6);
                    
                
                s.add(InputField, BorderLayout.CENTER);
                c.add(s, BorderLayout.CENTER);
		
	}

	public void actionPerformed(ActionEvent e) {
		Object bname = e.getSource();

		if (bname == b1) {
                        Node newNode = new Node();

			newNode.benName = field1.getText();
                        newNode.alienType = field2.getText();
                        newNode.planet = field3.getText();
			
                        db.insert(newNode);

                        error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NoError.gif")));
                        error1.setSize(306, 161);

                        Info.setEditable(true);
                        Info.append("You have successfully inserted " + newNode.benName + " in the Omnitrix\n");
                        Info.setEditable(false);
                        
                        field1.setText("");field2.setText("");field3.setText("");

		} else if (bname == b2) {
                        Node nodeToDelete = new Node();

			nodeToDelete.benName = field1.getText();
                        nodeToDelete.alienType = field2.getText();
                        nodeToDelete.planet = field3.getText();

                        if (db.delete(nodeToDelete)) {
                            error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NoError.gif")));
                            error1.setSize(306, 161);

                            Info.setEditable(true);
                            Info.append("You have successfully deleted " + nodeToDelete.benName + " in the Omnitrix\n");
                            Info.setEditable(false);
                        }else {
                            error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/WithError.gif")));
                            error1.setSize(306, 161);

                            Info.setEditable(true);
                            Info.append("An error has occurred while deleting\n\t" + nodeToDelete.benName + " is not in the Omnitrix\n");
                            Info.setEditable(false);

                            Alien.setLocation(778, 355);
                            Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ben10.gif")));
                            Alien.setSize(306, 362);
                        }
			field1.setText("");field2.setText("");field3.setText("");

		} else if (bname == b3) {
                        Node nodeToReplace = new Node(); //Color cc = JColorChooser.showDialog(null, "", Color.MAGENTA);
                        
                        nodeToReplace.benName = field1.getText();
                        nodeToReplace.alienType = field2.getText();
                        nodeToReplace.planet = field3.getText();
                        
                        field1.setText("");field2.setText("");field3.setText("");
                        
                        error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Processing.gif")));
                        error1.setSize(306, 161);

                        Node myNewNode = new Node();

                        myNewNode.benName = JOptionPane.showInputDialog(null, "Enter alien's name", "New Alien", JOptionPane.PLAIN_MESSAGE);
                        myNewNode.alienType = JOptionPane.showInputDialog(null, "Enter alien's type", "New Alien", JOptionPane.PLAIN_MESSAGE);
                        myNewNode.planet = JOptionPane.showInputDialog(null, "Enter alien's planet", "New Alien", JOptionPane.PLAIN_MESSAGE);

                        if (db.replace(nodeToReplace, myNewNode)) {
                            error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NoError.gif")));
                            error1.setSize(306, 161);
                            
                            Info.setEditable(true);
                            Info.append("You have successfully replaced " + nodeToReplace.benName + " by " + myNewNode.benName + "\n");
                            Info.setEditable(false);
                        }else {
                            error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/WithError.gif")));
                            error1.setSize(306, 161);

                            Info.setEditable(true);
                            Info.append("An error has occurred while replacing\n\t" + nodeToReplace.benName + " is not in the Omnitrix\n");
                            Info.setEditable(false);

                            Alien.setLocation(778, 355);
                            Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ben10.gif")));
                            Alien.setSize(306, 362);

                            //Kulang pa ng para sa pictures
                        }
                        field1.setText("");field2.setText("");field3.setText("");

		} else if (bname == b4) {
                        Node nodeToSearch = new Node();
                        
                        nodeToSearch.benName = field1.getText();
                        nodeToSearch.alienType = field2.getText();
                        nodeToSearch.planet = field3.getText();
                                            
                        if (db.search(nodeToSearch) != null) {
                            error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NoError.gif")));
                            error1.setSize(306, 161);

                            Info.setEditable(true);
                            Info.append(db.search(nodeToSearch) + "\n");
                            Info.setEditable(false);

                            if (nodeToSearch.benName.equals("four arms")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/FourArms.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("alien x")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/AlienX.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("big chill")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/BigChill.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("brainstorm")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Brainstorm.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("cannonbolt")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Cannonbolt.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("chromastone")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Chromastone.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("diamondhead")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Diamondhead.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("ditto")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ditto.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("echo echo")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EchoEcho.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("eye guy")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/EyeGuy.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("ghost freak")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ghostfreak.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("goop")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Goop.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("grey matter")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/GreyMatter.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("heatblast")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Heatblast.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("humungousaur")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Humungousaur.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("loadstar")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Loadstar.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("jetray")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Jetray.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("ripjaws")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ripjaws.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("spider monkey")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SpiderMonkey.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("stinkfly")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Stinkfly.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("swampfire")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Swampfire.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("upchuck")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Upchuck.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("upgrade")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Upgrade.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("way big")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/WayBig.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("wildmutt")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Wildmutt.gif")));
                                Alien.setSize(306, 362);
                            }else if (nodeToSearch.benName.equals("wildvine")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Wildvine.gif")));
                                Alien.setSize(306, 362);
                            } else if (nodeToSearch.benName.equals("XLR8")) {
                                Alien.setLocation(778, 355);
                                Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/XLR8.gif")));
                                Alien.setSize(306, 362);
                            }
                        }else {
                            error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/WithError.gif")));
                            error1.setSize(306, 161);

                            Info.setEditable(true);
                            Info.append("An error has occurred while searching\n\t" + nodeToSearch.benName + " is not in the Omnitrix\n");
                            Info.setEditable(false);

                            Alien.setLocation(778, 355);
                            Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ben10.gif")));
                            Alien.setSize(306, 362);
                        }
                        field1.setText("");field2.setText("");field3.setText("");

		} else if (bname == b5) {
			Node view = db.listIterator();
                        String list = "";
                        
                        while(view != null) {
                            list = list + view;
                            view = db.listIterator();
                        }
                        Info.setEditable(true);
                        Info.append(list + "\n");
                        Info.setEditable(false);

                        error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NoError.gif")));
                        error1.setSize(306, 161);

                        Alien.setLocation(778, 355);
                        Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ben10.gif")));
                        Alien.setSize(306, 362);

		} else if (bname == b6) {
			boolean isSaveSuccessful = true;
                        
                        try {
                            isSaveSuccessful = db.saveToFile("sample.dat");
                            error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NoError.gif")));
                            error1.setSize(306, 161);

                            Info.setEditable(true);
                            Info.append("Saving Complete\n");
                            Info.setEditable(false);
                        } catch (Exception ex) {
                            error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/WithError.gif")));
                            error1.setSize(306, 161);

                            Info.setEditable(true);
                            Info.append("ERROR: File was not saved successfully!\n");
                            Info.setEditable(false);

                            Alien.setLocation(778, 355);
                            Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ben10.gif")));
                            Alien.setSize(306, 362);
                           
                           isSaveSuccessful = false;
                        }
                        

		} else if (bname == b7) {
			try {
                            db.loadFromFile("sample.dat");
                            error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/NoError.gif")));
                            error1.setSize(306, 161);

                            Info.setEditable(true);
                            Info.append("Database was successfully loaded\n");
                            Info.setEditable(false);
                        }catch (Exception ex){
                            error1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/WithError.gif")));
                            error1.setSize(306, 161);
                            
                            Info.setEditable(true);
                            Info.append("ERROR: End of File Reached!\n");
                            Info.setEditable(false);

                            Alien.setLocation(778, 355);
                            Alien.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Ben10.gif")));
                            Alien.setSize(306, 362);
                        }
		}
	}

}