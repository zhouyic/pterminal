package tml.common;

import java.io.PrintStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class TimeMgmt
{
  public boolean checkTimePattern(String paramString)
  {
    int i = -1;
    int j = -1;
    int k = -1;
    int m = -1;
    int n = -1;
    int i1 = -1;
    boolean bool = false;
    int[][] arrayOfInt1 = { { 0, 0 }, { 1, 31 }, { 2, 28 }, { 3, 31 }, { 4, 30 }, { 5, 31 }, { 6, 30 }, { 7, 31 }, { 8, 31 }, { 9, 30 }, { 10, 31 }, { 11, 30 }, { 12, 31 } };
    int[][] arrayOfInt2 = { { 0, 0 }, { 1, 31 }, { 2, 29 }, { 3, 31 }, { 4, 30 }, { 5, 31 }, { 6, 30 }, { 7, 31 }, { 8, 31 }, { 9, 30 }, { 10, 31 }, { 11, 30 }, { 12, 31 } };
    if (14 == paramString.length())
      try
      {
        i = Integer.parseInt(paramString.substring(0, 4));
        j = Integer.parseInt(paramString.substring(4, 6));
        k = Integer.parseInt(paramString.substring(6, 8));
        m = Integer.parseInt(paramString.substring(8, 10));
        n = Integer.parseInt(paramString.substring(10, 12));
        i1 = Integer.parseInt(paramString.substring(12, 14));
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        System.out.println(localNumberFormatException1.toString());
      }
    else if (8 == paramString.length())
      try
      {
        i = Integer.parseInt(paramString.substring(0, 4));
        j = Integer.parseInt(paramString.substring(4, 6));
        k = Integer.parseInt(paramString.substring(6, 8));
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        System.out.println(localNumberFormatException2.toString());
      }
    int i2 = 0;
    if (((i % 4 == 0) && (i % 100 != 0)) || (i % 400 == 0))
      i2 = 1;
    if ((j >= 1) && (j <= 12))
      if (i2 != 0)
      {
        if (14 == paramString.length())
        {
          if ((i >= 1000) && (i <= 9999) && (k >= 1) && (k <= arrayOfInt2[j][1]) && (m >= 0) && (m <= 23) && (n >= 0) && (n <= 59) && (i1 >= 0) && (i1 <= 59))
            bool = true;
        }
        else if ((8 == paramString.length()) && (i >= 1000) && (i <= 9999) && (j >= 1) && (j <= 12) && (k >= 1) && (k <= arrayOfInt2[j][1]))
          bool = true;
      }
      else if (14 == paramString.length())
      {
        if ((i >= 1000) && (i <= 9999) && (j >= 1) && (j <= 12) && (k >= 1) && (k <= arrayOfInt1[j][1]) && (m >= 0) && (m <= 23) && (n >= 0) && (n <= 59) && (i1 >= 0) && (i1 <= 59))
          bool = true;
      }
      else if ((8 == paramString.length()) && (i >= 1000) && (i <= 9999) && (j >= 1) && (j <= 12) && (k >= 1) && (k <= arrayOfInt1[j][1]))
        bool = true;
    return bool;
  }

  public boolean checkDateVal(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = false;
    Date localDate1 = null;
    Date localDate2 = null;
    Date localDate3 = null;
    SimpleDateFormat localSimpleDateFormat = null;
    if (14 == paramString1.length())
      localSimpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
    else if (8 == paramString1.length())
      localSimpleDateFormat = new SimpleDateFormat("yyyyMMdd");
    else
      return false;
    try
    {
      localDate1 = localSimpleDateFormat.parse(paramString1);
    }
    catch (ParseException localParseException1)
    {
      System.out.println(localParseException1.toString());
    }
    if ((paramString2 == null) && (paramString3 != null))
    {
      try
      {
        localDate3 = localSimpleDateFormat.parse(paramString3);
      }
      catch (ParseException localParseException2)
      {
        System.out.println(localParseException2.toString());
      }
      if ((localDate1 != null) && (localDate3 != null) && (localDate1.compareTo(localDate3) <= 0))
        bool = true;
    }
    else if ((paramString2 != null) && (paramString3 == null))
    {
      try
      {
        localDate2 = localSimpleDateFormat.parse(paramString2);
      }
      catch (ParseException localParseException3)
      {
        System.out.println(localParseException3.toString());
      }
      if ((localDate1 != null) && (localDate2 != null) && (localDate1.compareTo(localDate2) >= 0))
        bool = true;
    }
    else if ((paramString2 != null) && (paramString3 != null))
    {
      try
      {
        localDate3 = localSimpleDateFormat.parse(paramString3);
        localDate2 = localSimpleDateFormat.parse(paramString2);
      }
      catch (ParseException localParseException4)
      {
        System.out.println(localParseException4.toString());
      }
      if ((localDate2 != null) && (localDate1 != null) && (localDate3 != null) && (localDate1.compareTo(localDate2) >= 0) && (localDate1.compareTo(localDate3) <= 0))
        bool = true;
    }
    return bool;
  }

  public boolean checkDateV(String paramString1, String paramString2, String paramString3)
  {
    boolean bool = false;
    long l1 = -1L;
    long l2 = -1L;
    long l3 = -1L;
    l1 = Long.parseLong(paramString1);
    if ((paramString2 == null) && (paramString3 == null))
    {
      bool = true;
    }
    else if ((paramString2 == null) && (paramString3 != null))
    {
      try
      {
        l3 = Long.parseLong(paramString3);
      }
      catch (NumberFormatException localNumberFormatException1)
      {
        System.out.println(localNumberFormatException1.toString());
      }
      if (l1 <= l3)
        bool = true;
    }
    else if ((paramString2 != null) && (paramString3 == null))
    {
      try
      {
        l2 = Long.parseLong(paramString2);
      }
      catch (NumberFormatException localNumberFormatException2)
      {
        System.out.println(localNumberFormatException2.toString());
      }
      if (l1 >= l2)
        bool = true;
    }
    else if ((paramString2 != null) && (paramString3 != null))
    {
      try
      {
        l3 = Long.parseLong(paramString3);
        l2 = Long.parseLong(paramString2);
      }
      catch (NumberFormatException localNumberFormatException3)
      {
        System.out.println(localNumberFormatException3.toString());
      }
      if ((l1 <= l3) && (l1 >= l2))
        bool = true;
    }
    return bool;
  }

  public String getCurDate(int paramInt)
  {
    SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat();
    Date localDate = new Date();
    switch (paramInt)
    {
    case 1:
      localSimpleDateFormat.applyPattern("yyyy-MM-dd");
      break;
    case 2:
      localSimpleDateFormat.applyPattern("yyyyMMdd");
      break;
    case 3:
      localSimpleDateFormat.applyPattern("yyyy/MM/dd");
      break;
    case 4:
      localSimpleDateFormat.applyPattern("yyyy-MM-dd HH-mm-ss");
      break;
    case 5:
      localSimpleDateFormat.applyPattern("yyyy-MM-dd HH:mm:ss");
      break;
    case 6:
      localSimpleDateFormat.applyPattern("yyyyMMdd:HHmmss");
      break;
    case 7:
      localSimpleDateFormat.applyPattern("yyyyMM");
      break;
    case 8:
      localSimpleDateFormat.applyPattern("yyyyMMddHHmmss");
      break;
    case 9:
      localSimpleDateFormat.applyPattern("yyyy-MM-dd HH:mm");
      break;
    }
    return localSimpleDateFormat.format(localDate);
  }

  public static String changeTimePattern1(String paramString)
  {
    StringTokenizer localStringTokenizer = new StringTokenizer(paramString, "-");
    if (paramString.length() == 19)
      return localStringTokenizer.nextToken() + localStringTokenizer.nextToken() + localStringTokenizer.nextToken();
    return localStringTokenizer.nextToken() + localStringTokenizer.nextToken() + localStringTokenizer.nextToken() + "000000";
  }
}