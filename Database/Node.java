import javax.swing.*;
import java.io.*;

class Node implements Serializable {

	public String benName;
	public String alienType;
	public String planet;
	Node next;

	Node() {
		this.benName = null;
		this.alienType = null;
		this.planet = null;
		this.next = null;
	}

	Node (Node n) {
		this.benName = n.benName;
		this.alienType = n.alienType;
		this.planet = n.planet;
		this.next = n.next;
		
	}

	void userInput () {
		this.benName = JOptionPane.showInputDialog("Name:");
		this.alienType = JOptionPane.showInputDialog("Type of Alien:");
		this.planet = JOptionPane.showInputDialog("From what planet:");
	}

	public boolean equals(Node n) {
		if (this.benName.equals(n.benName)) {
			if (this.alienType.equals(n.alienType)) {
				if (this.planet.equals(n.planet))
					return true;
				else
					return false;

			}else
				return false;
		}else
			return false;
		
	}

	public String toString() {
		return "*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n\nBen10 Alien: " + this.benName + "\nType: " + this.alienType + "\nPlanet:" + this.planet + "\n\n*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*\n";


	}
}
