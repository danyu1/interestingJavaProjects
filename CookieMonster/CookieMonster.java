import java.io.File;
import java.util.Scanner;
import java.util.ArrayDeque;
import java.util.PriorityQueue;
/* You are allowed (and expected!) to use either Java's ArrayDeque or LinkedList class to make stacks and queues,
 * and Java's PriorityQueue class to make a priority queue */

public class CookieMonster {

    private int [][] cookieGrid;
    private int numRows;
    private int numCols;
    
    //Constructs a CookieMonster from a file with format:
    //numRows numCols
    //<<rest of the grid, with spaces in between the numbers>>
    public CookieMonster(String fileName) {
		int row = 0;
		int col = 0;
		try
		{
			Scanner input = new Scanner(new File(fileName));

			numRows    = input.nextInt();  
			numCols    = input.nextInt();
			cookieGrid = new int[numRows][numCols];

			for (row = 0; row < numRows; row++) 
				for (col = 0; col < numCols; col++)
					cookieGrid[row][col] = input.nextInt();
			
			input.close();
		}
		catch (Exception e)
		{
			System.out.print("Error creating maze: " + e.toString());
			System.out.println("Error occurred at row: " + row + ", col: " + col);
		}

    }

    public CookieMonster(int [][] cookieGrid) {
        this.cookieGrid = cookieGrid;
        this.numRows    = cookieGrid.length;
        this.numCols    = cookieGrid[0].length;
    }

	/* RECURSIVELY calculates the route which grants the most cookies.
	 * Returns the maximum number of cookies attainable. */
	public int recursiveCookies() {
		//cookiegrid [ypos][xpos]
		//in order to make this a little bit less memory intensive create a increase capacity tester
		int [] differentCookies = new int [10];
		//method that finds the max and returns it
		return max(recursiveCookies (0, 0, 0, differentCookies, 0));
	}	

	/* Calculate which route grants the most cookies using a QUEUE.
	 * Returns the maximum number of cookies attainable. */
    /* From any given position, always add the path right before adding the path down */
	
	// it searches the grid by visiting all the positions in the next path before visiting the positions in the same path
	public int queueCookies() 
	{
		//queue of OrphanScout objects
	    ArrayDeque<OrphanScout> queue = new ArrayDeque<>();
	    int maxCookies = 0;
	    //OrphanScout object at the top left
	    OrphanScout start = new OrphanScout(0, 0, cookieGrid[0][0]);
	    queue.add(start);
	    while (!queue.isEmpty()) 
	    {
	    	//remove the OrphanScout object at the front of the queue because there is a new path
	        OrphanScout curr = queue.remove();
	        //coords of the OrphanScout
	        int xpos = curr.getEndingCol();
	        int ypos = curr.getEndingRow();
	        int cookies = curr.getCookiesDiscovered();
	      //dead end of the path created
	        if (!canGoRight(xpos, ypos) && !canGoDown(xpos, ypos)) 
	        {
	        	//if cookies of current object position is greater than maxCookies
	            if (cookies > maxCookies) 
	            {
	                maxCookies = cookies;
	            }
	        }
	        //can move right at current position
	        if (canGoRight(xpos, ypos)) 
	        {
	            OrphanScout newPos = new OrphanScout(ypos, xpos + 1, cookies + cookieGrid[ypos][xpos + 1]);
	            //if the cookies at the newPos are greater than that of the curr position then add new position to the queue
	            //if (newPos.compareTo(curr) > 0)
	                queue.add(newPos);
	        }
	        //can go down at current position 
	        if (canGoDown(xpos, ypos)) 
	        {
	            OrphanScout newPos = new OrphanScout(ypos + 1, xpos, cookies + cookieGrid[ypos + 1][xpos]);
	            //if the cookies at the newPos are greater than that of the curr position then add newpos to the queue
	            //if (newPos.compareTo(curr) > 0)
	                queue.add(newPos);
	        }
	    }
	    return maxCookies;
	}

    /* Calculate which route grants the most cookies using a stack.
 	 * Returns the maximum number of cookies attainable. */
    /* From any given position, always add the path right before adding the path down */
	
