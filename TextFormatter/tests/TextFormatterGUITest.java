import static java.util.concurrent.ThreadLocalRandom.current;
import static org.junit.Assert.assertEquals;

import org.junit.Test;
import tf.gui.TextFormatterGUI;

/**
 * Created by
 * Daniel Roesch
 * as "Nils Darmstrong".
 * -----------------
 * For "TextFormatter",
 * on 15.05.2017, 21:34.
 */
public class TextFormatterGUITest {

  private static String text;
  private static String outcome1, outcome2, outcome3, outcome4;
  private static String substring, replacementString;
  private static char character, replacement;

  static {
    text = "Hello, this is an example text, that has no meaning or value. Just an Example.";
    character = 'e';
    replacement = 'ö';
    substring = "example";
    replacementString = "bucket of shit";
    outcome1 = "Höllo, this is an öxamplö töxt, that has no möaning or valuö. Just an Examplö.";
    outcome2 = "Höllo, this is an öxamplö töxt, that has no möaning or valuö. Just an öxamplö.";
    outcome3 = "Hello, this is an bucket of shit text, that has no meaning or value. Just an Example.";
    outcome4 = "Hello, this is an bucket of shit text, that has no meaning or value. Just an bucket of shit.";
  }

  /**
   * method to create a random integer within given bounds.
   * the bounds are inclusive, so the random number can be the bound itself.
   * this method is the preferred standard after java 1.7.
   *
   * @param min int minimum value.
   * @param max int maximum value.
   * @return the randomly generated int.
   */
  private static int randInt(int min, int max) {
    return current().nextInt(min, max + 1);
  }

  /**
   * method to return a random char.
   * @return char between the ascii values 32 and 125.
   */
  private static char randChar(){
    // 32 is space in ascii, 125 (exclusive upper bound) is closing bracket.
    return (char) (current().nextInt(32, 126));
  }

  /**
   * method to create a random String of a specific length.
   * @param length int length for the string.
   * @return the randomly created String.
   */
  private static String randString(int length){
    String s = "";
    for (int i = 0; i < length; i++) {
      s += randChar();
    }
    return s;
  }

  @Test
  public void SingleCharReplacementTest(){
    String test1 = TextFormatterGUI.replaceSingleChar(character, replacement, text, true);
    assertEquals(test1, outcome1);
    String test2 = TextFormatterGUI.replaceSingleChar(character, replacement, text, false);
    assertEquals(test2, outcome2);
  }

  @Test
  public void SubstringReplacementTest(){
    String test3 = TextFormatterGUI.replaceSubstring(substring, replacementString, text, true);
    assertEquals(test3, outcome3);
    String test4 = TextFormatterGUI.replaceSubstring(substring, replacementString, text, false);
    assertEquals(test4, outcome4);
  }

}
