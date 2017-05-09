/**
* LLQueue.java
* @author Joseph Mbabu
* A program that implements a queue using a linked list
*
* Spring 2015
*/


public class LLPriQue<E> implements PriQue<E>{

	/**
	* Linked linked class
	*/
	private class Node{
		public Node next;
		public Node previous;
		public  E data;
		public int pri;

        // node constructor
		Node(int pri, E data){
			this.pri = pri;
			this.data = data;
		}
	}

    Node head; // first node
    Node last; // last node
    int counter; // counts items in queue

    // main constructor
    public LLPriQue(){
    	head = null;
    	last = null;
    	counter = 0;
    }

    // insert item
    public void insert(int pri, E data){
    	Node current = head;
    	Node new_node = new Node(pri, data);

          if(head == null){
          head = new_node;
          last = new_node;
          return;
       }

       // if new node equals current node
       else if(new_node.pri == current.pri ){
              return;
       }

       // if new node is greater than current node
       else if(new_node.pri < current.pri){
              head = new_node;
              current.previous = new_node;
              new_node.next = current;
              return;
       }
       else{
            while(current.next !=  null){
                   if(new_node.pri > current.next.pri){
                       current = current.next;
                     }
                   // skip duplicates
                   else if(new_node.pri == current.next.pri){
                          return;
                   }
                   else{
                       new_node.next = current.next;
                       current.next = new_node;
                       new_node.next.previous = new_node;
                       new_node. previous = current;
                       return;
                       }
            }
       }

       if(last.pri != new_node.pri){
          last.next = new_node;
          new_node.previous = last;
          last = new_node;
       }
    }


    // remove minimum from front
    public E remove(){
    	if (isEmpty()== false){
          E temp = head.data;
          Node current  = head;
      		head = current.next;
          return temp;
    	}
    	counter--;
        return null;
    }

    // check items in list
    public boolean isEmpty(){
    	if(head  == null){
    		return true;
    	}
    	else
    		return false;
    }

  // peek at element in head
  public E peek(){
    if(isEmpty())
      return null;
    else
      return head.data;
  }
}
