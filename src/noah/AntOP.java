package noah;

import org.apache.commons.lang3.StringUtils;
import org.apache.tools.ant.*;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

/**
 * @description ToDo
 * @author Noah
 * @created at 2018-07-13 09:42:19
 **/
public class AntOP {

    enum LOG_LEVEL {
        MSG_ERR, MSG_WARN, MSG_INFO, MSG_VERBOSE, MSG_DEBUG
    }

    private static final String JAVA_HOME = "JAVA_HOME";

    public static final String BUILD_FILE = Main.DEFAULT_BUILD_FILENAME;

    static {
        Objects.requireNonNull(System.getenv(JAVA_HOME), String.format("环境变量：%s未配置", JAVA_HOME));
    }

    private Project project = new Project();
    private BuildLogger buildLogger = new DefaultLogger();
    private File buildFile;

    public AntOP buildFile(Path buildPath) {
        if (!buildPath.endsWith(".xml")) {
            buildPath = buildPath.resolve(BUILD_FILE);
        }
        this.buildFile = new File(buildPath.toAbsolutePath().toString());
        return this;
    }

    /**
     * @param buildLogger
     * @param level       日志输出级别(LOG_LEVEL.MSG_INFO)
     * @return
     */
    public AntOP addEventListener(BuildLogger buildLogger, LOG_LEVEL level) {
        this.buildLogger = buildLogger;
        this.project.addBuildListener(this.buildLogger);
        this.buildLogger.setMessageOutputLevel(level.ordinal());
        return this;
    }

    public AntOP addEventListener() {
        this.project.addBuildListener(this.buildLogger);
        this.buildLogger.setMessageOutputLevel(LOG_LEVEL.MSG_DEBUG.ordinal());
        return this;
    }

    public AntOP addOutputPrintStream() {
        this.buildLogger.setOutputPrintStream(System.out);
        return this;
    }

    public AntOP addErrorPrintStream() {
        this.buildLogger.setErrorPrintStream(System.err);
        return this;
    }

    public AntOP setBaseDir(Path baseDir) {
        this.project.setBaseDir(baseDir.toFile());
        return this;
    }

    public Project getProject() {
        return project;
    }

    /**
     * 执行build.xml文件 中的target
     *
     * @param target 需要执行的任务
     */
    public void exeTarget(String target) {
        try {
            this.project.fireBuildStarted();
            this.project.init();
            ProjectHelper helper = ProjectHelper.getProjectHelper();
            helper.parse(this.project, buildFile);
            this.project.executeTarget(StringUtils.isBlank(target) ? project.getDefaultTarget() : target);
            this.project.fireBuildFinished(null);
        } catch (BuildException e) {
            project.fireBuildFinished(e);
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void exeDefaultTarget() {
        this.exeTarget(this.project.getDefaultTarget());
    }



}