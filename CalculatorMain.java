import java.util.*;

public class CalculatorMain
{
	public static Scanner kbd = new Scanner(System.in);

	public static void main(String[] args)
	{
		// My tests to prove it works
		String calc = "2 + 3";
		System.out.println ( calc + " = " + Calculator.exec(calc));
		calc = "2 + 4 * 3 (-2)";
		System.out.println ( calc + " = " + Calculator.exec(calc));
		calc = "(2 + 4) * 3 ^2";
		System.out.println ( calc + " = " + Calculator.exec(calc));
		calc = "21.234 + 4 * 3^ (-2+ 2*4) / 3 ";
		System.out.println ( calc + " = " + Calculator.exec(calc));

		// So you can test it yourself
		calc = kbd.nextLine();
		while(!calc.equals("END"))
		{
			System.out.println("= " + Calculator.exec(calc));
			calc = kbd.nextLine();
		}
	}
}
