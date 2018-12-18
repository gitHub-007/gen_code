package noah;

import net.ptnetwork.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tmatesoft.svn.core.*;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.svn.SVNRepositoryFactoryImpl;
import org.tmatesoft.svn.core.wc.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

/**
 * 使用Hign-Level API来操作Working Copy，由于所有的操作比较贴近SVN命令行、客户端的操作，所以理解起来相对容易一些。
 * 对于Working Copy的不同操作被分类封装到不同的SVN*Client中，SVNClientManager类包含这些SVN*Client，所以通常使用SVNClientManager类可以很方便的管理这些SVN*Client
 * 所以我们在操作Working Copy时有两种选择：1.可以分别实例化各种SVN*Client类。2
 * .实例化一个SVNClientManager类，实际上在首次请求SVNClient类的时候，它就已经实例化了每个SVN*Client类了。
 * 具体结构详见类图
 */
public class ManageWorkingCopy {
    private static Logger logger = LoggerFactory.getLogger(ManageWorkingCopy.class);
    public SVNClientManager svnClientManager;

    public ManageWorkingCopy(String userName, String password) {
        //1.根据访问协议初始化工厂
        SVNRepositoryFactoryImpl.setup();
        //2.使用默认选项
        ISVNOptions isvnOptions = SVNWCUtil.createDefaultOptions(false);
        //3.初始化权限
        ISVNAuthenticationManager isvnAuthenticationManager = SVNWCUtil.createDefaultAuthenticationManager(userName,
                                                                                                           password.toCharArray());
        //4.创建SVNClientManager的实例
        svnClientManager = SVNClientManager.newInstance(isvnOptions, isvnAuthenticationManager);
    }

    public void doImport(SVNURL svnurl, Path path, String log) {
        SVNProperties svnProperties = new SVNProperties();
        boolean useGlobalIgnores = true;
        boolean ignoreUnknownNodeTypes = true;
        try {
            SVNCommitInfo svnCommitInfo = svnClientManager.getCommitClient().doImport(path.toFile(), svnurl, log,
                                                                                      svnProperties, useGlobalIgnores
                    , ignoreUnknownNodeTypes, SVNDepth.INFINITY);
            logger.info("执行import操作成功，导入结果：" + JsonUtils.toJson(svnCommitInfo));
        } catch (SVNException e) {
            e.printStackTrace();
        }
    }

    /**
     * 检出-checkout
     * 参数：
     * 仓库地址
     * 本地Working Copy地址
     * Peg Revision
     * Revision
     * 检出深度，一般递归检出
     * 是否允许没有版本的障碍物，true的话允许，false不允许，false在checkout的时候如果有障碍物就会停止检出，所以一般是true
     * 返回值：long 当前版本号
     *
     * @throws SVNException
     */
    public void doCheckout(SVNURL svnurl, Path path) {
        try {
            long nowRevision = svnClientManager.getUpdateClient().doCheckout(svnurl, path.toFile(), SVNRevision.HEAD,
                                                                             SVNRevision.HEAD, SVNDepth.INFINITY, true);
            logger.info("执行checkout操作成功，当前检出的版本号是：" + nowRevision);
        } catch (SVNException e) {
            e.printStackTrace();
        }
    }

