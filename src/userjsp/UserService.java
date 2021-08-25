package userjsp;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import userjsp.utils.StringUtil;

public class UserService {

    static final String 이름_필수 = "이름을 입력하세요";
    static final String 사용자ID_필수 = "사용자ID를 입력하세요";
    static final String 사용자ID_중복 = "사용자ID가 중복됩니다";
    static final String 작업_실패 = "작업 도중 오류가 발생했습니다";


    public static String validate(User user) throws Exception {
        if (StringUtil.isEmptyOrBlank(user.getUserid()))
            return 사용자ID_필수;
        if (StringUtil.isEmptyOrBlank(user.getName()))
            return 이름_필수;
        User user1 = UserDAO.findByUserid(user.getUserid());
        if (user1 != null && user1.getId() != user.getId())
            return 사용자ID_중복;
        return null;
    }

    public static String insert(User user) {
        try {
            String errorMessage = validate(user);
            if (errorMessage != null) return errorMessage;
            String pw = encrypt(user.getPassword());
            user.setPassword(pw);
            UserDAO.insert(user);
            return null;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return 작업_실패;
        }
    }

    public static String update(User user) {
        try {
            String errorMessage = validate(user);
            if (errorMessage != null) return errorMessage;
            UserDAO.update(user);
            return null;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return 작업_실패;
        }
    }

    public static String delete(int id) {
        try {
            UserDAO.delete(id);
            return null;
        } catch (Exception e) {
            e.printStackTrace(System.err);
            return 작업_실패;
        }
    }

    public static String encrypt(String passwd) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        byte[] passBytes = passwd.getBytes();
        md.reset();
        byte[] digested = md.digest(passBytes);
        StringBuffer sb = new StringBuffer();
        for(int i=0;i<digested.length;i++)
            sb.append(Integer.toHexString(0xff & digested[i]));
        return sb.toString();
    }

}
