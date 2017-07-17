public class Queue
{
	private Object [] queue = new Object[100]; // Creates a queue
	private int head = 0; // Top of queue
	private int tail = 0; // Bottom of queue

	public Object insert(Object obj) // Adding an object to queue
	{
		if (queue[tail] == null) // If queue is not full
		{
			queue[tail] = obj;
			tail= (tail+1)%queue.length;
			return obj;
		}

		return null;
	}

	public Object remove() // Removing object from queue
	{
		if (empty()) // If queue is empty, return nothing
		{
			return null;
		}

		Object ret = queue[head];
		queue[head]=null;
		head=(head+1)%queue.length;
		return ret;
	}

	public Object peek() // Look at object at top of queue
	{
		return queue[head];
	}

	public boolean empty() // Return true if queue is empty
	{
		return (queue[head]==null);
	}

	public String toString() // Printing queue
	{
		String str = "Queue: [ ";

		if (!empty())
		{
			str = str+queue[head];
			int index = (head +1)%queue.length;

			while(index!=tail)
			{
				str = str+" , " + queue[index];
				index=(index+1)%queue.length;
			}
		}

		return str +" ]";
	}
}