    /**
     * 提交-commit
     * commit更改一个文件时，如果文件本身存在，则需要提交其父目录
     *
     * @throws SVNException
     */
    public void doCommit(Path workingCopyBasPath, String log) {
        try {
            List<File> files = new ArrayList<>();
            //没有版本号的先执行add操作
            Files.walkFileTree(workingCopyBasPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    if (dir.endsWith(".svn")) {
                        logger.warn(String.format("忽略文件夹-->%s", dir.toAbsolutePath().toString()));
                        return FileVisitResult.SKIP_SUBTREE;
                    }
                    File dirFile = dir.toFile();
                    addFile(dirFile);
                    files.add(dirFile);
                    return super.preVisitDirectory(dir, attrs);
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    File tmpFile = file.toFile();
                    addFile(tmpFile);
                    files.add(tmpFile);
                    return super.visitFile(file, attrs);
                }
            });
            //执行commit操作
            svnClientManager.getCommitClient().setIgnoreExternals(true);
            SVNProperties svnProperties = new SVNProperties();
            String[] changeLists = new String[]{};
            SVNCommitInfo svnCommitInfo =
                    svnClientManager.getCommitClient().doCommit(files.toArray(new File[files.size()]), false, log,
                                                                svnProperties, changeLists, false, false,
                                                                SVNDepth.fromRecurse(true));
            logger.info("执行commit操作成功，操作结果：" + JsonUtils.toJson(svnCommitInfo));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addFile(File tempFile) {
        try {
            SVNStatus status = svnClientManager.getStatusClient().doStatus(tempFile, true);
            if (status == null || status.getContentsStatus() == SVNStatusType.STATUS_UNVERSIONED) {
                logger.info("文件" + tempFile.getName() + "无版本号");
                svnClientManager.getWCClient().doAdd(tempFile, true, false, true, SVNDepth.INFINITY, false, true);
            }
        } catch (SVNException e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新-update
     *
     * @throws SVNException
     */
    public void doUpdate(Path path) {
        try {
            long nowRevision = svnClientManager.getUpdateClient().doUpdate(path.toFile(), SVNRevision.HEAD,
                                                                           SVNDepth.INFINITY, true, false);
            logger.info("执行update操作成功，当前版本号：" + nowRevision);
        } catch (SVNException e) {
            e.printStackTrace();
        }
    }

    /**
     * 创建目录-mkdir
     *
     * @throws SVNException
     */
    public void doMkDir() throws SVNException {
        String commitMessage = "创建一个目录";
        SVNCommitInfo svnCommitInfo = svnClientManager.getCommitClient().doMkDir(new SVNURL[]{SVNURL.parseURIEncoded(
                "https://wlyfree-PC:8443/svn/testRepository/trunk/aaa"), SVNURL.parseURIEncoded("https://wlyfree-PC:8443/svn/testRepository/trunk/bbb")}, commitMessage);
        System.out.println("执行mkdir操作成功，操作结果：" + JsonUtils.toJson(svnCommitInfo));
    }

    /**
     * 锁定-lock
     *
     * @throws SVNException
     */
    public void doLock() throws SVNException {
//        svnClientManager.getWCClient().doLock(new SVNURL[]{SVNURL.parseURIEncoded
// ("https://wlyfree-PC:8443/svn/testRepository/trunk/bbb/aa.txt")},true,"给文件加锁");
        svnClientManager.getWCClient().doLock(new File[]{new File("E:\\svnWorkspace\\projectWorkingCopy\\bbb\\aa.txt")}, true, "给文件加锁");
        System.out.println("给文件加锁成功");
    }

    /**
     * 删除-delete
     *
     * @throws SVNException
     */
    public void doDelete() throws SVNException {
        SVNCommitInfo svnCommitInfo =
                svnClientManager.getCommitClient().doDelete(new SVNURL[]{SVNURL.parseURIEncoded("https://wlyfree-PC" + ":8443/svn" + "/testRepository/trunk/bbb"), SVNURL.parseURIEncoded("https://wlyfree-PC:8443/svn/testRepository/trunk/b.txt")}, "执行删除操作，删除一个目录bbb一个文件b.txt");
        System.out.println("执行delete操作成功，操作结果：" + JsonUtils.toJson(svnCommitInfo));
    }

    /**
     * 复制-copy
     *
     * @throws SVNException
     */
    public void doCopy() throws SVNException {
        SVNCopySource svnCopySource1 = new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, SVNURL.parseURIEncoded(
                "https://wlyfree-PC:8443/svn/testRepository/trunk/aaa/aa.txt"));
        SVNCopySource svnCopySource2 = new SVNCopySource(SVNRevision.HEAD, SVNRevision.HEAD, SVNURL.parseURIEncoded(
                "https://wlyfree-PC:8443/svn/testRepository/trunk/aaa/bb.txt"));
        svnClientManager.getCopyClient().doCopy(new SVNCopySource[]{svnCopySource1, svnCopySource2}, new File("E:\\svnWorkspace\\projectWorkingCopy\\bbb"), false, false, true);
        System.out.println("执行copy操作成功");
    }

    /**
     * 状态-status
     *
     * @throws SVNException
     */
    public void doStatus() throws SVNException {
        SVNStatus svnStatus = svnClientManager.getStatusClient().doStatus(new File("E:\\svnWorkspace" +
                                                                                           "\\projectWorkingCopy\\a" + ".txt"), true);
        System.out.println("执行status操作成功，操作结果：" + JsonUtils.toJson(svnStatus));
    }

    /**
     * 信息-info
     *
     * @throws SVNException
     */
    public void doInfo() throws SVNException {
        SVNInfo svnInfo = svnClientManager.getWCClient().doInfo(new File("E:\\svnWorkspace\\projectWorkingCopy\\a" +
                                                                                 ".txt"), SVNRevision.HEAD);
        System.out.println("执行info操作成功，操作结果：" + JsonUtils.toJson(svnInfo));
    }
}