package com.antigravity.model;

import java.time.Instant;

public class Version {
    private String versionId; // e.g., v1, v2
    private String storagePath; // Base64 string or local path for simulation
    private String finalizedBy;
    private Instant finalizedAt;
    private String metadata;
    
    // Getters and Setters
    public String getVersionId() { return versionId; }
    public void setVersionId(String versionId) { this.versionId = versionId; }
    public String getStoragePath() { return storagePath; }
    public void setStoragePath(String storagePath) { this.storagePath = storagePath; }
    public String getFinalizedBy() { return finalizedBy; }
    public void setFinalizedBy(String finalizedBy) { this.finalizedBy = finalizedBy; }
    public Instant getFinalizedAt() { return finalizedAt; }
    public void setFinalizedAt(Instant finalizedAt) { this.finalizedAt = finalizedAt; }
    public String getMetadata() { return metadata; }
    public void setMetadata(String metadata) { this.metadata = metadata; }
}
