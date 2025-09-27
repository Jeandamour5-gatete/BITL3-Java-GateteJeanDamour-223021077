package telecom.system;
import javax.swing.*;
public class MainFrame extends JFrame {
	    JTabbedPane tabbedPane;
	    public MainFrame() {
	        setTitle("Telecom Customer Care System");
	        setSize(900, 700);
	        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        setLocationRelativeTo(null);
	        tabbedPane = new JTabbedPane();
	        tabbedPane.add("Users", new UserPanel());
	        tabbedPane.add("Customers", new CustomerPanel());
	        tabbedPane.add("Services", new ServicePanel());
	        tabbedPane.add("Subscription", new SubscriptionPanel());
	        tabbedPane.add("Payment", new PaymentPanel());
	        tabbedPane.add("SubscriptionCategory", new SubscriptionCategoryPanel());
	        add(tabbedPane);
	    }
	    public static void main(String[] args) {
	        SwingUtilities.invokeLater(() -> {
	            new MainFrame().setVisible(true);
	        });
	    }
	}


