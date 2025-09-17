package ASSIGNMENT1;

public class SUPERMARKETBILLINGSYSTEM {
	    public static void main(String[] args) {
	        Scanner sb = new Scanner(System.in);
	        System.out.print("Enter the number of different items purchased: ");
	        int itemCount = sb.nextInt();
	        sb.nextLine();
	        String[] itemNames = new String[itemCount];
	        double[] prices = new double[itemCount];
	        int[] quantities = new int[itemCount];
	        double[] subtotals = new double[itemCount];
	        double totalBill = 0;
	        for (int i = 0; i < itemCount; i++) {
	            System.out.println("Enter name of item %d: "+ i+1);
	            itemNames[i] = sb.nextLine();
	            System.out.println("Enter price per unit of %s: "+ itemNames[i]);
	            prices[i] = sb.nextDouble();
	            System.out.println("Enter quantity of %s: "+ itemNames[i]);
	            quantities[i] = sb.nextInt();
	            sb.nextLine(); 
	            subtotals[i] = prices[i] * quantities[i];
	            totalBill += subtotals[i];
	        }
	        double discount = 0;
	        if (totalBill > 50000) {
	            discount = totalBill * 0.05;
	        }
	        double finalAmount = totalBill - discount;
	        System.out.print("\n----- Receipt -----");
	        System.out.print("%-15s %-10s %-15s %-10s%n"; "Item Name"; "Quantity"; "Price per Unit"; "Subtotal");
	        for (int i = 0; i < itemCount; i++) {
	            System.out.print("%-15s %-10d %-15.2f %-10.2f%n"+
	                itemNames[i] +quantities[i] +prices[i] +subtotals[i]
	            );
	        }
	        System.out.print("\nTotal before discount: %.2f%n", + totalBill);
	        System.out.print("Discount: %.2f%n" + discount);
	        System.out.print("Final amount payable: %.2f%n" + finalAmount);
	        sb.close();
	    }
	}

}
