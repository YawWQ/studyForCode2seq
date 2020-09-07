import java.io.*;

public class ProcessJava {
    public static void main(String args[]) {
        String path = "data/javaCode";
        String outputPath = "data/javaCode/output/";
        String typeName = ".java";
        File[] files = getFileType(path, typeName);
        for (File file : files) {
            String filePath = file.getAbsolutePath();
            String fileName = file.getName();
            System.out.println(filePath);
            try {
                BufferedReader in = new BufferedReader(new FileReader(filePath));
                String line;
                boolean endFlag = true;
                while ((line = in.readLine()) != null) {
                    if (endFlag) {
                        System.out.println(line);
                        int commentPos = line.indexOf("//");
                        if (commentPos != -1) {
                            line = line.substring(0, commentPos);
                        }
                        int conmmentPos2 = line.indexOf("/*");
                        if (conmmentPos2 != -1) {
                            line = "";
                            endFlag = false;
                            continue;
                        }
                        line = line.replaceAll("[\\pS\\pC]", "");
                        // write
                        File output = new File(outputPath + fileName);
                        output.createNewFile();
                        BufferedWriter out = new BufferedWriter(new FileWriter(output));
                        out.write(line);
                        out.flush();
                        out.close();
                    } else {
                        int conmmentPos3 = line.indexOf("*/");
                        if (conmmentPos3 != -1) {
                            line = line.substring(conmmentPos3+1);
                            endFlag = true;
                            line = line.replaceAll("[\\pS\\pC]", "");
                            // 写入
                            File output = new File(outputPath + fileName);
                            output.createNewFile();
                            BufferedWriter out = new BufferedWriter(new FileWriter(output));
                            out.write(line);
                            out.flush();
                            out.close();
                        } else {
                            line = "";
                            continue;
                        }
                    }

                }
            } catch (IOException e) {
            }
        }
    }

    public static File[] getFileType(String path, String typeName) {
        File directionry = new File(path);
        File[] files = new File[]{};
        if (directionry.isDirectory()) {
            files = directionry.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    return new File(dir, name).isFile() && name.endsWith(typeName);
                }
            });
        }
        return files;
    }
}
