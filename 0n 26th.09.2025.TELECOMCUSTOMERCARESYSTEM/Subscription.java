package telecom.system;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.sql.*;

public class SubscriptionPanel extends JPanel implements ActionListener {
	JTextField idTxt = new JTextField();
	JTextField nameTxt = new JTextField();
	JTextField typeTxt = new JTextField();
	JTextField startDateTxt = new JTextField();
	JTextField endDateTxt = new JTextField(); 
	JTextField statusTxt = new JTextField();
	JComboBox<CustomerItem> customerCmb = new JComboBox<>();

	JButton addBtn = new JButton("Add");
	JButton updateBtn = new JButton("Update");
	JButton deleteBtn = new JButton("Delete");
	JButton loadBtn = new JButton("Load");

	JTable table;
	DefaultTableModel model;

	public SubscriptionPanel() {
		setLayout(null);

		String[] labels = {"Subscription_ID", "Name", "Type", "StartDate", "EndDate", "Status", "Customer_ID"};
		model = new DefaultTableModel(labels, 0);
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(20, 300, 900, 250);

		int y = 20;
		addField("Subscription_ID", idTxt, y); idTxt.setEditable(false); y+=30;
		addField("Name", nameTxt, y); y+=30;
		addField("Type", typeTxt, y); y+=30;
		addField("StartDate (yyyy-MM-dd)", startDateTxt, y); y+=30;
		addField("EndDate (yyyy-MM-dd)", endDateTxt, y); y+=30;
		addField("Status", statusTxt, y); y+=30;
		addComboField("Customer", customerCmb, y); y+=40;

		addButtons();
		add(sp);

		loadCustomersToCombo();
		loadSubscriptions();

		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if(row >= 0) {
					idTxt.setText(model.getValueAt(row, 0).toString());
					nameTxt.setText(model.getValueAt(row, 1).toString());
					typeTxt.setText(model.getValueAt(row, 2).toString());
					startDateTxt.setText(model.getValueAt(row, 3).toString());
					Object endDateVal = model.getValueAt(row, 4);
					endDateTxt.setText(endDateVal != null ? endDateVal.toString() : "");
					statusTxt.setText(model.getValueAt(row, 5).toString());

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
		try (Connection con = DB.getConnection()) {
			if(e.getSource() == addBtn) {
				String sql = "INSERT INTO Subscription (Name, Type, StartDate, EndDate, Status, Customer_ID) VALUES (?, ?, ?, ?, ?, ?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nameTxt.getText());
				ps.setString(2, typeTxt.getText());
				ps.setDate(3, java.sql.Date.valueOf(startDateTxt.getText()));
				if(endDateTxt.getText().trim().isEmpty()) {
					ps.setNull(4, Types.DATE);
				} else {
					ps.setDate(4, java.sql.Date.valueOf(endDateTxt.getText()));
				}
				ps.setString(5, statusTxt.getText());
				ps.setInt(6, ((CustomerItem)customerCmb.getSelectedItem()).id);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Subscription added!");
				loadSubscriptions();
			} else if(e.getSource() == updateBtn) {
				String sql = "UPDATE Subscription SET Name=?, Type=?, StartDate=?, EndDate=?, Status=?, Customer_ID=? WHERE Subscription_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nameTxt.getText());
				ps.setString(2, typeTxt.getText());
				ps.setDate(3, java.sql.Date.valueOf(startDateTxt.getText()));
				if(endDateTxt.getText().trim().isEmpty()) {
					ps.setNull(4, Types.DATE);
				} else {
					ps.setDate(4, java.sql.Date.valueOf(endDateTxt.getText()));
				}
				ps.setString(5, statusTxt.getText());
				ps.setInt(6, ((CustomerItem)customerCmb.getSelectedItem()).id);
				ps.setInt(7, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Subscription updated!");
				loadSubscriptions();
			} else if(e.getSource() == deleteBtn) {
				String sql = "DELETE FROM Subscription WHERE Subscription_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Subscription deleted!");
				loadSubscriptions();
			} else if(e.getSource() == loadBtn) {
				loadSubscriptions();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	private void loadSubscriptions() {
		try (Connection con = DB.getConnection()) {
			model.setRowCount(0);
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Subscription");
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getInt("Subscription_ID"),
						rs.getString("Name"),
						rs.getString("Type"),
						rs.getDate("StartDate"),
						rs.getDate("EndDate"),
						rs.getString("Status"),
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
}
