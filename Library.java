import java.util.*;

public class Library 
{
	public static void main(String[] args)
	{
		Scanner sc = new Scanner(System.in);
		System.out.println();
		System.out.println("******************!!Welcome to Library Store!!******************");
		System.out.println();
		System.out.println("                   Select From The Following Options:	    ");
		
		books ob = new books();
		students obStudent = new students();
		int choice;
		int searchChoice;

		do 
		{
            ob.dispMenu();
			System.out.print("Enter your choice: ");
			choice = sc.nextInt();
			switch (choice)
			{
				case 0:
				System.out.println("");
				break;

                case 1:
				book b = new book();
				ob.addBook(b);
				break;

				case 2:
				ob.upgradeBookQty();
				break;

 			    case 3:
                System.out.println("** Press 1 to Search with Book's Serial No.");
				System.out.println("** Press 2 to Search with Book's Author Name.");
				searchChoice = sc.nextInt();
                switch (searchChoice) 
				{
					case 1:
					ob.searchBySno();
					break;
                    
					case 2:
					ob.searchByAuthorName();
				}
				break;

				case 4:
				ob.showAllBooks();
				break;

		        case 5:
				student s = new student();
				obStudent.addStudent(s);
				break;
				
			    case 6:
				obStudent.showAllStudents();
				break;
				
			    case 7:
				obStudent.checkOutBook(ob);
				break;

			    case 8:
				obStudent.checkInBook(ob);
				break;

			    default:
                System.out.println("ENTER CHOICE BETWEEN 0 TO 8 ONLY.");
			}
        }
		while (choice != 0);
	}
}

class students 
{
	Scanner sc = new Scanner(System.in);
	student theStudents[] = new student[50];
    public static int count = 0;

	public void addStudent(student s)
	{
		for (int i = 0; i < count; i++) 
		{
            if (s.regNum.equalsIgnoreCase(theStudents[i].regNum)) 
			{
                System.out.println("Student of Register Num "+s.regNum+" is already Registered.");
                return;
			}
		}

		if (count <= 50) 
		{
			theStudents[count] = s;
			count++;
		}
	}

    public void showAllStudents()
	{
		System.out.println("Student Name\tRegistration Number");
		for (int i = 0; i < count; i++) 
		{
            System.out.println(theStudents[i].studentName+ "\t\t\t"+ theStudents[i].regNum);
		}
	}

	public int isStudent()
	{
		System.out.print("Enter Reg Number: ");
        String regNum = sc.nextLine();

		for (int i = 0; i < count; i++) 
		{
            if (theStudents[i].regNum.equalsIgnoreCase(regNum))
			{
				return i;
			}
		}
        System.out.println("Student is not Registered");
		System.out.println("!!!Get Registered First!!!");
		return -1;
	}
	
	public void checkOutBook(books book)
	{
		int studentIndex = this.isStudent();

		if (studentIndex != -1) 
		{
			book.showAllBooks();
			book b = book.checkOutBook();

			System.out.println("~~Checking out~~");
			if (b != null) 
			{
                if (theStudents[studentIndex].booksCount<= 3) 
				{
					System.out.println("Adding book");
					theStudents[studentIndex].borrowedBooks[theStudents[studentIndex].booksCount]= b;
					theStudents[studentIndex].booksCount++;
					return;
				}
				else 
				{
					System.out.println("Student Can not Borrow more than 3 Books.");
					return;
				}
			}
			System.out.println("Book is not Available.");
		}
	}

	public void checkInBook(books book)
	{
		int studentIndex = this.isStudent();
		if (studentIndex != -1) 
		{			
			System.out.println("S.No\t\tBook Name\t\tAuthor Name");
			student s = theStudents[studentIndex];

			for (int i = 0; i < s.booksCount; i++) 
			{
				System.out.println(s.borrowedBooks[i].sNo + "\t\t"+ s.borrowedBooks[i].bookName + "\t\t"+ s.borrowedBooks[i].authorName);
			}
			
			System.out.print("Enter Serial Number of Book to be Checked In: ");
			int sNo = sc.nextInt();

			for (int i = 0; i < s.booksCount; i++) 
			{
				if (sNo == s.borrowedBooks[i].sNo) 
				{
					book.checkInBook(s.borrowedBooks[i]);
					s.borrowedBooks[i] = null;
					return;
				}
			}
			System.out.println("Book of Serial No " + sNo+ "not Found");
		}
	}
}

class student
{
	String studentName;
	String regNum;
    book borrowedBooks[] = new book[3];
	public int booksCount = 0;
    Scanner sc = new Scanner(System.in);	
	
	public student()
	{		
		System.out.print("Enter Student Name: ");		
		this.studentName = sc.nextLine();		
		System.out.print("Enter Registration Number: ");
		this.regNum = sc.nextLine();
	}
}

class books 
{
	book theBooks[] = new book[50];
	public static int count;

	Scanner sc = new Scanner(System.in);
	public int compareBookObjects(book b1, book b2)
	{		
		if (b1.bookName.equalsIgnoreCase(b2.bookName)) 
		{			
			System.out.println("Book of this Name Already Exists.");
			return 0;
		}		
		if (b1.sNo == b2.sNo) 
		{			
			System.out.println("Book of this Serial No Already Exists.");
			return 0;
		}
		return 1;
	}
	
