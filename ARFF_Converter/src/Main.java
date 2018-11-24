import java.io.*;
import java.util.*;

public final class Main {
	//First argument is expected to be the attribute descriptor file
	//Second argument argument is the data file
	//Third argument is the output file
	public static final void main(final String[] args)throws Exception {
		PrintWriter output = new PrintWriter(new FileWriter(args[2]));
		BufferedReader attributeDescriptors = new BufferedReader(new FileReader(args[0]));

		output.println("@RELATION ml_term");
		output.println();

		String line = attributeDescriptors.readLine();

		while(line != null) {
			Scanner ln = new Scanner(line);
			line = attributeDescriptors.readLine();

			String name = ln.next();
			name = name.substring(0, name.length()-1);

			if(line == null){
				output.printf("@attribute\t%s\t%s%n", name, "numeric");
			}
			else{
				output.printf("@attribute	class	{0,1}");
			}
			
		}

		attributeDescriptors.close();

		output.println();
		output.println("@data");

		BufferedReader data = new BufferedReader(new FileReader(args[1]));

		line = data.readLine();

		while(line != null) {
			output.println(line.replaceAll("\\s+", ",").replace("?", "NaN").replaceAll(",$", ""));
			line = data.readLine();
		}

		data.close();

		output.close();	
	}
}
