package telecom.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.sql.*;
import java.text.SimpleDateFormat;

public class ServicePanel extends JPanel implements ActionListener {
	JTextField idTxt = new JTextField();
	JTextField titleTxt = new JTextField();
	JTextField dateTxt = new JTextField();
	JTextField statusTxt = new JTextField();
	JTextField valueTxt = new JTextField();
	JTextArea notesTxt = new JTextArea();
	JComboBox<CustomerItem> customerCmb = new JComboBox<>();

	JButton addBtn = new JButton("Add");
	JButton updateBtn = new JButton("Update");
	JButton deleteBtn = new JButton("Delete");
	JButton loadBtn = new JButton("Load");

	JTable table;
	DefaultTableModel model;

	public ServicePanel() {
		setLayout(null);

		String[] labels = {"Service_ID", "Title", "Date", "Status", "Value", "Notes", "Customer_ID"};
		model = new DefaultTableModel(labels, 0);
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(20, 320, 900, 250);

		int y = 20;
		addField("Service_ID", idTxt, y); idTxt.setEditable(false); y+=30;
		addField("Title", titleTxt, y); y+=30;
		addField("Date (yyyy-MM-dd)", dateTxt, y); y+=30;
		addField("Status", statusTxt, y); y+=30;
		addField("Value", valueTxt, y); y+=30;
		addAreaField("Notes", notesTxt, y); y+=60;
		addComboField("Customer", customerCmb, y); y+=40;

		addButtons();
		add(sp);

		loadCustomersToCombo();
		loadServices();

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if(row >= 0) {
					idTxt.setText(model.getValueAt(row, 0).toString());
					titleTxt.setText(model.getValueAt(row, 1).toString());
					dateTxt.setText(model.getValueAt(row, 2).toString());
					statusTxt.setText(model.getValueAt(row, 3).toString());
					valueTxt.setText(model.getValueAt(row, 4).toString());
					notesTxt.setText(model.getValueAt(row, 5).toString());

					int cid = Integer.parseInt(model.getValueAt(row, 6).toString());
					for(int i=0; i<customerCmb.getItemCount(); i++) {
						if(customerCmb.getItemAt(i).id == cid) {
							customerCmb.setSelectedIndex(i);
							break;
						}
					}
				}
			}
		});
	}

	private void addField(String lbl, JComponent comp, int y) {
		JLabel label = new JLabel(lbl);
		label.setBounds(20, y, 130, 25);
		comp.setBounds(160, y, 200, 25);
		add(label);
		add(comp);
	}

	private void addAreaField(String lbl, JTextArea area, int y) {
		JLabel label = new JLabel(lbl);
		label.setBounds(20, y, 130, 25);
		area.setBounds(160, y, 200, 60);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		add(label);
		add(area);
	}

	private void addComboField(String lbl, JComboBox<?> cmb, int y) {
		JLabel label = new JLabel(lbl);
		label.setBounds(20, y, 130, 25);
		cmb.setBounds(160, y, 200, 25);
		add(label);
		add(cmb);
	}

	private void addButtons() {
		addBtn.setBounds(400, 20, 100, 30);
		updateBtn.setBounds(400, 60, 100, 30);
		deleteBtn.setBounds(400, 100, 100, 30);
		loadBtn.setBounds(400, 140, 100, 30);

		add(addBtn); add(updateBtn); add(deleteBtn); add(loadBtn);

		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		loadBtn.addActionListener(this);
	}

	private void loadCustomersToCombo() {
		try (Connection con = DB.getConnection()) {
			customerCmb.removeAllItems();
			ResultSet rs = con.createStatement().executeQuery("SELECT Customer_ID, Customer_Name FROM Customer");
			while(rs.next()) {
				customerCmb.addItem(new CustomerItem(rs.getInt(1), rs.getString(2)));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try (Connection con = DB.getConnection()) {
			if(e.getSource() == addBtn) {
				String sql = "INSERT INTO Service (Title, Date, Status, Value, Notes, Customer_ID) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, titleTxt.getText());
				ps.setDate(2, java.sql.Date.valueOf(dateTxt.getText()));
				ps.setString(3, statusTxt.getText());
				ps.setBigDecimal(4, new java.math.BigDecimal(valueTxt.getText()));
				ps.setString(5, notesTxt.getText());
				ps.setInt(6, ((CustomerItem)customerCmb.getSelectedItem()).id);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Service added!");
				loadServices();
			} else if(e.getSource() == updateBtn) {
				String sql = "UPDATE Service SET Title=?, Date=?, Status=?, Value=?, Notes=?, Customer_ID=? WHERE Service_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, titleTxt.getText());
				ps.setDate(2, java.sql.Date.valueOf(dateTxt.getText()));
				ps.setString(3, statusTxt.getText());
				ps.setBigDecimal(4, new java.math.BigDecimal(valueTxt.getText()));
				ps.setString(5, notesTxt.getText());
				ps.setInt(6, ((CustomerItem)customerCmb.getSelectedItem()).id);
				ps.setInt(7, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Service updated!");
				loadServices();
			} else if(e.getSource() == deleteBtn) {
				String sql = "DELETE FROM Service WHERE Service_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Service deleted!");
				loadServices();
			} else if(e.getSource() == loadBtn) {
				loadServices();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	private void loadServices() {
		try (Connection con = DB.getConnection()) {
			model.setRowCount(0);
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Service");
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getInt("Service_ID"),
						rs.getString("Title"),
						rs.getDate("Date"),
						rs.getString("Status"),
						rs.getBigDecimal("Value"),
						rs.getString("Notes"),
						rs.getInt("Customer_ID")
				});
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static class CustomerItem {
		int id;
		String name;
		public CustomerItem(int id, String name) {
			this.id = id; this.name = name;
		}
		@Override
		public String toString() {
			return name + " (" + id + ")";
		}
	}
}

