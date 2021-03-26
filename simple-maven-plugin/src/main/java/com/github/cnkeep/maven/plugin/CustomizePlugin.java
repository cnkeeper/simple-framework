package com.github.cnkeep.maven.plugin;

import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.apache.maven.project.MavenProject;

/**
 * @description: 自定义maven plugin http://maven.apache.org/guides/plugin/guide-java-plugin-development.html
 * @author: <a href="mailto:zhangleili924@gamil.com">LeiLi.Zhang</a>
 * @date: 2021/3/26
 * @version:
 **/
@Mojo(name = "Say",defaultPhase = LifecyclePhase.PACKAGE)
public class CustomizePlugin extends AbstractMojo {

    @Parameter(defaultValue = "${project}", readonly = true, required = true)
    private MavenProject project;

    @Parameter(readonly = false, required = false)
    private JvmConfiguration jvmConfiguration;

    @Override
    public void execute() throws MojoExecutionException {
        getLog().info(project.getProperties().toString());
        getLog().info(project.toString());
        getLog().info("say word:"+jvmConfiguration);
    }
}