	public void addBook(book b)
	{
		for (int i = 0; i < count; i++) 
		{
			if (this.compareBookObjects(b, this.theBooks[i])== 0)
			return;
		}
        if (count < 50) 
		{
			theBooks[count] = b;
			count++;
		}
		else 
		{
			System.out.println("No Space to Add More Books.");
		}
	}
	
	public void searchBySno()
	{		
		System.out.println("\t\tSEARCH BY SERIAL NUMBER\n");
		int sNo;
		System.out.print("Enter Serial No of Book: ");
		sNo = sc.nextInt();

		int flag = 0;
		System.out.println("S.No\tName\t\tAuthor\t\tAvailable Qty\tTotal Qty");
        for (int i = 0; i < count; i++) 
		{
			if (sNo == theBooks[i].sNo) 
			{
				System.out.println(theBooks[i].sNo + "\t"+ theBooks[i].bookName + "\t"+ theBooks[i].authorName + "\t\t"+ theBooks[i].bookQtyCopy + "\t\t"+ theBooks[i].bookQty);
				flag++;
				return;
			}
		}
		if (flag == 0)
		{
			System.out.println("No Book for Serial No "+ sNo + " Found.");
		}
	}

	public void searchByAuthorName()
	{		
		System.out.println("\t\tSEARCH BY AUTHOR'S NAME");
		System.out.print("Enter Author Name: ");
		String authorName = sc.nextLine();
		int flag = 0;
		System.out.println("S.No\tName\tAuthor\t\tAvailable Qty\tTotal Qty");

		for (int i = 0; i < count; i++) 
		{			
			if (authorName.equalsIgnoreCase(theBooks[i].authorName)) 
			{				
				System.out.println(theBooks[i].sNo + "\t"+ theBooks[i].bookName + "\t"+ theBooks[i].authorName + "\t\t"+ theBooks[i].bookQtyCopy + "\t\t"+ theBooks[i].bookQty);
				flag++;
			}
		}		
		if (flag == 0)
		{
			System.out.println("No Books of " + authorName+ " Found.");
		}
	}

	public void showAllBooks()
	{

		System.out.println("\t\tSHOWING ALL BOOKS\n");
		System.out.println("S.No.\t\tName\t\tAuthor\t\tAvailable Qty\tTotal Qty");

		for (int i = 0; i < count; i++) 
		{
			System.out.println(theBooks[i].sNo + "\t"+ theBooks[i].bookName + "\t\t"+ theBooks[i].authorName + "\t\t"+ theBooks[i].bookQtyCopy + "\t\t"+ theBooks[i].bookQty);
		}
	}

	public void upgradeBookQty()
	{

		System.out.println("\t\tUPGRADE QUANTITY OF A BOOK\n");
		System.out.print("Enter Serial No of Book: ");
        int sNo = sc.nextInt();

		for (int i = 0; i < count; i++) 
		{
			if (sNo == theBooks[i].sNo) 
			{				
				System.out.print("Enter No. of Books to be Added: ");
				int addingQty = sc.nextInt();
				theBooks[i].bookQty += addingQty;
				theBooks[i].bookQtyCopy += addingQty;
				return;
			}
		}
	}

	public void dispMenu()
	{
		System.out.println("-----------------------------------------------------------------------");
		System.out.println("Press 1 to Add new Book");
		System.out.println("Press 2 to Upgrade Quantity of a Book");
		System.out.println("Press 3 to Search a Book");
		System.out.println("Press 4 to Show All Books");
		System.out.println("Press 5 to Register Student");
		System.out.println("Press 6 to Show All Registered Students");
		System.out.println("Press 7 to Check Out Book");
		System.out.println("Press 8 to Check In Book");
		System.out.println("Press 0 to Exit the Application");
		System.out.println("-----------------------------------------------------------------------");
		System.out.println();
	}

	public int isAvailable(int sNo)
	{

		for (int i = 0; i < count; i++) 
		{
			if (sNo == theBooks[i].sNo) 
			{
				if (theBooks[i].bookQtyCopy > 0) 
				{
					System.out.println("Book is Available.");
					return i;
				}
				System.out.println("Book is Unavailable");
				return -1;
			}
		}
		System.out.println("No Book of Serial Number "+"Available in Library.");
		return -1;
	}
	
	public book checkOutBook()
	{
		System.out.print("Enter Serial No of Book to be Checked Out: ");
		int sNo = sc.nextInt();
		int bookIndex = isAvailable(sNo);

		if (bookIndex != -1) 
		{
			theBooks[bookIndex].bookQtyCopy--;
			return theBooks[bookIndex];
		}
		return null;
	}

	public void checkInBook(book b)
	{
		for (int i = 0; i < count; i++) 
		{
			if (b.equals(theBooks[i])) 
			{
				theBooks[i].bookQtyCopy++;
				return;
			}
		}
	}
}

class book 
{	
	public int sNo;
	public String bookName;
	public String authorName;
	public int bookQty;
	public int bookQtyCopy;
	Scanner sc = new Scanner(System.in);
	
	public book()
	{		
		System.out.print("Enter Serial No of Book: ");
		this.sNo = sc.nextInt();
		sc.nextLine();

		System.out.print("Enter Book Name: ");
		this.bookName = sc.nextLine();

		System.out.print("Enter Author Name: ");
		this.authorName = sc.nextLine();

		System.out.print("Enter Quantity of Books: ");
		this.bookQty = sc.nextInt();
		bookQtyCopy = this.bookQty;
	}
}