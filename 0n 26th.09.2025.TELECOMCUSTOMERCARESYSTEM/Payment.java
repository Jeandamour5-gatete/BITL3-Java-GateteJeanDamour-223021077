package telecom.system;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.event.*;
import java.sql.*;

public class PaymentPanel extends JPanel implements ActionListener {
	JTextField idTxt = new JTextField();
	JTextField amountTxt = new JTextField();
	JTextField paymentDateTxt = new JTextField(); 
	JTextField descriptionTxt = new JTextField();
	JComboBox<CustomerItem> customerCmb = new JComboBox<>();
	JComboBox<SubscriptionItem> subscriptionCmb = new JComboBox<>();

	JButton addBtn = new JButton("Add");
	JButton updateBtn = new JButton("Update");
	JButton deleteBtn = new JButton("Delete");
	JButton loadBtn = new JButton("Load");

	JTable table;
	DefaultTableModel model;

	public PaymentPanel() {
		setLayout(null);

		String[] labels = {"Payment_ID", "Amount", "PaymentDate", "Description", "Customer_ID", "Subscription_ID"};
		model = new DefaultTableModel(labels, 0);
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(20, 320, 900, 250);

		int y = 20;
		addField("Payment_ID", idTxt, y); idTxt.setEditable(false); y+=30;
		addField("Amount", amountTxt, y); y+=30;
		addField("PaymentDate (yyyy-MM-dd)", paymentDateTxt, y); y+=30;
		addField("Description", descriptionTxt, y); y+=30;
		addComboField("Customer", customerCmb, y); y+=40;
		addComboField("Subscription", subscriptionCmb, y); y+=40;

		addButtons();
		add(sp);

		loadCustomersToCombo();
		loadSubscriptionsToCombo();
		loadPayments();

		table.addMouseListener(new java.awt.event.MouseAdapter() {
			public void mouseClicked(java.awt.event.MouseEvent evt) {
				int row = table.getSelectedRow();
				if(row >= 0) {
					idTxt.setText(model.getValueAt(row, 0).toString());
					amountTxt.setText(model.getValueAt(row, 1).toString());
					paymentDateTxt.setText(model.getValueAt(row, 2).toString());
					descriptionTxt.setText(model.getValueAt(row, 3).toString());

					int cid = Integer.parseInt(model.getValueAt(row, 4).toString());
					for(int i=0; i<customerCmb.getItemCount(); i++) {
						if(customerCmb.getItemAt(i).id == cid) {
							customerCmb.setSelectedIndex(i);
							break;
						}
					}

					int sid = Integer.parseInt(model.getValueAt(row, 5).toString());
					for(int i=0; i<subscriptionCmb.getItemCount(); i++) {
						if(subscriptionCmb.getItemAt(i).id == sid) {
							subscriptionCmb.setSelectedIndex(i);
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

	private void loadSubscriptionsToCombo() {
		try (Connection con = DB.getConnection()) {
			subscriptionCmb.removeAllItems();
			ResultSet rs = con.createStatement().executeQuery("SELECT Subscription_ID, Name FROM Subscription");
			while(rs.next()) {
				subscriptionCmb.addItem(new SubscriptionItem(rs.getInt(1), rs.getString(2)));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try (Connection con = DB.getConnection()) {
			if(e.getSource() == addBtn) {
				String sql = "INSERT INTO Payment (Amount, PaymentDate, Description, Customer_ID, Subscription_ID) VALUES (?, ?, ?, ?, ?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setBigDecimal(1, new java.math.BigDecimal(amountTxt.getText()));
				ps.setDate(2, java.sql.Date.valueOf(paymentDateTxt.getText()));
				ps.setString(3, descriptionTxt.getText());
				ps.setInt(4, ((CustomerItem)customerCmb.getSelectedItem()).id);
				ps.setInt(5, ((SubscriptionItem)subscriptionCmb.getSelectedItem()).id);
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Payment added!");
				loadPayments();
			} else if(e.getSource() == updateBtn) {
				String sql = "UPDATE Payment SET Amount=?, PaymentDate=?, Description=?, Customer_ID=?, Subscription_ID=? WHERE Payment_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setBigDecimal(1, new java.math.BigDecimal(amountTxt.getText()));
				ps.setDate(2, java.sql.Date.valueOf(paymentDateTxt.getText()));
				ps.setString(3, descriptionTxt.getText());
				ps.setInt(4, ((CustomerItem)customerCmb.getSelectedItem()).id);
				ps.setInt(5, ((SubscriptionItem)subscriptionCmb.getSelectedItem()).id);
				ps.setInt(6, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Payment updated!");
				loadPayments();
			} else if(e.getSource() == deleteBtn) {
				String sql = "DELETE FROM Payment WHERE Payment_ID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Payment deleted!");
				loadPayments();
			} else if(e.getSource() == loadBtn) {
				loadPayments();
			}
		} catch(Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	private void loadPayments() {
		try (Connection con = DB.getConnection()) {
			model.setRowCount(0);
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM Payment");
			while(rs.next()) {
				model.addRow(new Object[] {
						rs.getInt("Payment_ID"),
						rs.getBigDecimal("Amount"),
						rs.getDate("PaymentDate"),
						rs.getString("Description"),
						rs.getInt("Customer_ID"),
						rs.getInt("Subscription_ID")
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

	private static class SubscriptionItem {
		int id;
		String name;
		public SubscriptionItem(int id, String name) {
			this.id = id; this.name = name;
		}
		@Override
		public String toString() {
			return name + " (" + id + ")";
		}
	}
}

