package com.github.cnkeep.maven.plugin;

public class JvmConfiguration {
    private String jdkVersion;
    private String word;

    public String getJdkVersion() {
        return jdkVersion;
    }

    public void setJdkVersion(String jdkVersion) {
        this.jdkVersion = jdkVersion;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    @Override
    public String toString() {
        return "JvmConfiguration{" +
                "jdkVersion='" + jdkVersion + '\'' +
                ", word='" + word + '\'' +
                '}';
    }
}
