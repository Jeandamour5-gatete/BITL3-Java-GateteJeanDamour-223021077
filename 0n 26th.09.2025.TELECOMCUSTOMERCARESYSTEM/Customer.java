package telecom.system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class CustomerPanel extends JPanel implements ActionListener {
	JTextField idTxt = new JTextField();
	JTextField nameTxt = new JTextField();
	JTextArea descriptionTxt = new JTextArea();
	JTextField provinceTxt = new JTextField();
	JTextField districtTxt = new JTextField();
	JButton addBtn = new JButton("Add");
	JButton updateBtn = new JButton("Update");
	JButton deleteBtn = new JButton("Delete");
	JButton loadBtn = new JButton("Load");
	JTable table;
	DefaultTableModel model;
	public CustomerPanel() {
		setLayout(null);

		String[] labels = {"Customer_ID", "Customer_Name", "Description", "Province", "District", "CreatedAt"};
		model = new DefaultTableModel(labels, 0);
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(20, 300, 900, 300);

		int y = 20;
		addField("Customer_ID", idTxt, y); idTxt.setEditable(false); y+=30;
		addField("Customer_Name", nameTxt, y); y+=30;
		addAreaField("Description", descriptionTxt, y); y+=60;
		addField("Province", provinceTxt, y); y+=30;
		addField("District", districtTxt, y); y+=30;

		addButtons();
		add(sp);

		loadCustomers();

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					idTxt.setText(model.getValueAt(row, 0).toString());
					nameTxt.setText(model.getValueAt(row, 1).toString());
					descriptionTxt.setText(model.getValueAt(row, 2).toString());
					provinceTxt.setText(model.getValueAt(row, 3).toString());
					districtTxt.setText(model.getValueAt(row, 4).toString());
				}
			}
		});
	}

	private void addField(String lbl, JComponent comp, int y) {
		JLabel label = new JLabel(lbl);
		label.setBounds(20, y, 100, 25);
		comp.setBounds(130, y, 200, 25);
		add(label);
		add(comp);
	}

	private void addAreaField(String lbl, JTextArea area, int y) {
		JLabel label = new JLabel(lbl);
		label.setBounds(20, y, 100, 25);
		area.setBounds(130, y, 200, 50);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		add(label);
		add(area);
	}

	private void addButtons() {
		addBtn.setBounds(350, 20, 100, 30);
		updateBtn.setBounds(350, 60, 100, 30);
		deleteBtn.setBounds(350, 100, 100, 30);
		loadBtn.setBounds(350, 140, 100, 30);

		add(addBtn); add(updateBtn); add(deleteBtn); add(loadBtn);

		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		loadBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try (Connection con = DB.getConnection()) {
			if (e.getSource() == addBtn) {
				String sql = "INSERT INTO Customer (Customer_Name, Description, Province, District) VALUES (?, ?, ?, ?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nameTxt.getText());
				ps.setString(2, descriptionTxt.getText());
				ps.setString(3, provinceTxt.getText());
				ps.setString(4, districtTxt.getText());
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Customer added!");
				loadCustomers();
			} else if (e.getSource() == updateBtn) {
				String sql = "UPDATE Customer SET Customer_Name=?, Description=?, Province=?, District=? WHERE Customer_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nameTxt.getText());
				ps.setString(2, descriptionTxt.getText());
				ps.setString(3, provinceTxt.getText());
				ps.setString(4, districtTxt.getText());
				ps.setInt(5, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Customer updated!");
				loadCustomers();
			} else if (e.getSource() == deleteBtn) {
				String sql = "DELETE FROM Customer WHERE Customer_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Customer deleted!");
				loadCustomers();
			} else if (e.getSource() == loadBtn) {
				loadCustomers();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	private void loadCustomers() {
		try (Connection con = DB.getConnection()) {
			model.setRowCount(0);
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Customer");
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getInt("Customer_ID"),
						rs.getString("Customer_Name"),
						rs.getString("Description"),
						rs.getString("Province"),
						rs.getString("District"),
						rs.getTimestamp("CreatedAt")
				});
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}

