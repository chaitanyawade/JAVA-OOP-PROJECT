package com.antigravity.model;

import java.util.ArrayList;
import java.util.List;

public class AssetNode extends ProjectNode {
    private List<Version> versions = new ArrayList<>();
    private String currentVersionId;
    private String fileName;

    public AssetNode() {
        this.type = "ASSET";
    }

    // Getters and Setters
    public List<Version> getVersions() { return versions; }
    public void setVersions(List<Version> versions) { this.versions = versions; }
    public String getCurrentVersionId() { return currentVersionId; }
    public void setCurrentVersionId(String currentVersionId) { this.currentVersionId = currentVersionId; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
}
