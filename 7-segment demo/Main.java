import java.util.Scanner;
import java.util.Arrays;
class Main {
  public static final String WHITE = "\u001B[47m";
  public static final String RESET = "\u001B[0m";
  public static final char[] segmentA = {'0', '2', '3', '5', '6', '7', '8', '9', 'A', 'C', 'E', 'F', 'G', 'K', 'M', 'O', 'P', 'Q', 'S', 'W', 'X', 'Z'};
  public static final char[] segmentB = {'0', '1', '2', '3', '4', '7', '8', '9', 'A', 'D', 'H', 'J', 'M', 'O', 'P', 'Q', 'U', 'Y', 'Z'};
  public static final char[] segmentC = {'0', '1', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'D', 'G', 'H', 'J', 'K', 'N', 'O', 'Q', 'S', 'U', 'V', 'W', 'Y'};
  public static final char[] segmentD = {'0', '2', '3', '5', '6', '8', 'B', 'C', 'D', 'E', 'G', 'J', 'L', 'M', 'O', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
  public static final char[] segmentE = {'0', '2', '6', '8', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'N', 'O', 'P', 'R', 'T', 'U', 'W', 'Z'};
  public static final char[] segmentF = {'0', '4', '5', '6', '8', '9', 'A', 'B', 'C', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'O', 'P', 'Q', 'S', 'T', 'U', 'Y'};
  public static final char[] segmentG = {'2', '3', '4', '5', '6', '8', '9', 'A', 'B', 'D', 'E', 'F', 'H', 'K', 'N', 'P', 'Q', 'R', 'S', 'T', 'X', 'Y', 'Z'};

  public static final char[] numbers = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'};
  public static final char[] alphabet = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
  
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    
    for(char c : numbers)
      {
        System.out.println(c + ":");
        printNum(c);
        wait(1000);
        clear();
      }

    for(char a : alphabet)
    {
      System.out.println(a + ":");
      printNum(a);
      wait(1000);
      clear();
    }
    System.out.print("Enter a character: ");
    char c = sc.next().toUpperCase().charAt(0);
    printNum(c);
  }

  public static void printNum(char cha)
  {
    //segment A
    if(Arrays.binarySearch(segmentA, cha) >= 0)
    {
      System.out.println(" " + WHITE + "     " + RESET);
    }
    else System.out.println("       ");

    //segment F & B
    for(int i = 0; i < 3; i++)
      {
        //segment F
        if(Arrays.binarySearch(segmentF, cha) >= 0)
        {
          System.out.print(WHITE + " " + RESET + "     ");
        }
        else System.out.print("      ");
      
        //segment B
        if(Arrays.binarySearch(segmentB, cha) >= 0)
        {
          System.out.print(WHITE + " " + RESET);
        }
        System.out.print("\n");
      }

    //segment G
    if(Arrays.binarySearch(segmentG, cha) >= 0)
      {
        System.out.println(" " + WHITE + "     " + RESET);
      }
      else System.out.println("       ");

    //segment E & C
    for(int i = 0; i < 3; i++)
    {
      //segment E
      if(Arrays.binarySearch(segmentE, cha) >= 0)
      {
        System.out.print(WHITE + " " + RESET + "     ");
      }
      else System.out.print("      ");

      //segment C
      if(Arrays.binarySearch(segmentC, cha) >= 0)
      {
        System.out.print(WHITE + " " + RESET);
      }
      System.out.print("\n");
    }

    //segment D
    if (Arrays.binarySearch(segmentD, cha) >= 0)
    {
      System.out.println(" " + WHITE + "     " + RESET);
    }
    
  }

  public static void clear()
  {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }
  public static void wait(int time)
  {
      try
      {
          Thread.sleep(time);   
      }
      catch(InterruptedException ex)
      {
          ex.printStackTrace();
      }
  }
}