import java.util.*;
public class Calculator
{
	public final static int NUM = 0; //Instance field value in Token to indicate a number
	public final static int OPER = 1; // Instance field value in Token to indicate an operator

	public static double exec (String expr) //Accesses all operations to turn input into the answer
	{
		expr = "(" + expr + ")"; // Adds brackets at beginning and end to address corner cases
		expr = expr.replaceAll (" ", ""); //Removes all extra spaces
		expr = expr.replaceAll ( "\\(\\+", "(" ); //Removes unnecessary plus signs
		expr = expr.replaceAll ( "\\(\\-", "(0-" ); // Adds 0 to address negative numbers

		Queue infix = parse (expr); // Parses initial string into queue of Tokens in infix notation

		Queue postfix = compile (infix); //Compiles tokens in infix notation into queue of Tokens in postfix notation

		double result = compute (postfix); // Performs the operations and computes the value of the queue of Tokens

		return result;
	}

	private static Queue parse(String expr) // Parses initial string into queue of Tokens
	{
		Queue infix = new Queue(); // Creates queue that will hold Tokens in infix notation
		int index = 0; // Index in the string that is currently being parsed

		while (index < expr.length())
		{
			char ch = expr.charAt(index); // Character being parsed

			if(isOperator(ch)) // If it is an operator
			{
				Token t = new Token(OPER, 0.0, ch); //Creates token holding the operator
				infix.insert(t); // Inserts the Token into the queue
				index++;
			}

			else // If it is a number
			{
				String number = ""; //Creates String to hold the number, however many digits it may be

				while (isOperator(ch) == false) // Unitl the next operator, all characters will be a part of the number
				{
					 number = number + ch;
					 index++;
					 ch = expr.charAt(index);
				}

				double d = Double.parseDouble(number); // Converts the String to a Double
				Token t = new Token(NUM, d, 'X'); // Creates token of the number
				infix.insert(t); // Inserts Token into the queue
			}
		}

		return infix;
	}

	private static Queue compile (Queue infix)//outputs queue in postfix notation
	{
		Queue postfix = new Queue(); // Creates Queue for postfix notation
		Stack s = new Stack(); // Used to apply order of operations when changing to postfix notation

		while (!infix.empty()) // While there are still Tokens in the infix Queue
		{
			Token t = (Token)infix.remove(); // Token currently being processed

			if (t.type == NUM)
			{
				postfix.insert(t); // Numbers remain in the same order from infix to postfix notation
			}

			else // If it is an operator
			{
				if (t.operation == '(')
				{
					s.push(t); // Opening bracket has highest priority when pushed into operator Stack
				}

				else
				{
					if (t.operation == ')')
					{
						Token fromS = (Token) s.pop(); // Pop out the last token from the operator Stack

						while (fromS.operation != '(') // Keep pushing into postfix until the next opening bracket
						{
							postfix.insert (fromS);

							fromS = (Token) s.pop();
						}
						// Brackets are not in postfix notation
					}

					else // If the operator is not a bracket, it needs to be sorted into higher priority for order of operations (BEDMAS)
					{
						Token fromS = (Token) s.pop();

						if (highPriority (t, fromS)) // If the temporary Token is a higher priority than fromS
						{
							s.push(fromS);
							s.push(t); // Both are pushed back into stack (because fromS was popped earlier)
						}

						else // If the Stack had a higher priority
						{
							postfix.insert(fromS);
							s.push(t); // fromS is in queue postfix, temporary Token is in the operator Stack
						}
					}
				}
			}
		}

		return postfix;
	}

	private static double compute(Queue postfix)
	{
		Stack s = new Stack(); // Stack used to compute the value of the postfix queue of Tokens

		while(postfix.empty() == false)
		{
			Token t = (Token)postfix.remove();

			if(t.type == NUM)
			{
				s.push(t);
			}

			else
			{
				Token b = (Token) s.pop(); // Second value in operation
				Token a = (Token) s.pop(); // First value in operation
				double val = calc (a.value, b.value, t.operation); // Calculates the value of this part of the operation
				Token result = new Token (NUM, val, ' '); // Creates Token representing the result of this calculation
				s.push (result);
			}
		}

		Token finalVal = (Token) s.pop();
		return finalVal.value; //There is only one Token left in the stack after processing, and it is the final result
	}

	private static boolean isOperator(char ch) // Returns whether is is an Operator
	{
		String s = "()+-*^/=";
		return(s.indexOf(ch)>=0);
	}

	private static boolean highPriority (Token a, Token b) // Returns true if a has a higher priority
	{
		String priority = "( +-*/^"; // Shows priority of operators in reverse BEDMAS

		if(priority.indexOf(a.operation) == 6 && priority.indexOf(b.operation) == 6) // If they are both ^
			return true;

		return(priority.indexOf(a.operation) / 2 > priority.indexOf(b.operation) / 2); // Checks to see if they are the same by dividing by 2 (+ and - will have the same answer) and returns false if they are the same or b is higher
	}

	private static double calc (double a, double b, char operator) // Calculates the value of a single operation
	{
		if (operator == '+')
		{
			return (a + b);
		}
		if (operator == '-')
		{
			return (a - b);
		}
		if (operator == '*')
		{
			return (a * b);
		}
		if (operator == '/')
		{
			return (a / b);
		}

		return (Math.pow(a, b));
	}
}

class Token // A class to process numbers and operators
{
	public int type; // Type of symbol (number or operator)
	public double value; // If number, the value
	public char operation; // If operator, the operator

	Token (int t, double v, char o)
	{
		type = t;
		value = v;
		operation = o;
	}

	public String toString()
	{
		if (type == 0)
			return "" + value;

		return "" + operation;
	}
}