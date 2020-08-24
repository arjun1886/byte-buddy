/*
 * Copyright 2014 - 2020 Rafael Winterhalter
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.bytebuddy.build.gradle;

import net.bytebuddy.build.Plugin;
import net.bytebuddy.build.gradle.api.CompileClasspath;
import org.gradle.api.tasks.InputDirectory;
import org.gradle.api.tasks.InputFiles;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import java.io.File;
import java.io.IOException;

/**
 * A Byte Buddy task implementation that does not use modern Gradle APIs.
 */
public class ByteBuddyLegacyTask extends AbstractByteBuddyTask {

    /**
     * The source file or folder.
     */
    private File source;

    /**
     * The target file.
     */
    private File target;

    /**
     * The class path to supply to the plugin engine.
     */
    private Iterable<File> classPath;

    /**
     * Returns the task's source file or folder.
     *
     * @return The task's source file or folder.
     */
    @InputDirectory
    public File getSource() {
        return source;
    }

    /**
     * Sets the task's source file or folder.
     *
     * @param source The task's source file or folder.
     */
    public void setSource(File source) {
        this.source = source;
    }

    /**
     * Returns the task's target file or folder.
     *
     * @return The task's target file or folder.
     */
    @OutputDirectory
    public File getTarget() {
        return target;
    }

    /**
     * Sets the task's target file or folder.
     *
     * @param target The task's target file or folder.
     */
    public void setTarget(File target) {
        this.target = target;
    }

    /**
     * Returns the class path to supply to the plugin engine.
     *
     * @return The class path to supply to the plugin engine.
     */
    @InputFiles
    @CompileClasspath
    public Iterable<File> getClassPath() {
        return classPath;
    }

    /**
     * Sets the class path to supply to the plugin engine.
     *
     * @param classPath The class path to supply to the plugin engine.
     */
    public void setClassPath(Iterable<File> classPath) {
        this.classPath = classPath;
    }

    @Override
    protected File source() {
        return getSource();
    }

    @Override
    protected File target() {
        return getTarget();
    }

    @Override
    protected Iterable<File> classPath() {
        return getClassPath();
    }

    /**
     * Applies this task.
     * @throws IOException If an I/O exception is thrown.
     */
    @TaskAction
    public void apply() throws IOException {
        doApply(getSource().isDirectory()
                ? new Plugin.Engine.Source.ForFolder(getSource())
                : new Plugin.Engine.Source.ForJarFile(getSource()), getTarget().isDirectory()
                ? new Plugin.Engine.Target.ForFolder(getTarget())
                : new Plugin.Engine.Target.ForJarFile(getTarget()));
    }
}
