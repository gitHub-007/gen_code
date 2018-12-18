package noah;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.util.Assert;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.ATOMIC_MOVE;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

/**
 * @Description TODO
 * @Author Noah
 * @Date 2018-6-8
 * @Version V1.0
 */
public class Copyright {
    //原所属版权
    private String oldCopyRight;
    //新版权所属
    private String newCopyRight;
    //项目路径(指定到根目录即可)
    private String projectPath = "F:/java_workspace/ptnetwok-base_RELEASE";

    public Copyright() {
    }

    public Copyright(String projectPath, String oldCopyRight, String newCopyRight) {
        this.projectPath = projectPath;
        this.oldCopyRight = oldCopyRight;
        this.newCopyRight = newCopyRight;
        Assert.hasLength(projectPath.trim(), "项目路径必填");
        Assert.isTrue(Paths.get(projectPath.trim()).toFile().isDirectory());
        if (StringUtils.isBlank(newCopyRight)) {
            newCopyRight = oldCopyRight;
            System.out.println("新版权未指定,使用原来的版权");
        }
        Assert.isTrue(!StringUtils.isNumeric(newCopyRight), "版权所属者不能全是数字");
    }

    public void modifyCopyRight() {
        try {
            modifySrcDirFiles(projectPath);
            modifyWebContentDirFiles(projectPath);
            modifyPomXmlFile(projectPath);
            executor.shutdown();
            executor.awaitTermination(1, TimeUnit.DAYS);
            if (executor.isTerminated()) {
                System.out.println("所有文件内容替换完毕");
                reNameFiles(projectPath);
                reNamePackageName(projectPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public static void modifyProjectName(final Path dotProjectPath, String name) {
        try {
            Document document = new SAXReader().read(dotProjectPath.toFile());
            Element element = (Element) document.selectSingleNode("/projectDescription/name");
            element.setText(name);
            OutputStream out = new FileOutputStream(dotProjectPath.toFile());
            OutputFormat format = OutputFormat.createPrettyPrint();
            format.setEncoding("utf-8");
            XMLWriter writer = new XMLWriter(out, format);
            writer.write(document);
            writer.flush();
            writer.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private void modifySrcDirFiles(String projectPath) throws Exception {
        Path srcPath = Paths.get(projectPath).resolve(src);
        Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String fileName = file.toString();
                if (!suffixs.contains(StringUtils.substringAfterLast(fileName, "."))) {
                    System.out.println(String.format("忽略文件-->%s", file.toAbsolutePath().toString()));
                    return super.visitFile(file, attrs);
                }
                if (fileName.contains(Copyright.class.getSimpleName())) {
                    System.out.println(String.format("忽略工具类-->%s,", file.toAbsolutePath().toString()));
                    return super.visitFile(file, attrs);
                }
                executor.submit(() -> modifyFileContent(file, oldCopyRight, newCopyRight));
                return super.visitFile(file, attrs);
            }
        });

    }

    public void modifyWebContentDirFiles(String projectPath) {
        Path srcPath = Paths.get(projectPath).resolve(webContent);
        try {
            Files.walkFileTree(srcPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    for (String ignoreDir : ignoreDirs) {
                        if (dir.endsWith(ignoreDir)) {
                            System.out.println(String.format("忽略文件夹-->%s", dir.toAbsolutePath().toString()));
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                    }
                    return super.preVisitDirectory(dir, attrs);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    String fileName = file.toString();
                    if (!suffixs.contains(StringUtils.substringAfterLast(fileName, "."))) {
                        System.out.println(String.format("忽略文件-->%s", file.toAbsolutePath().toString()));
                        return super.visitFile(file, attrs);
                    }
                    executor.submit(() -> modifyFileContent(file, oldCopyRight, newCopyRight));
                    return super.visitFile(file, attrs);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    private static void modifyCopyRightUtilsPackageName(String projectPath, String oldCopyRight, String newCopyRight) {
        Path copyRightUtilsPath = Paths.get(projectPath).resolve(src).resolve("net").resolve(oldCopyRight).resolve(
                "util").resolve("CopyrightUtils.java");
        if (!copyRightUtilsPath.toFile().exists()) return;
        System.out.println("正在修改工具类包名");
        try {
            Stream<String> lines = Files.lines(copyRightUtilsPath);
            StringBuilder stringBuilder = new StringBuilder((int) copyRightUtilsPath.toFile().length());
            lines.forEach(lineSrc -> {
                String lineCopy = lineSrc;
                if (StringUtils.startsWith(lineCopy, "package")) {
                    lineCopy = StringUtils.replace(lineCopy, oldCopyRight, newCopyRight);
                }
                stringBuilder.append(lineCopy);
                stringBuilder.append(System.getProperty("line.separator"));
            });
            Files.write(copyRightUtilsPath, stringBuilder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void modifyPomXmlFile(String projectPath) {
        Path pomXmlPath = Paths.get(projectPath).resolve("pom.xml");
        if (!pomXmlPath.toFile().exists()) return;
        System.out.println("正在修改pom.xml文件内容");
        executor.submit(() -> modifyFileContent(pomXmlPath, oldCopyRight, newCopyRight));
    }

    public synchronized void reNameFiles(String projectPath) {
        if (StringUtils.equals(oldCopyRight, newCopyRight)) {
            return;
        }
        try {
            Path srcPath = Paths.get(projectPath).resolve(src);
            Path propertyPath = srcPath.resolve(String.format("%s.properties", oldCopyRight));
            Path newPropertyPath = srcPath.resolve(String.format("%s.properties", newCopyRight));

            System.out.println("正在重命名相关文件");
            if (propertyPath.toFile().exists()) {
                Files.move(propertyPath, newPropertyPath, REPLACE_EXISTING, ATOMIC_MOVE);
            } else {
                System.out.println(String.format("文件-->%s不存在", propertyPath.toAbsolutePath().toString()));
            }
            Path xmlPath = srcPath.resolve(String.format("%s.xml", oldCopyRight));
            Path newXmlPath = srcPath.resolve(String.format("%s.xml", newCopyRight));
            if (xmlPath.toFile().exists()) {
                Files.move(xmlPath, newXmlPath, REPLACE_EXISTING, ATOMIC_MOVE);
            } else {
                System.out.println(String.format("文件-->%s不存在", xmlPath.toAbsolutePath().toString()));
            }
            Path txtPath = Paths.get(projectPath).resolve(webContent).resolve(String.format("%s.txt", oldCopyRight));
            Path newTxtPath = srcPath.resolve(String.format("%s.txt", newCopyRight));
            if (txtPath.toFile().exists()) {
                Files.move(txtPath, newTxtPath, REPLACE_EXISTING, ATOMIC_MOVE);
            } else {
                System.out.println(String.format("文件-->%s不存在", txtPath.toAbsolutePath().toString()));
            }
            System.out.println("重命名相关文件处理完毕");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public synchronized void reNamePackageName(String projectPath) {
        Path netPath = Paths.get(projectPath).resolve(src).resolve("net");
        Path netPathSrc = netPath.resolve(oldCopyRight);
        Path netPathNew = netPath.resolve(newCopyRight);
        if (Files.notExists(netPathSrc, LinkOption.NOFOLLOW_LINKS)) return;
        try {
            if (Files.notExists(netPathNew)) {
                Files.createDirectories(netPathNew);
            }
            // 如果相同则返回
            if (Files.exists(netPathSrc) && Files.isSameFile(netPathSrc, netPathNew)) return;
            // 目标文件夹不能是源文件夹的子文件夹
            System.out.println("重命名包名,请稍等");
            Files.walkFileTree(netPathSrc, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    // 在目标文件夹中创建dir对应的子文件夹
                    Path subDir = 0 == dir.compareTo(netPathSrc) ? netPathNew :
                            netPathNew.resolve(dir.subpath(netPathNew.getNameCount(), dir.getNameCount()));
                    Files.createDirectories(subDir);
                    return super.preVisitDirectory(dir, attrs);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.move(file, netPathNew.resolve(file.subpath(netPathSrc.getNameCount(), file.getNameCount()))
                            , REPLACE_EXISTING, ATOMIC_MOVE);
                    return super.visitFile(file, attrs);
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    // 移动操作时删除源文件夹
                    Files.delete(dir);
                    return super.postVisitDirectory(dir, exc);
                }
            });
            System.out.println(String.format("重命名包名结束%s-->%s,请刷新项目", netPathSrc.toAbsolutePath().toString(),
                                             netPathNew.toAbsolutePath().toString()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }

    }

    public boolean modifyFileContent(Path path, String oldStr, String newStr) {
        System.out.println(String.format("正在处理文件-->%s", path.toAbsolutePath().toString()));
        if (!path.toFile().exists()) return false;
        try {
            Stream<String> lines = Files.lines(path);
            StringBuilder stringBuilder = new StringBuilder((int) path.toFile().length());
            lines.forEach(lineSrc -> {
                String lineCopy = lineSrc;
                if (StringUtils.contains(lineCopy, oldStr)) {
                    lineCopy = StringUtils.replace(lineCopy, oldStr, newStr);
                }
                if (StringUtils.contains(lineCopy, oldStr.toUpperCase())) {
                    lineCopy = StringUtils.replace(lineCopy, oldStr.toUpperCase(), newStr.toUpperCase());
                }
                stringBuilder.append(lineCopy);
                stringBuilder.append(System.getProperty("line.separator"));
            });
            Files.write(path, stringBuilder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage(), e);
        }
        System.out.println(String.format("文件-->%s处理完毕", path.toAbsolutePath().toString()));
        return true;
    }

    public String getOldCopyRight() {
        return oldCopyRight;
    }

    public void setOldCopyRight(String oldCopyRight) {
        this.oldCopyRight = oldCopyRight;
    }

    public String getNewCopyRight() {
        return newCopyRight;
    }

    public void setNewCopyRight(String newCopyRight) {
        this.newCopyRight = newCopyRight;
    }

    public String getProjectPath() {
        return projectPath;
    }

    public void setProjectPath(String projectPath) {
        this.projectPath = projectPath;
    }

    final static String src = "src";
    final static String webContent = "WebContent";
    //这样的方式有可能会导致oom
    //    static ExecutorService executor = Executors.newFixedThreadPool(50);
    private static ExecutorService executor = new ThreadPoolExecutor(50, 50, 1, TimeUnit.SECONDS,
                                                                     new LinkedTransferQueue<>(),
                                                                     new ThreadPoolExecutor.CallerRunsPolicy());

    static Set<String> suffixs = new HashSet(Arrays.asList("java", "html", "htm", "ftl", "jsp", "sql", "properties",
                                                           "xls", "txt", "js", "css", "xml", "dic"));

    static Set<String> ignoreDirs = new HashSet(Arrays.asList("class", "lib", "captcha", "upload", "fonts", "font",
                                                              "images", "datePicker", "flash", "layer", "ueditor"));

}

