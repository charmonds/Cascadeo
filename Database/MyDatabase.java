import java.io.*;

class MyDatabase implements Database {
	Node head = null;
	Node rover;
	Node terminator;
	int check = 1;

	public void insert (Node myNewNode) {
		this.rover = this.head;		
		if (rover == null) { 		//If the list is empty
			this.head = myNewNode;
			this.head.next = null;
			this.check = 1;
		}else {
			while (this.rover.next != null) 
				this.rover = this.rover.next;
			this.rover.next = myNewNode;
			myNewNode.next = null;
			this.check = 1;
		}		

	}
	public boolean delete(Node nodeToDelete) {
		rover = head;
		while (rover != null) {			//Search for the node to be deleted
			if (rover.equals(nodeToDelete))
				break;
			else rover = rover.next;
		};
		if (rover != null) {
			if (rover == head) 		//If the node is the head
				head = head.next;
			else if (rover.next == null) {	//If the node is the end
				terminator = head;
				while (terminator.next != rover)
					terminator = terminator.next;
				terminator.next = null;
			}else {				//If the node is in the middle
				terminator = head;
				while (terminator.next != rover)
					terminator = terminator.next;
				terminator.next = rover.next;
			}
			this.check = 1; //Used to specify that the iterator must go back to head
			return true;
		}else 
			return false;
		
	}
	public boolean replace (Node nodeToReplace, Node myNewNode){
		rover = head;
		while (rover != null) {
			if (rover.equals(nodeToReplace))
				break;
			else rover = rover.next;
		};
		if (rover != null) {
			if (rover == head) {
				myNewNode.next = head.next;
				head = myNewNode;
				
			}else if (rover.next == null) {
				terminator = head;
				while (terminator.next != rover)
					terminator = terminator.next;
				terminator.next = myNewNode;
				myNewNode = null;
			}else {
				terminator = head;
				while (terminator.next != rover)
					terminator = terminator.next;
				myNewNode.next = rover.next;
				terminator.next = myNewNode;
			}
			this.check = 1;
			return true;
		}else 
			return false;

	}
	public Node search (Node nodeToSearch) {
		rover = head;
		while (rover != null) {
			if (rover.equals(nodeToSearch))
				break;
			else rover = rover.next;
		};
		if (rover == null)
			return null;
		else
			return new Node (rover);
	}
	public boolean saveToFile (String filename) throws Exception {
		ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(filename));
		out.writeObject(head);
		out.close();
		return true;
	}
	public boolean loadFromFile (String filename) throws Exception {
		try {
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(filename));
			head = new Node((Node) in.readObject()); //Try to see if such filename exists
			
		}catch(FileNotFoundException e) {
			saveToFile(filename); //If not, create the said file
			return false;
		}			
			
		return true;
	
	}
	public Node listIterator () {
		
		if (this.check == 1) {
			rover = head;
			if (rover != null) {
				this.check++;
				return new Node (rover);
			}else
				return null;
		}else {
			if (rover.next != null) {
				rover = rover.next;
				this.check++;
				return new Node (rover);
			}else {
				this.check = 1;
				return null;
			}
		}
			
	}
	public boolean equals (Database db) {
		int counterRover = 1;
		rover = head;
		while(rover != null) {
			rover = rover.next;
			counterRover++;
		};
		int counterProof = 1;
		Node proof = db.listIterator();
		while (proof != null) {     
			proof = db.listIterator();
			counterProof++;
		};
		if (counterRover == counterProof) { //Check if both databases have the same number of nodes
			rover = head;
			while (rover != null) {
				if (db.search(rover) == null || search(rover) == null) //check if each database has
					return false;				       //the exactly the same entries 
				rover = rover.next;
			};
			return true;
				
		}else
			return false;
	}
	public String whoIAm() {
		return "Charmond Santiago\n\t--When life gives you lemons, make lemonade!";
	}
}
