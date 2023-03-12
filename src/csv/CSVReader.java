package csv;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public static List<EmployeeProject> readFromCSV(String fileName) {
        List<EmployeeProject> employees = new ArrayList<>();
        Path pathToFile = Paths.get(fileName);

        try(BufferedReader br = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8))
        {
            String line = br.readLine();

            while(line != null)
            {

                String[] attributes = line.split(",");

                if(line.contains(";"))
                {
                    attributes = line.split(";");
                }

                if(attributes.length <= 2){
                    line = br.readLine();
                    continue;
                }

                employees.add(new EmployeeProject(attributes));

                line = br.readLine();
            }

        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
        return employees;
    }
}
