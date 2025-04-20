import java.time.LocalTime; // import the LocalTime class
import java.util.Scanner;

public class Main {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    
    int[] times = openTimer();

    System.out.println("Wait... Press enter after sufficient time passed");
    sc.nextLine();

    int[] elapsed = closeTimer(times);

    System.out.println("Elapsed time: " + elapsed[0] + "h " + elapsed[1] + "m " + elapsed[2] + "s");
    sc.close();
  }

  //returns the time at which the timer was opened as an array of ints
  public static int[] openTimer() 
  {
    LocalTime myObj = LocalTime.now();
    System.out.println("Start time (unformatted): " + myObj);

    String frmtTime = myObj.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
    System.out.println("Start time (formatted): " + frmtTime);
    int hour1 = Integer.parseInt(frmtTime.substring(0,2));
    int min1 = Integer.parseInt(frmtTime.substring(3,5));
    int sec1 = Integer.parseInt(frmtTime.substring(6,8));
    int[] time = {hour1, min1, sec1};
    return time;
  }

  //takes the beginning time and returns time elapsed as an array
  public static int[] closeTimer(int[] times)
  {
    int hour1 = times[0];
    int min1 = times[1];
    int sec1 = times[2];
    int hourelap;
    int minelap;
    int secelap;
    
    LocalTime myObj = LocalTime.now();
    String frmtTime = myObj.format(java.time.format.DateTimeFormatter.ofPattern("HH:mm:ss"));
    System.out.println("End Time: " + frmtTime);
    int hour2 = Integer.parseInt(frmtTime.substring(0,2));
    int min2 = Integer.parseInt(frmtTime.substring(3,5));
    int sec2 = Integer.parseInt(frmtTime.substring(6,8));
    if(hour2 != hour1)
    {
      hourelap = hour2 - hour1;
      secelap = sec2;
      minelap = min2;
    }
    else if(min2 != min1)
    {
      hourelap = 0;
      minelap = min2 - min1;
      secelap = sec2;
    }
    else
    {
      hourelap = 0;
      minelap = 0;
      secelap = sec2 - sec1;
    }

    int[] elapsed = {hourelap, minelap, secelap};
    return elapsed;
  }
}