/*
 * Copyright 2016-2017 EMBL - European Bioinformatics Institute
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package eva.dalio.demo;

import java.io.*;

public class FileUtils {

    BufferedReader reader;
    PrintStream writer;

    public FileUtils(String path) {
        File file = new File(path);
        if (!file.exists()) {
            throw new RuntimeException("File not exist");
        }

        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public FileUtils(){
        try {
            writer=new PrintStream(new FileOutputStream(new File("variants.txt")));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public Line readLine() throws IOException {
        String line = reader.readLine();
        if (line != null) {
            return new Line(line);
        }
        return null;
    }

    public void write(Allele allele){
        writer.append(allele.toString());
    }

    public void closeReader() {
        try {
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void closeWriter() {
        writer.close();
    }
}
