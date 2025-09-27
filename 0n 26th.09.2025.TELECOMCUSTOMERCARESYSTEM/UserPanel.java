package telecom.system;
import java.awt.event.ActionListener;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
public class UserPanel  extends JPanel implements ActionListener {
	JTextField idTxt = new JTextField();
	JTextField usernameTxt = new JTextField();
	JPasswordField passTxt = new JPasswordField();
	JTextField roleTxt = new JTextField();
	JTextField emailTxt = new JTextField();
	JComboBox<String> roleCmb = new JComboBox<>(new String[]{"admin", "operator", "manager"});
	JButton addBtn = new JButton("Add");
	JButton updateBtn = new JButton("Update");
	JButton deleteBtn = new JButton("Delete");
	JButton loadBtn = new JButton("Load");
	JTable table;
	DefaultTableModel model;
	public UserPanel() {
		setLayout(null);
		String[] labels = {"UserID", "Username", "Password", "Role", "Email", "CreatedAt"};
		model = new DefaultTableModel(labels, 0);
		table = new JTable(model);
		JScrollPane sp = new JScrollPane(table);
		sp.setBounds(20, 230, 800, 300);
		int y = 20;
		addField("UserID", idTxt, y); idTxt.setEditable(false); y+=30;
		addField("Username", usernameTxt, y); y+=30;
		addField("Password", passTxt, y); y+=30;
		addComboField("Role", roleCmb, y);
		addField("Email", emailTxt, y); y+=30;
		addButtons();
		add(sp);
		loadUsers();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if (row >= 0) {
					idTxt.setText(model.getValueAt(row, 0).toString());
					usernameTxt.setText(model.getValueAt(row, 1).toString());
					passTxt.setText(model.getValueAt(row, 2).toString());
					roleTxt.setText(model.getValueAt(row, 3).toString());
					emailTxt.setText(model.getValueAt(row, 4).toString());
				}
			}});
		}
	

	private void addField(String lbl, JComponent txt, int y) {
		JLabel label = new JLabel(lbl);
		label.setBounds(20, y, 80, 25);
		txt.setBounds(100, y, 150, 25);
		add(label);
		add(txt);
	}
	private void addComboField(String lbl, JComboBox<String> cmb, int y) {
		JLabel l = new JLabel(lbl);
		l.setBounds(20, y, 80, 25);
		cmb.setBounds(100, y, 150, 25);
		add(l); add(cmb);
	}

	private void addButtons() {
		addBtn.setBounds(300, 20, 100, 30);
		updateBtn.setBounds(300, 60, 100, 30);
		deleteBtn.setBounds(300, 100, 100, 30);
		loadBtn.setBounds(300, 140, 100, 30);

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
				String sql = "INSERT INTO User (Username, Password, Role, Email) VALUES (?, ?, ?, ?)";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, usernameTxt.getText());
				ps.setString(2, new String(passTxt.getPassword()));
				ps.setString(3, roleCmb.getSelectedItem());
				ps.setString(4, emailTxt.getText());
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "User added!");
				loadUsers();
			} else if (e.getSource() == updateBtn) {
				String sql = "UPDATE User SET Username=?, Password=?, Role=?, Email=? WHERE UserID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setString(1, usernameTxt.getText());
				ps.setString(2, new String(passTxt.getPassword()));
				ps.setString(3, roleCmb.getSelectedItem());
				ps.setString(4, emailTxt.getText());
				ps.setInt(5, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "User updated!");
				loadUsers();
			} else if (e.getSource() == deleteBtn) {
				String sql = "DELETE FROM User WHERE UserID=?";
				PreparedStatement ps = con.prepareStatement(sql);
				ps.setInt(1, Integer.parseInt(idTxt.getText()));
				ps.executeUpdate();
				JOptionPane.showMessageDialog(this, "User deleted!");
				loadUsers();
			} else if (e.getSource() == loadBtn) {
				loadUsers();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
		}
	}

	private void loadUsers() {
		try (Connection con = DB.getConnection()) {
			model.setRowCount(0);
			ResultSet rs = con.createStatement().executeQuery("SELECT * FROM User");
			while (rs.next()) {
				model.addRow(new Object[] {
						rs.getInt("UserID"),
						rs.getString("Username"),
						rs.getString("Password"),
						rs.getString("Role"),
						rs.getString("Email"),
						rs.getTimestamp("CreatedAt")
				});
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}}




