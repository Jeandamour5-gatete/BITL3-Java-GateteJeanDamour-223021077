package ASSIGNMENT1;

public class GRADING {

	 public static void main(String[] args) {
	        Scanner G = new Scanner(System.in);
	        int totalStudents = 0;
	        int passes = 0;
	        int fails = 0;
	        System.out.println("Enter student marks :")
	        while {
	            System.out.print("Enter student mark: ");
	            int mark = G.nextInt();
	            if (mark == -1) {
	                break;
	            }
	            if (mark < 0 || mark > 100) {
	                System.out.println("Invalid mark! Please enter a value between 0 and 100.");
	                continue;
	            }
	            totalStudents++;
	            String grade;
	            if (mark >= 80 && mark <= 100) {
	                grade = "A";
	                passes++;
	            } else if (mark >= 70 && mark <= 79) {
	                grade = "B";
	                passes++;
	            } else if (mark >= 60 && mark <= 69) {
	                grade = "C";
	                passes++;
	            } else if (mark >= 50 && mark <= 59) {
	                grade = "D";
	                passes++;
	            } else {
	                grade = "F";
	                fails++;
	            }
	            System.out.println("Student " + totalStudents + " Grade: " + grade);
	        }
	        System.out.println("SUMMARY REPORT");
	        System.out.println("Total Students: " + totalStudents);
	        System.out.println("Passed: " + passes);
	        System.out.println("Failed: " + fails);
	        if (totalStudents > 0) {
	            double passed = (double) passes / totalStudents * 100;
	            System.out.printf("Pass Rate: %.2f%%\n", passed);
	        } else {
	            System.out.println("Passed: No students entered");
	        }
	        G.close();
	    }
}
