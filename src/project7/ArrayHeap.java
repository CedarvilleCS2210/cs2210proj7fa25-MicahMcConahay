package project7;

/**
 * Title:        Project #7
 * Description:
 * Copyright:    Copyright (c) 2001
 * Company:
 * @author
 * @version 1.0
 */

public class ArrayHeap extends ArrayBinaryTree implements Heap {

    Comparator heapComp;

    public ArrayHeap(Comparator newComp) {
        this (newComp, DEFAULT_SIZE);
    }

    public ArrayHeap(Comparator newComp, int newSize) {
        super (newSize);
        heapComp = newComp;
    }


  public void add(Object newKey, Object newElement) throws InvalidObjectException {
      // TODO: add code here
	  // Code has been added
	  
	  //Preprocessing: ensure key matches object type, expand array if needed
	  //Processing: insert object, increase size
	  //Postprocessing: bubble up

	  //Preprocessing:
	  if (!heapComp.isComparable(newKey)) {
          throw new InvalidObjectException("Key is not comparable by this heap's comparator");
      }
      if (size == btArray.length) {
          expandArray();
      }
	  
	  //Processing:
	  Item newItem = new Item(newKey, newElement);
	  btArray[size] = new ArrayPosition(size, newItem);
	  size++;

	  //Postprocessing:
	  bubbleUp(size - 1);
  }

  public Object removeRoot() throws EmptyHeapException {
      // TODO: add code here
	  // Code has been added

	  // Preprocessing: Check for empty array and save root value
	  // Processing: Move the last node to the root and remove the last position
	  // Postprocessing: Decrease size, bubble down

	  //Preprocessing:
	  if (size == 0) {
		  throw new EmptyHeapException("Heap is empty");
	  }
	  Item rootItem = (Item) btArray[0].element();

	  //Processing:
	  btArray[0].setElement(btArray[size - 1].element());
	  btArray[size - 1] = null;

	  //Postprocessing:
	  size--;
	  if (size > 0) {
		  bubbleDown(0);
	  }
	  return rootItem;
  }
	  private void bubbleUp(int index) {
          int current = index;

          while (current > 0) {
              int parentIndex = (current - 1) / 2;

              Item currItem = (Item) btArray[current].element();
              Item parentItem = (Item) btArray[parentIndex].element();

              try {
                  if (heapComp.isLessThanOrEqualTo(parentItem.key(), currItem.key())) {
                      return;
                  }
              } catch (InvalidObjectException e) {
                  throw e;
              }

              swap(current, parentIndex);
              current = parentIndex;
          }
      }

	  private void bubbleDown(int index) {
          int current = index;

          while (true) {
              int left  = current * 2 + 1;
              int right = current * 2 + 2;

              if (left >= size) break;

              int smaller = left;

              if (right < size) {
                  Item leftItem  = (Item) btArray[left].element();
                  Item rightItem = (Item) btArray[right].element();

                  try {
                      if (heapComp.isLessThan(rightItem.key(), leftItem.key())) {
                          smaller = right;
                      }
                  } catch (InvalidObjectException e) {
                      throw e;
                  }
              }

              Item currentItem = (Item) btArray[current].element();
              Item childItem   = (Item) btArray[smaller].element();

              try {
                  if (heapComp.isLessThanOrEqualTo(currentItem.key(), childItem.key())) {
                      break;
                  }
              } catch (InvalidObjectException e) {
                  throw e;
              }

              swap(current, smaller);
              current = smaller;
          }
      }

        // you may want to expand main; it is just provided as a sample
    public static void main (String[] args) {
	    Comparator myComp = new IntegerComparator();
        Heap myHeap = new ArrayHeap (myComp, 8);

        myHeap.add(new Integer(14),new Integer(14));
        myHeap.add(new Integer(17),new Integer(17));
        myHeap.add(new Integer(3),new Integer(3));
        myHeap.add(new Integer(2),new Integer(21));
        myHeap.add(new Integer(8),new Integer(8));
        myHeap.add(new Integer(7),new Integer(18));
        myHeap.add(new Integer(1),new Integer(1));
        myHeap.add(new Integer(19),new Integer(11));
        myHeap.add(new Integer(17),new Integer(17));
        myHeap.add(new Integer(25),new Integer(6));

        System.out.println(myHeap.size());
        while (!myHeap.isEmpty()) {

            Item removedItem = (Item) myHeap.removeRoot();
            System.out.print("Key:   " + removedItem.key() + "     ");
            System.out.println("Removed " + removedItem.element());
        }
        System.out.println("All nodes removed");
    }
}
