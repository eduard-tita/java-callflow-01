/*
 * Copyright (c) 2016-present Sonatype, Inc. All rights reserved.
 *
 * This program is licensed to you under the Apache License Version 2.0,
 * and you may not use this file except in compliance with the Apache License Version 2.0.
 * You may obtain a copy of the Apache License Version 2.0 at http://www.apache.org/licenses/LICENSE-2.0.
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the Apache License Version 2.0 is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the Apache License Version 2.0 for the specific language governing permissions and limitations there under.
 */
package org.sonatype.lifecycle.jenkins.examples.callflow;

import java.io.File;

import com.google.common.io.Files;

/**
 * Vulnerable method callflow example.
 */
public class App
{
  public static void main(final String[] args) {
    File dir = createTempDirectory();
    try {
      System.out.println("Created temporary directory: " + dir);
    }
    finally {
      dir.delete();
    }
  }

  private static File createTempDirectory() {
    // https://nvd.nist.gov/vuln/detail/CVE-2023-2976
    return Files.createTempDir();
  }
}
