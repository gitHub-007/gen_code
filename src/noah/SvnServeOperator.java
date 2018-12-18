package noah;

import org.ini4j.Ini;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Path;

/**
 * @Description TODO
 * @Author Noah
 * @Date 2018-10-22
 * @Version V1.0
 */
public class SvnServeOperator {
    private static Logger logger = LoggerFactory.getLogger(SvnServeOperator.class);

    public static String createRepo(String repoPath) {
        ProcessBuilder pb = new ProcessBuilder("svnadmin", "create", repoPath);
        String s;
        try {
            Process p = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
            while ((s = stdInput.readLine()) != null) {
                logger.info(s);
            }
            while ((s = stdError.readLine()) != null) {
                logger.error(s);
            }
            try {
                p.waitFor();
                stdInput.close();
                stdError.close();
                logger.info("执行创建项目：" + repoPath);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String modifyConf(Path projecPath, String username, String passwd) {
        Path confPath = projecPath.resolve("conf");
        File authzFile = confPath.resolve("authz").toFile();
        File passwdFile = confPath.resolve("passwd").toFile();
        File confFile = confPath.resolve("svnserve.conf").toFile();
        try {
            Ini file = new Ini();
            file.setFile(confFile);
            file.put("general", "anon-access", "read");
            file.put("general", "auth-access", "write");
            file.put("general", "password-db", "passwd");
            file.put("general", "auth-db", "authz");
            file.put("general", "realm", projecPath.getFileName());
            file.store();
            file = new Ini();
            file.setFile(passwdFile);
            file.put("users", username, passwd);
            file.store();
            file = new Ini();
            file.setFile(authzFile);
            file.put(String.format("%s:/", projecPath.getFileName()), username, "rw");
            file.store();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
//        //这是加密方式
//        String hashed = BCrypt.hashpw("jenkins@123456", BCrypt.gensalt());
//        System.out.println(hashed);
//
//        //这是解密方式
//        if (BCrypt.checkpw("jenkins@123456", hashed)) System.out.println("It matches");
//        else System.out.println("It does not match");

    }

}