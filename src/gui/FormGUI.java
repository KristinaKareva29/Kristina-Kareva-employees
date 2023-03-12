package gui;

import csv.CSVReader;
import csv.EmployeeProject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class FormGUI  implements ActionListener {

    private JFrame frame;
    private JTable table;
    private JPanel panel;
    private JButton filePicker;
    private JFileChooser fileChooser = new JFileChooser();
    private Font font = new Font("Arial", Font.PLAIN, 14);

    public FormGUI() {
        frame = new JFrame("Sirma Task");
        frame.setSize(640,480);
        frame.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                super.windowOpened(e);
                int usr = JOptionPane.showConfirmDialog(frame,"Do you want to quit ?", "Exiting", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if(usr == 0) {
                    System.exit(0);
                }
            }
        });

        frame.setLayout(null);

        panel = new JPanel();
        panel.setSize(640,480);
        panel.setLocation(20,20);
        frame.add(panel);

        frame.setVisible(true);
        panel.setLayout(null);

        filePicker = new JButton("Choose File");
        filePicker.setBounds(0,0,120,40);
        filePicker.setFont(font);
        filePicker.addActionListener(this);

        panel.add(filePicker);

    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        int result = fileChooser.showOpenDialog(null);

        if(result == JFileChooser.APPROVE_OPTION)
        {
            java.util.List<EmployeeProject> employees = CSVReader.readFromCSV(fileChooser.getSelectedFile().getAbsolutePath());
            List<EmployeeProject> equalProjects = new ArrayList<>();

            for (int i = 0 ; i< employees.size(); i++)
            {

                if(i == employees.size())
                {
                    break;
                }

                for (int j = i+1; j<employees.size();j++)
                {
                    if(employees.get(i).getProjectID() == employees.get(j).getProjectID())
                    {
                        if(!equalProjects.contains(employees.get(i)))
                        {
                            equalProjects.add(employees.get(i));
                        }

                        if(!equalProjects.contains(employees.get(j)))
                        {
                            equalProjects.add(employees.get(j));
                        }

                    }

                }
            }

            this.initDataGrid(equalProjects);
        }
    }

    private void initDataGrid(List<EmployeeProject> equalProjects) {
        String[] headers = {"EmployeeID #1", "EmployeeID #2", "ProjectID","Days"};

        Map<Integer, List<EmployeeProject>> data = equalProjects.stream().collect(Collectors.groupingBy(e->e.getProjectID()));

        String[][] tableData = new String[data.size()][];

        int index = 0;

        for (Map.Entry<Integer, List<EmployeeProject>> entry : data.entrySet()) {

            EmployeeProject employee1 = entry.getValue().get(0);
            EmployeeProject employee2 = entry.getValue().get(1);

            long daysOnProject = employee1.dateDifference() + employee2.dateDifference();

                tableData[index] = new String[]{
                        String.valueOf(employee1.getEmpID()),
                        String.valueOf(employee2.getEmpID()),
                        String.valueOf(entry.getKey()),
                        String.valueOf(daysOnProject)
                };

                index++;
        }


        this.table = new JTable(tableData,headers);
        this.table.setBounds(0,50,350,100);

        JScrollPane sp = new JScrollPane(this.table);
        this.panel.add(this.table);

        this.panel.revalidate();
        this.panel.repaint();
    }


}
