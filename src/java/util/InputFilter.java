/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

public final class InputFilter {

 
   /**
    *  Given a phone number string, return true if it is an 8-digit number
    */
   public static boolean isValidPhone(String phoneNumber) {
      if (phoneNumber.length() != 8) {
         return false;
      }
      for (int i = 0; i < phoneNumber.length(); ++i) {
         char c = phoneNumber.charAt(i);
         if (c < '0' || c > '9') {
            return false;
         }
      }
      return true;
   }
 
   /**
    * Given a string, return a positive integer if the string can be parsed into
    * a positive integer. Return 0 for non-positive integer or parsing error.
    */
   public static int parsePositiveInt(String str) {
      if (str == null || (str = str.trim()).length() == 0) {
         return 0;
      }
 
      int result;
      try {
         result = Integer.parseInt(str);
      } catch (NumberFormatException ex) {
         return 0;
      }
      return (result > 0) ? result : 0;
   }
}
