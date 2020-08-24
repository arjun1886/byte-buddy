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

import org.gradle.api.GradleException;
import org.gradle.api.file.SourceDirectorySet;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.compile.JavaCompile;

import java.io.File;
import java.io.IOException;

/**
 * Implements a configuration of a Byte Buddy legacy task.
 */
public class ByteBuddyLegacyTaskConfiguration extends AbstractByteBuddyTaskConfiguration<ByteBuddyLegacyTask, ByteBuddyLegacyTaskExtension> {

    /**
     * Creates a new Byte Buddy legacy task configuration.
     *
     * @param name      The name of the task.
     * @param sourceSet The source set for which the task chain is being configured.
     */
    public ByteBuddyLegacyTaskConfiguration(String name, SourceSet sourceSet) {
        super(name, sourceSet, ByteBuddyLegacyTask.class);
    }

    @Override
    protected void configureDirectories(SourceDirectorySet source, JavaCompile compileTask, ByteBuddyLegacyTask byteBuddyTask) {
        try {
            File raw = new File(compileTask.getDestinationDir(), RAW_FOLDER).getCanonicalFile(), processed = compileTask.getDestinationDir();
            compileTask.setDestinationDir(raw);
            byteBuddyTask.setSource(raw);
            byteBuddyTask.setTarget(processed);
            byteBuddyTask.setClassPath(compileTask.getClasspath());
        } catch (IOException exception) {
            throw new GradleException("Could not resolve raw class folder", exception);
        }
    }
}
