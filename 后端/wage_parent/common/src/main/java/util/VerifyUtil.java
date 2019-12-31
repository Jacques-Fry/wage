package util;

/**
 * @author Jack_YD
 * @create 2019/12/11 16:16
 */
public class VerifyUtil {

  //验证密码
  public static boolean password(String password) {
    String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,16}$";
    return password.matches(regex);
  }
}
