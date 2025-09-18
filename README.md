On 18th/09/2025
Group Members:
1.KABAREBE Denis                              223019165
2.UMUHIRE Elie                                223014167
3.NEZERWA Gikundiro Sandrine                  222007415
4.GATETE Jean d’Amour                         223021077
5.TUMUKUNDE Yvonne                            222005483
6.MUNYEMANA Jean Paul                         222007122

JAVA ASSIGNMENT
Q1. Rectangle Area Calculator
package trig;
import java.util.Scanner;
public class Rectangle {
    double length;
    double width;
public double calculateArea(double length, double width) {
        return length * width;
}
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
System.out.print("enter the value of length: ");
        double l= s.nextDouble();
System.out.print("enter the value of width: ");
        double w = s.nextDouble();

      
        Rectangle re = new Rectangle();
        double area = re.calculateArea(l, w);
if (l == w) {
       System.out.println("it is a square.");}
 else {
          System.out.println("it is not a square.");
        }
        System.out.println("the area of rectangle is: " + area);
        s.close();
    }
}
Q2. Circle Area with Switch Menu
package trig;
import java.util.Scanner;
public class Circle {
    double radius;
    double pi=3.14;
 public double calculateArea(double radius) {
        return pi*radius*radius;
    }
    public double calculateCircumference(double radius) {
        return 2*pi*radius;
    }

    public static void main(String[] args) {
        Scanner s= new Scanner(System.in);
        System.out.print("enter the radius of the circle: ");
        double rd= s.nextDouble()
    Circle c = new Circle();
    System.out.println("Choose an option:");
        System.out.println("1. Calculate Area");
        System.out.println("2. Calculate Circumference");
System.out.print("enter your choice (1/2): ");
        int choice = s.nextInt();
        switch (choice) {
            case 1:
                double area = c.calculateArea(rd);
                System.out.println("the area of circle is: " + area);
                break;
            case 2:
                double circumference = c.calculateCircumference(rd);
                System.out.println("the circumference of the circle is: " + circumference);
                break;
            default:
                System.out.println("Invalid choice. Try again");
        }
        s.close();
    }
}
Q3.  Multiple Shapes with For Loop 
package trig;
import java.util.Scanner;
public class Rectangle {
    double length;
    double width;
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
   public double calculateArea() {
        return length * width;
    }
   public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
 Rectangle[] rectangles = new Rectangle[3];
 for (int i = 0; i < rectangles.length; i++) {
            System.out.println("enter the dimensions for Rectangle " + (i + 1) + ":");
        System.out.print("Length: ");
            double l = s.nextDouble();
            System.out.print("Width: ");
            double w = s.nextDouble();
            rectangles[i] = new Rectangle(l, w);
        }

       
        System.out.println("\n*****AREAS OF RECTANGLES *****");
        for (int i = 0; i < rectangles.length; i++) {
            System.out.println("Rectangle " + (i + 1) + " Area = " + rectangles[i].calculateArea());
        }
        s.close();
    }
}
Q4.    Shape Chooser with Switch and OOP
package trig;
import java.util.Scanner;
class Rectangle {
    double length;
    double width;
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    public double calculateArea() {
        return length * width;
    }
}
class Circle {
    double radius;
double pi=3.14
    Circle(double radius) {
        this.radius = radius;
    }
    public double calculateArea() {
        return pi* radius * radius;
    }
}
public class ShapeChooser {
    public static void main(String[] args) {
        Scanner s= new Scanner(System.in);
        System.out.println("Choose a shape:");
        System.out.println("1. Rectangle");
        System.out.println("2. Circle");
        System.out.print("enter your choice (1/2): ");
        int choice = s.nextInt();
        switch (choice) {
            case 1:
                System.out.print("enter the length: ");
                double length = s.nextDouble();
                System.out.print("enter the width: ");
                double width = s.nextDouble();
                Rectangle re = new Rectangle(length, width);
                System.out.println("Area of Rectangle = " + re.calculateArea());
                break;

            case 2:
                System.out.print("enter the radius: ");
                double radius = s.nextDouble();
                Circle circle = new Circle(radius);
                System.out.println("the area of the circle is: " + circle.calculateArea());
                break;
            default:
                System.out.println("Invalid choice. Try again");
        }
        s.close();
    }
}
Q5.   Comparing Shapes with If Condition
package trig;
import java.util.Scanner;
class Rectangle {
    double length;
    double width;
    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }
    public double calculateArea() {
        return length * width;
    }
}
class Circle {
    double radius;
   double pi=3.14;
    Circle(double radius) {
        this.radius = radius;
    }
    public double calculateArea() {
        return pi* radius * radius;
    }
}
public class CompareShapes {
    public static void main(String[] args) {
        Scanner s= new Scanner(System.in);
        System.out.print("enter the length of rectangle: ");
        double length = s.nextDouble();
        System.out.print("enter width the of rectangle: ");
        double width = s.nextDouble();
        System.out.print("enter the radius of circle: ");
        double radius = s.nextDouble();
        Rectangle re = new Rectangle(length, width);
        Circle c= new Circle(radius);


        double reArea = re.calculateArea();
        double circleArea = c.calculateArea();
        System.out.println("\n****AREAS****");
        System.out.println("the area of rectangle is: " + reArea);
        System.out.println("the area circle is:" + cArea);
        if (reArea > cArea) {
            System.out.println("The Rectangle has the bigger area than Circle.");
        } else if (cArea > reArea) {
            System.out.println("The Circle has the bigger area than Rectangle.");
        } else {
            System.out.println("Both shapes have equal area.");
        }
        s.close();
    }
}
