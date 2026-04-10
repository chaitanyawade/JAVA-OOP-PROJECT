package com.antigravity.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "teams")
public class Team {
    @Id
    private String id;
    private String name;
    private String type; // "General", "Creative", "Technical"

    // The Vault: Array of the latest AssetNode IDs storing the official project versions
    private List<String> sourceOfTruth = new ArrayList<>();

    // The Chat: Array of ChatNode IDs (or potentially embedded documents)
    private List<String> liveStream = new ArrayList<>();

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public List<String> getSourceOfTruth() { return sourceOfTruth; }
    public void setSourceOfTruth(List<String> sourceOfTruth) { this.sourceOfTruth = sourceOfTruth; }
    public List<String> getLiveStream() { return liveStream; }
    public void setLiveStream(List<String> liveStream) { this.liveStream = liveStream; }
}
