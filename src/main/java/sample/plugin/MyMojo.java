package sample.plugin;


import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;

import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;



/**
 * Goal which touches a timestamp file.
 */
@Mojo( name = "deploy", defaultPhase = LifecyclePhase.PROCESS_SOURCES )
public class MyMojo
    extends AbstractMojo {
    /**
     * Location of the file.
     */
    @Parameter( defaultValue = "${project.build.directory}", property = "outputDir", required = true )
    private File outputDirectory;

    public void execute() throws MojoExecutionException {
        getLog().info( "Using latest plugin 1" );

        try {
            ProcessBuilder processBuilder = new ProcessBuilder();
            processBuilder.command("git", "status");
            Process process = processBuilder.start();

            BufferedReader stdInput = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String s = null;
            while ((s = stdInput.readLine()) != null) {
                getLog().info(s);
            }

            BufferedReader stdError = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            while ((s = stdError.readLine()) != null) {
                getLog().info(s);
            }

            int exitCode = process.waitFor();
        }
        catch (Exception e) {
            getLog().info( "shell command failed" );
        }

    }
}