	//visits the next position in the same path before visiting the positions in the next path
	public int stackCookies() 
	{
		//stack of Orphan Scouts
	    ArrayDeque<OrphanScout> stack = new ArrayDeque<>();
	    int maxCookies = 0;
	    //object representing the starting position at the top left
	    OrphanScout start = new OrphanScout(0, 0, cookieGrid[0][0]);
	    stack.push(start);
	    while (!stack.isEmpty()) 
	    {
	        OrphanScout curr = stack.pop();
	        //coords of the current object
	        int xpos = curr.getEndingCol();
	        int ypos = curr.getEndingRow();
	        int cookies = curr.getCookiesDiscovered();
	        //if the OrphanScout's path is at a dead end
	        if (!canGoRight(xpos, ypos) && !canGoDown(xpos, ypos)) 
	        {
	            if (cookies > maxCookies) 
	            {
	                maxCookies = cookies;
	            }
	        }
	        //if the object can go right at the current position
	        if (canGoRight(xpos, ypos)) 
	        {
	            OrphanScout newPos = new OrphanScout(ypos, xpos + 1, cookies + cookieGrid[ypos][xpos + 1]);
	            //finds the path faster
	            //if (newPos.compareTo(curr) > 0)
	                stack.push(newPos);
	        }
	        //if the object can go down at the current position
	        if (canGoDown(xpos, ypos)) 
	        {
	            OrphanScout newPos = new OrphanScout(ypos + 1, xpos, cookies + cookieGrid[ypos + 1][xpos]);
	            //finds the path faster
	            //if (newPos.compareTo(curr) > 0)
	                stack.push(newPos);
	        }
	    }
	    return maxCookies;
	}
    /* Calculate which route grants the most cookies using a priority queue.
	 * Returns the maximum number of cookies attainable. */
    /* From any given position, always add the path right before adding the path down */
	//explores the grid by visiting the position with the highest number of cookies discovered so far
	public int pqCookies()
	{
		//pq = heap
	    PriorityQueue<OrphanScout> pq = new PriorityQueue<>();
	    int maxCookies = 0;
	    //top left of the 2d array
	    OrphanScout start = new OrphanScout(0, 0, cookieGrid[0][0]);
	    pq.offer(start);
	    while (!pq.isEmpty()) 
	    {
	    	//remove the object with min priority that is considered the current path
	        OrphanScout curr = pq.poll();
	        //coords of the object
	        int xpos = curr.getEndingCol();
	        int ypos = curr.getEndingRow();
	        int cookies = curr.getCookiesDiscovered();
	        //the path is at a dead end
	        if (!canGoRight(xpos, ypos) && !canGoDown(xpos, ypos)) 
	        {
	            if (cookies > maxCookies) 
	            {
	                maxCookies = cookies;
	            }
	        }
	        //can go right at the current position
	        if (canGoRight(xpos, ypos)) 
	        {
	            OrphanScout newPos = new OrphanScout(ypos, xpos + 1, cookies + cookieGrid[ypos][xpos + 1]);
	            //if (newPos.compareTo(curr) > 0)
	                pq.add(newPos);
	        }
	        //can go down at the current position
	        if (canGoDown(xpos, ypos)) 
	        {
	            OrphanScout newPos = new OrphanScout(ypos + 1, xpos, cookies + cookieGrid[ypos + 1][xpos]);
	            //if (newPos.compareTo(curr) > 0)
	                pq.add(newPos);
	        }
	    }
	    return maxCookies;
	}


    //maybe make this return an array?
    public int [] recursiveCookies (int xpos, int ypos, int totalCookies, int [] differentCookies, int index)
    {
    	if (index >= differentCookies.length-1)
    	{
    		differentCookies = increaseCapacity (differentCookies);
    	}
    	//cookieGrid [ypos][xpos]
    	//helpermethods: canGoDown bool, canGoRight
    	index = findIndex (differentCookies);
    	if (!canGoDown (xpos, ypos) && !canGoRight (xpos, ypos))
    	{
    		totalCookies += cookieGrid [ypos][xpos];
    		if (index >= differentCookies.length-1)
        	{
        		differentCookies = increaseCapacity (differentCookies);
        	}
    		differentCookies [index] = totalCookies;
    		return differentCookies;
    	}
    	totalCookies += cookieGrid [ypos][xpos];
    	index = findIndex (differentCookies);
    	//pseudo code, if canGoDown and canGoRight call recursive cookies with x updated and one with the y updated
    	//if canGoDown but !canGoRight call recursive cookies  with only the y updated
    	//if !canGoDown but canGoRight call recursive coolies with only the x updated
    	if (canGoDown (xpos, ypos) && canGoRight (xpos, ypos))
    	{
    		xpos++;
    		differentCookies = recursiveCookies (xpos, ypos, totalCookies, differentCookies, index);
    		xpos--;
    		ypos++;
    		differentCookies = recursiveCookies (xpos, ypos, totalCookies, differentCookies, index);
    	}
    	else if (canGoDown (xpos, ypos) && !canGoRight (xpos, ypos))
    	{
    		ypos++;
    		differentCookies = recursiveCookies (xpos, ypos, totalCookies, differentCookies, index);
    	}
    	else if (!canGoDown (xpos, ypos) && canGoRight (xpos, ypos))
    	{
    		xpos++;
    		differentCookies = recursiveCookies (xpos, ypos, totalCookies, differentCookies, index);
    	}
    	return differentCookies;
    }
    //if object can go down at current position
    public boolean canGoDown(int xpos, int ypos)
    {
    	if (ypos == cookieGrid.length - 1)
    	{
    		return false;
    	}
    	if (cookieGrid[ypos+1][xpos] == -1)
    	{
    		return false;
    	}
    	return true;
    }
    //if object can go right at current position
    public boolean canGoRight(int xpos, int ypos)
    {
    	if (xpos == cookieGrid[0].length - 1)
    	{
    		return false;
    	}
    	if (cookieGrid[ypos][xpos+1] == -1)
    	{
    		return false;
    	}
    	return true;
    }
    //finds the max int value in an array
    public int max(int[] differentCookies) {
	    int max = 0;
	    for (int i = 0; i < differentCookies.length; i++) {
	        if (differentCookies[i] > max) {
	            max = differentCookies[i];
	        }
	    }
	    return max;
    }
    //finds the index of an array where the first zero appears
    public int findIndex (int [] arr)
    {
    	int index = 0;
    	for (int i = 0; i < arr.length; i++)
    	{
    		if (arr[i] != 0)
    		{
    			index++;
    		}
    		else
    		{
    			break;
    		}
    	}
    	return index;
    }
    public int [][] getGrid ()
    {
    	return this.cookieGrid;
    }
    public static int [] increaseCapacity (int [] arr)
    {
    	int [] newarry = new int [arr.length*2];
    	for (int i = 0; i < arr.length; i++)
    	{
    		newarry[i] = arr[i];
    	}
    	arr = newarry;
    	return arr;
    }
    
}


