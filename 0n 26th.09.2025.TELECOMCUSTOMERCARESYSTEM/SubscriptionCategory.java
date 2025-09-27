package telecom.system;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.*;
import java.sql.*;

public class SubscriptionCategoryPanel extends JPanel implements ActionListener {
	JTextField idTxt = new JTextField();
	JTextField nameTxt = new JTextField();

	JButton addBtn = new JButton("Add");
	JButton updateBtn = new JButton("Update");
	JButton deleteBtn = new JButton("Delete");
	JButton loadBtn = new JButton("Load");

	JTable table;
	DefaultTableModel model;

	public SubscriptionCategoryPanel() {
		setLayout(null);

		
		String[] columns = {"SubscriptionCategory_ID", "Name"};
		model = new DefaultTableModel(columns, 0);
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(20, 160, 500, 300);
		add(sp);

		
		int y = 20;
		addField("SubscriptionCategory_ID", idTxt, y);
		idTxt.setEditable(false);
		y += 30;
		addField("Name", nameTxt, y);
		y += 30;

		
		addButtons();

	
		loadSubscriptionCategories();

		
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					idTxt.setText(model.getValueAt(row, 0).toString());
					nameTxt.setText(model.getValueAt(row, 1).toString());
				}
			}
		});
	}

	private void addField(String label, JTextField field, int y) {
		JLabel lbl = new JLabel(label);
		lbl.setBounds(20, y, 150, 25);
		field.setBounds(180, y, 200, 25);
		add(lbl);
		add(field);
	}

	private void addButtons() {
		addBtn.setBounds(420, 20, 100, 30);
		updateBtn.setBounds(420, 60, 100, 30);
		deleteBtn.setBounds(420, 100, 100, 30);
		loadBtn.setBounds(420, 140, 100, 30);

		add(addBtn);
		add(updateBtn);
		add(deleteBtn);
		add(loadBtn);

		addBtn.addActionListener(this);
		updateBtn.addActionListener(this);
		deleteBtn.addActionListener(this);
		loadBtn.addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try (Connection con = DB.getConnection()) {
			if (e.getSource() == addBtn) {
				String sql = "INSERT INTO SubscriptionCategory (Name) VALUES (?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nameTxt.getText().trim());
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Category added.");
				loadSubscriptionCategories();
				clearFields();
			} else if (e.getSource() == updateBtn) {
				String sql = "UPDATE SubscriptionCategory SET Name = ? WHERE SubscriptionCategory_ID = ?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, nameTxt.getText().trim());
				ps.setInt(2, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "Category updated.");
				loadSubscriptionCategories();
				clearFields();
			} else if (e.getSource() == deleteBtn) {
				int confirm = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this category?");
				if (confirm == JOptionPane.YES_OPTION) {
					String sql = "DELETE FROM SubscriptionCategory WHERE SubscriptionCategory_ID = ?";
					PreparedStatement ps = con.prepareStatement(sql);
					ps.setInt(1, Integer.parseInt(idTxt.getText()));
					ps.executeUpdate();
					JOptionPane.showMessageDialog(this, "Category deleted.");
					loadSubscriptionCategories();
					clearFields();
				}
			} else if (e.getSource() == loadBtn) {
				loadSubscriptionCategories();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	private void loadSubscriptionCategories() {
		try (Connection con = DB.getConnection()) {
			model.setRowCount(0);
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM SubscriptionCategory");
			while (rs.next()) {
				model.addRow(new Object[]{
						rs.getInt("SubscriptionCategory_ID"),
						rs.getString("Name")
				});
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void clearFields() {
		idTxt.setText("");
		nameTxt.setText("");
	}
}

