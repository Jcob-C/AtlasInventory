package delarama;

import base.DB;
import java.sql.*;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.FileOutputStream; 
import com.itextpdf.text.Image;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import base.Main;

public class AuditTrail extends JPanel {
    private final DefaultTableModel model = new DefaultTableModel();
    private final JTextField searchField = new JTextField();
    
    public AuditTrail() {
        setLayout(null);
        setBackground(Main.getDarkColor());
        setBounds(0,0,880, 660);

        model.addColumn("ID");
        model.addColumn("Full_Name");
        model.addColumn("Activity");
        model.addColumn("Date_Time");

        JTable table = new JTable(model);
        table.getTableHeader().setReorderingAllowed(false);
        table.getColumnModel().getColumn(0).setPreferredWidth(52);
        
        JScrollPane scroll = new JScrollPane(table);
        scroll.setBounds(40, 136, 800, 458);

        JPanel panel1 = new JPanel(null);
        panel1.setBackground(Main.getLightColor());
        panel1.setBounds(0, 0, 880, 30);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Main.getLightColor());
        panel2.setBounds(0, 630, 880, 30);

        JLabel searchLabel = new JLabel("Search by ID:");
        searchLabel.setBounds(50, 49, 139, 34);
        searchLabel.setForeground(Color.WHITE);

        searchField.setBounds(132, 49, 139, 34);

        JButton searchButton = new JButton("Search");
        searchButton.setBounds(281, 50, 79, 33);
        searchButton.setBackground(Main.getMidColor());
        searchButton.setForeground(Color.WHITE);

        JButton createButton = new JButton("Create");
        createButton.setBounds(735, 51, 105, 33);
        createButton.setBackground(Main.getMidColor());
        createButton.setForeground(Color.WHITE);
        
        JButton exportButton = new JButton("Export to PDF");
        exportButton.setBounds(587,93,126,33);
        exportButton.setBackground(Main.getMidColor());
        exportButton.setForeground(Color.WHITE);
        
        JButton refreshButton = new JButton("Refresh");
        refreshButton.setBounds(735,93,105,33);
        refreshButton.setBackground(Main.getMidColor());
        refreshButton.setForeground(Color.WHITE);

        JButton backButton = new JButton("<");
        backButton.setBounds(0, 0, 45, 30);
        backButton.setBackground(Main.getMidColor());
        backButton.setForeground(Color.WHITE);
        panel1.add(backButton);

        JButton closeButton = new JButton("X");
        closeButton.setBounds(835, 0, 45, 30);
        closeButton.setBackground(Color.RED);
        closeButton.setForeground(Color.WHITE);
        panel1.add(closeButton);

        add(panel1);
        add(panel2);
        add(scroll);
        add(searchLabel);
        add(searchField);
        add(searchButton);
        add(createButton);
        add(exportButton);
        add(refreshButton);

        LoadAllActivities();

        searchButton.addActionListener(e -> {
            String input = searchField.getText().trim();
            if (input.isEmpty()) {
                LoadAllActivities();
            } else {
                searchAccount();
            }
        });
        
        createButton.addActionListener(e -> {
            Panels.allFalseVisibility();
            Panels.createPanel.setVisible(true);
        });
        
        refreshButton.addActionListener(e -> LoadAllActivities());
        
        exportButton.addActionListener(e -> exportToPDF());

        closeButton.addActionListener(e -> System.exit(0));

        backButton.addActionListener(e -> {
            Panels.allFalseVisibility();
            Panels.accountPanel.setVisible(true);
        });
    }

    private void popupWarning(String message) {
        JOptionPane.showMessageDialog(this, message, "WARNING", JOptionPane.WARNING_MESSAGE);
    }

    private void popupError(String message) {
        JOptionPane.showMessageDialog(this, message, "ERROR", JOptionPane.ERROR_MESSAGE);
    }

    public void LoadAllActivities() {
        try (Connection conn = DB.getConnection()) {
            model.setRowCount(0);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM Activity ORDER BY activity_id DESC");

            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("ID"),
                    rs.getString("Full_name"),
                    rs.getString("Activity"),
                    rs.getString("Date_Time")
                });
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    private void exportToPDF() { 
        try {
            Document document = new Document(); 
            PdfWriter.getInstance(document, new FileOutputStream("AuditReport.pdf")); 
            document.open();
            
            Image logo = Image.getInstance("src/main/resources/Atlas-Feeds-Logo-d.png");
            logo.setAbsolutePosition(365,750);
            logo.scaleToFit(204,83);
            
            document.add(logo);
            document.add(new Paragraph("Audit Trail Report")); 
            document.add(new Paragraph("Generated on: " + new java.util.Date().toString())); 
            document.add(Chunk.NEWLINE);
            
            PdfPTable pdfTable = new PdfPTable(4); 
            pdfTable.setWidths(new int[]{1, 4, 4, 2});
            pdfTable.addCell("ID");
            pdfTable.addCell("Full Name");
            pdfTable.addCell("Activity"); 
            pdfTable.addCell("Date & Time");
            
            for (int i = 0; i < model.getRowCount(); i++) {
                pdfTable.addCell(model.getValueAt(i, 0).toString());
                pdfTable.addCell(model.getValueAt(i, 1).toString()); 
                pdfTable.addCell(model.getValueAt(i, 2).toString());
                pdfTable.addCell(model.getValueAt(i, 3).toString());
            }
            
            document.add(pdfTable);
            document.close(); 
            JOptionPane.showMessageDialog(this, "PDF Report generated: AuditReport.pdf");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void searchAccount() {
        int id;

        try {
            id = Integer.parseInt(searchField.getText().trim());
        } catch (NumberFormatException e) {
            popupWarning("Invalid Input! Try to input a number instead.");
            return;
        }

        try (Connection conn = DB.getConnection()) {
            model.setRowCount(0);
            
            PreparedStatement stmt = conn.prepareStatement("SELECT * FROM Activity WHERE ID = ?");
            stmt.setInt(1, id);
            
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("ID"),
                    rs.getString("Full_name"),
                    rs.getString("Activity"),
                    rs.getString("Date_Time")
                });
            } else {
                popupError("Account not found!");
            }

            rs.close();
            stmt.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void Audit_Trail(String action) {
        try (Connection conn = DB.getConnection()) {
            
            PreparedStatement insert = conn.prepareStatement("INSERT INTO Activity (ID, Full_Name, Activity) VALUES (?, ?, ?)");

            insert.setString(1, Main.loggedInID);
            insert.setString(2, Main.userFullName);
            insert.setString(3, action);

            insert.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}