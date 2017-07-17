import java.util.Scanner;
import java.util.Random;
import java.text.DecimalFormat;

public class Stack
{
	int top = -1; // Top of Stack
	Object stack[] = new Object[100]; // Creates Stack as an object 100 long

	public Object push (Object obj) // Inserting Objects into Stack
	{
		if(top >= stack.length - 1 || obj == null) // If the Stack is full, doesn't insert Object
			return (Object)null;

		top++;
		stack[top] = obj;
		return true;
	}

	public String toString () // Printing the Stack
	{
		String returner = "";

		if(top >= 0)
			returner += stack[0];

		for(int i = 1; i <= top; i++)
		{
			returner += " , " + stack[i];
		}

		return returner;
	}

	public Object pop() // Removing object from Stack
	{
		if(empty()) // If there is nothing in Stack, return nothing
			return (Object)null;

		Object obj = stack[top];
		top--;
		return stack[top + 1];
	}

	public boolean empty() // Returns true if stack is empty
	{
		return (top < 0);
	}
}
