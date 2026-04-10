package com.antigravity.controller;

import com.antigravity.model.Team;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/blueprints")
public class BlueprintController {

    @PostMapping("/team")
    public ResponseEntity<Team> createTeamBlueprint(@RequestBody TeamRequest request) {
        // Utilizing a Factory Pattern dynamically dispatching team configurations depending on industry requested
        Team newTeam = TeamFactory.createTeam(request.getName(), request.getType());
        
        // In fully deployed project, repository is invoked here: teamRepository.save(newTeam);
        return ResponseEntity.ok(newTeam);
    }
}

// Request DTO Structure mapping
class TeamRequest {
    private String name;
    private String type;
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
}

// Factory Pattern implementation for configuring specific nodes directly inside object initialization
class TeamFactory {
    public static Team createTeam(String name, String type) {
        Team team = new Team();
        team.setName(name);
        
        if ("Creative".equalsIgnoreCase(type)) {
            team.setType("Creative");
            // Perform setup of nodes/blueprints needed specifically for Design & Ad Agencies
            team.getSourceOfTruth().add("blueprint-creative-folder-structure-id");
        } else if ("Technical".equalsIgnoreCase(type)) {
            team.setType("Technical");
            // Perform setup corresponding specifically to Dev-teams
            team.getSourceOfTruth().add("blueprint-agile-sprint-backlog-id");
        } else {
            team.setType("General");
            // Build minimal standard setup configuration
        }
        
        return team;
    }
}
