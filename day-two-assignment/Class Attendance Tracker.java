package ASSIGNMENT1;

public class CLASSATTENDANCESYSTEM {
	public static void main(String[] args) {
        Scanner D = new Scanner(System.in);
        final int MAX_DAYS = 35;
        int[] attendance = new int[MAX_DAYS];
        int dayCount = 0;
        System.out.println("=== Class Attendance System ===");
        System.out.print("Enter total number of students in the class: ");
        int totalStudents = D.nextInt();
        if (totalStudents <= 0) {
            System.out.println("Invalid number of students!");
            D.close();
            return;
        }
        String continueEntry;
        do {
            System.out.print("Enter number of students present for Day " + (dayCount + 1) + ": ");
            int present = scanner.nextInt();
            if (present < 0 || present > totalStudents) {
                System.out.println("Invalid input! Number must be between 0 and " + totalStudents);
                continue;
            }
            attendance[dayCount] = present;
            dayCount++;
            if (dayCount >= MAX_DAYS) {
                System.out.println("Maximum days (35) reached. Cannot record more attendance.");
                break;
            }
            System.out.print("Do you want to enter attendance for another day? (yes/no): ");
            continueEntry = scanner.next().toLowerCase();
        } while (continueEntry.equals("yes") || continueEntry.equals("y"));
        System.out.println("\n=== ATTENDANCE SUMMARY REPORT ===");
        System.out.println("\nDaily Attendance Record:");
        System.out.println("Day\tStudents Present\tPercentage");
        System.out.println("--------------------------------------------");
        int totalAttendance = 0;
        int lowAttendanceDays = 0;
        
        for (int i = 0; i < dayCount; i++) {
            int present = attendance[i];
            double percentage = (double) present / totalStudents * 100;
            totalAttendance += present;
            System.out.printf("Day %d\t%d\t\t\t%.1f%%\n", i + 1, present, percentage);
            if (percentage < 50) {
                lowAttendanceDays++;
            }
        }
        if (dayCount > 0) {
            double averageAttendance = (double) totalAttendance / dayCount;
            double averagePercentage = (double) totalAttendance / (dayCount * totalStudents) * 100;
            double lowAttendanceRate = (double) lowAttendanceDays / dayCount * 100;
            System.out.println("\n=== STATISTICS ===");
            System.out.printf("Total days recorded: %d\n", dayCount);
            System.out.printf("Average attendance per day: %.1f students\n", averageAttendance);
            System.out.printf("Average attendance rate: %.1f%%\n", averagePercentage);
            System.out.printf("Days with low attendance (<50%%): %d\n", lowAttendanceDays);
            System.out.printf("Percentage of days with low attendance: %.1f%%\n", lowAttendanceRate);
            if (lowAttendanceDays > 0) {
                System.out.println("\nDays with attendance below 50%:");
                for (int i = 0; i < dayCount; i++) {
                    double percentage = (double) attendance[i] / totalStudents * 100;
                    if (percentage < 50) {
                        System.out.printf("Day %d: %.1f%%\n", i + 1, percentage);
                    }
                }
            } else {
                System.out.println("\nNo days with attendance below 50%!");
            } 
        } else {
            System.out.println("No attendance data recorded.");
        }
        D.close();
    }
}


